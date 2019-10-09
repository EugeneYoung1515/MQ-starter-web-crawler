package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ywcjxf.simple.redismq.config.EnableSimpleRedisMQ;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQ;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQExecutor;


@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class DemoApplication {

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
				while (j < 240000) {
					simpleRedisMQ.push("qaz");
					j++;
				}
			});
		};



		demoApplication.stringSimpleRedisMQExecutor.setCallBack(m->{
					System.out.println(m);
				/*
				try{
					Thread.sleep(1000);
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

	@Autowired
	public void setStringSimpleRedisMQExecutor(SimpleRedisMQExecutor<String> stringSimpleRedisMQExecutor) {
		this.stringSimpleRedisMQExecutor = stringSimpleRedisMQExecutor;
	}
}
