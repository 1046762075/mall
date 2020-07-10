package com.firenay.mall.seckill.scheduel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;



/**
 * <p>Title: HelloSchedule</p>
 * Description：@Scheduled它不是整合cron ,如果要整合cron 需要自己引入
 * date：2020/7/6 17:03
 */
@Slf4j
// 开启异步任务
//@EnableAsync
//@EnableScheduling
//@Component
public class HelloSchedule {

	/**
	 * 在Spring中 只允许6位 [* * * ? * 1] : 每周一每秒执行一次
	 * 						[* /5 * * ? * 1] : 每周一 每5秒执行一次
	 * 	1.定时任务不应阻塞 [默认是阻塞的]
	 * 	2.定时任务线程池 spring.task.scheduling.pool.size=5
	 * 	3.让定时任务异步执行
	 */
	@Async
	@Scheduled(cron = "*/5 * * ? * 1")
	public void hello(){
		log.info("i love you...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) { }
	}
}
