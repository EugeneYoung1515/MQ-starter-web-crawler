package com.example.demo;

import com.example.demo.service.BookCrawlerService;
import com.example.demo.util.DateUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.pool.ScheduledPool;
import org.apache.http.HttpHost;
import org.asynchttpclient.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.asynchttpclient.channel.ChannelPool;
import org.asynchttpclient.proxy.ProxyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQ;
import org.ywcjxf.simple.redismq.core.SimpleRedisMQExecutor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan({"com.example.demo.dao"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class DemoApplication {
	private SimpleRedisMQExecutor<String> stringSimpleRedisMQExecutor;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);


		SimpleRedisMQ simpleRedisMQ = context.getBean(SimpleRedisMQ.class);
		DemoApplication demoApplication = context.getBean(DemoApplication.class);
		ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) context.getBean("threadPoolTaskExecutor");
		ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
		BookCrawlerService bookCrawlerService = context.getBean(BookCrawlerService.class);

		try {
			Thread.sleep(20000);
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}


		//DefaultAsyncHttpClientConfig cfg = new DefaultAsyncHttpClientConfig.Builder().setReadTimeout(100).build();
		//AsyncHttpClient cli = new DefaultAsyncHttpClient(cfg);

        AsyncHttpClient cli = new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder()
                .setConnectTimeout(600000)
                .setRequestTimeout(600000)
				.setHandshakeTimeout(600000)
				.setReadTimeout(600000)
		.build());


		/*
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(300000)
				.setConnectionRequestTimeout(300000)
				.setConnectTimeout(300000).build();
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setMaxConnPerRoute(1000)
				.setMaxConnTotal(1000)
				.build();

		httpclient.start();
		*/

		//ScheduledPool pool = new ScheduledPool();
		// 在线程中启动IP池
		//new Thread(pool::execute).start();
		// 从IP池中取出IP

		int i  = 0;
		int num = 1000;

		//CountDownLatch latch = new CountDownLatch(num*2);
		//System.out.println("latch");

		//while (i<64) {
			threadPoolTaskExecutor.execute(() -> {
				int j=2000;
				while (j < 3000) {

					/*
					if(j%15==0){
						IP ip = pool.take();
						System.out.println( ip.getAddress()+" "+ip.getPort());
						System.getProperties().setProperty("http.proxyHost", ip.getAddress());
						System.getProperties().setProperty("http.proxyPort", ip.getPort()+"");
					}
					*/

					try {
						Thread.sleep(120);
					}catch (InterruptedException ex){
						ex.printStackTrace();
					}

					/*
					//HttpHost proxy = new HttpHost(System.getProperties().getProperty("http.proxyHost"),Integer.parseInt(System.getProperties().getProperty("http.proxyPort")));
					//RequestConfig requestConfig1 = RequestConfig.custom().setProxy(proxy).build();

					final HttpGet request = new HttpGet("https://api.ituring.com.cn/api/Book/"+(j+1));
					//request.setConfig(requestConfig1);

					httpclient.execute(request, new FutureCallback<org.apache.http.HttpResponse>() {

						public void completed(final org.apache.http.HttpResponse response) {
							try {
								//latch.countDown();
								simpleRedisMQ.push(EntityUtils.toString(response.getEntity(),Charset.forName("UTF-8")));
							}catch (IOException ex){
								ex.printStackTrace();
							}
						}

						public void failed(final Exception ex) {
							//latch.countDown();
							ex.printStackTrace();
						}

						public void cancelled() {
							//latch.countDown();
							System.out.println("cancelled");
						}

					});
					*/

					/*
				Future<Boolean> f= cli.prepareGet("http://www.ituring.com.cn/book/"+(j+1)).execute(new AsyncCompletionHandler<Boolean>(){
					@Override
					public Boolean onCompleted(Response response) throws Exception {
						String resp = response.getResponseBody();
						simpleRedisMQ.push(resp);
						return true;
					}

					@Override
					public void onThrowable(Throwable t) {
						t.printStackTrace();
					}
				});
				*/

					ListenableFuture<Response> resp = cli.prepareGet("https://api.ituring.com.cn/api/Book/"+(j+1)).execute();
				resp.addListener(()->{
					try {
						//latch.countDown();
						Response r = resp.get();
						simpleRedisMQ.push(r.getResponseBody());
					}catch (InterruptedException|ExecutionException ex){
						ex.printStackTrace();
					}
				},threadPoolTaskExecutor);


				/*

					Boolean v =null;
					try {
						v = f.get();
					}catch (InterruptedException|ExecutionException ex){
						ex.printStackTrace();
					}
					*/

					j++;
				}
			});
			//i++;
		//}

		demoApplication.stringSimpleRedisMQExecutor.setCallBack(m->{
			//latch.countDown();
			//System.out.println(m);
			JsonNode jsonNode = null;
			try {
			 jsonNode  = objectMapper.readTree(m);
				JsonNode status = jsonNode.get("status");
				if(status==null || status.equals(NullNode.getInstance())){
					bookCrawlerService.insertBook(jsonNode);
					DateUtil.close();

				}
			}catch (IOException ex){
				ex.printStackTrace();
			}
			//System.out.println("qaz"+jsonNode);

		}
		);

		demoApplication.stringSimpleRedisMQExecutor.execute();

		/*
		try {
			latch.await();
		}catch (InterruptedException ex){
			ex.printStackTrace();
		}

		try {
			//httpclient.close();
			cli.close();
		}catch (IOException ex){
			ex.printStackTrace();
		}

		System.out.println("close");


		context.close();
		//context.registerShutdownHook();
		*/
	}

	@Autowired
	public void setStringSimpleRedisMQExecutor(SimpleRedisMQExecutor<String> stringSimpleRedisMQExecutor) {
		this.stringSimpleRedisMQExecutor = stringSimpleRedisMQExecutor;

	}
}
