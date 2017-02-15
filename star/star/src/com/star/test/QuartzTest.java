package com.star.test;

import java.text.ParseException;
import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Hello, Quartz! - executing its JOB at"
				+ new Date()+"by" + context.getTrigger().getName());
		
	}
	public static void main(String[] args) throws SchedulerException, ParseException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail jobDetail = new JobDetail("helloQuartzJob", 
                Scheduler.DEFAULT_GROUP, QuartzTest.class);
        
        JobDetail jobDetail1 = new JobDetail("helloQuartzJob1", 
                Scheduler.DEFAULT_GROUP, QuartzTest.class);

//        SimpleTrigger simpleTrigger = new SimpleTrigger("simpleTrigger", 
//                Scheduler.DEFAULT_GROUP);
//
//        simpleTrigger.setStartTime(new Date(System.currentTimeMillis()));
//        simpleTrigger.setRepeatInterval(5000);
//        simpleTrigger.setRepeatCount(10);
        String cronExpression = "1/5 * * * * ?";
        CronTrigger cronTrigger = new CronTrigger("cronTrigger", Scheduler.DEFAULT_GROUP, cronExpression);
        scheduler.scheduleJob(jobDetail, cronTrigger);
        
        String cronExpression1 = "1/7 * * * * ?";
        CronTrigger cronTrigger1 = new CronTrigger("cronTrigger1", Scheduler.DEFAULT_GROUP, cronExpression1);
        scheduler.scheduleJob(jobDetail1, cronTrigger1);
        
        scheduler.start();
		
	}
}
