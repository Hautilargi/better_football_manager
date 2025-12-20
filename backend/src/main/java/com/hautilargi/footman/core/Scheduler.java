package com.hautilargi.footman.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	//private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	//@Scheduled(cron = "0 2 * * * ?", zone = "Europe/Berlin")
    @Scheduled(cron = "* */5 * * * *", zone = "Europe/Berlin")
	public void reportCurrentTime() {
		System.out.println(String.format
            ("The time is now %s}", dateFormat.format(new Date())));
	}
}