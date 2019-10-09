package org.ywcjxf.simple.redismq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ywcjxf.simple.redismq.core.MessageWrapper;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQ;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQExecutor;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class DemoApplication {

	private RedisTemplate<String,MessageWrapper> stringMessageWrapperRedisTemplate;

	private SimpleRedisMQExecutor<String> stringSimpleRedisMQExecutor;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);


		SimpleRedisMQ simpleRedisMQ = context.getBean(SimpleRedisMQ.class);
		DemoApplication demoApplication = context.getBean(DemoApplication.class);

		ThreadPoolTaskExecutor threadPoolTaskExecutor = context.getBean(ThreadPoolTaskExecutor.class);

		try {
			Thread.sleep(30000);
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}

		for (int i=0;i<1;i++){
			threadPoolTaskExecutor.execute(() -> {
				int j =0;
				while (j < 10000) {
					System.out.println("push");

					simpleRedisMQ.push("qaz");
					j++;
				}
			});
		}

		try{
			Thread.sleep(6000);
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}

		System.out.println("pop");

		try{
			Thread.sleep(6000);
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}

		demoApplication.stringSimpleRedisMQExecutor.setCallBack(m->{
					System.out.println(m);

					/*
					try{
						Thread.sleep(2000);
					}catch (InterruptedException ex){
						ex.printStackTrace();
					}
					*/
				}
		);

		demoApplication.stringSimpleRedisMQExecutor.execute();

		//context.close();
		//context.registerShutdownHook();

	}


	//注意几个转换
	//spring 传给 lua的
	//redis 传给 lua的 lua传给redis的
	//lua 传给spring
	//spring 能接受的lua 返回值
	/*
	The preceding code configures a RedisScript pointing to a file called checkandset.lua, which is expected to return a boolean value. The script resultType should be one of Long, Boolean, List, or a deserialized value type. It can also be null if the script returns a throw-away status (specifically, OK).
	 */
	//long int bool List
	//spring 传给lua的值可能要在字符串和int(long)里试 特别是中间加了一层 jackson的序列化和反序列化

	@Autowired
	public void setStringMessageWrapperRedisTemplate(RedisTemplate<String, MessageWrapper> stringMessageWrapperRedisTemplate) {
		this.stringMessageWrapperRedisTemplate = stringMessageWrapperRedisTemplate;
	}

	@Autowired
	public void setStringSimpleRedisMQExecutor(SimpleRedisMQExecutor<String> stringSimpleRedisMQExecutor) {
		this.stringSimpleRedisMQExecutor = stringSimpleRedisMQExecutor;
	}
}
