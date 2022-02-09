package com.sikiedu.betobe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author zhu
 * @date 2021/4/30 15:36:55
 * @description
 */
public class MyStringUtils {

	// 1分钟的毫秒值
	private final static long MINUTE_TIME = 60 * 1000;
	// 1小时
	private final static long HOUR_TIME = 60 * MINUTE_TIME;
	// 1天
	private final static long DAY_TIME = 24 * HOUR_TIME;
	// 1个月（30天）
	private final static long MONTH_TIME = 30 * DAY_TIME;

	// 截取字符串的一部分
	public String rightVideoTime(String time, String n) {
		return time.substring(0, new Integer(n));
	}

	// 将秒数转换为时分秒的函数
	public String displayVideoSecond(String seconds){

		int secondTime = new Integer(seconds);
		int minuteTime = 0;
		int hourTime = 0;

		// 如果秒数大于60
		if (secondTime >= 60){
			// 获取分钟，除以60去整数，得到整数分钟
			minuteTime = secondTime / 60;
			// 获取秒数，秒数取余，得到整数秒数
			secondTime = secondTime % 60;
			// 如果分钟大于60， 将分钟换成小时
			if (minuteTime >= 60){
				// 获取小时，获取小时除以60，得到整数小时
				hourTime = minuteTime / 60;
				// 获取小时后取余的分钟，
				minuteTime = minuteTime % 60;

			}
		}

		// 字符串拼接
		String target = "" + secondTime;
		if (minuteTime == 0 && hourTime > 0){
			return target = hourTime + ":" + minuteTime + ":" + target;
		}
		if (minuteTime > 0){
			target = minuteTime + ":" + target;
		}
		if (hourTime > 0){
			target = hourTime + ":" + target;
		}

		return target;

	}

	public String FormatRunTime(String runTime1) {

		long runTime = new Long(runTime1);

		if (runTime < 0) return "00:00:00";

		long hour = runTime / 3600;
		long minute = (runTime % 3600) / 60;
		long second = runTime % 60;

		if (hour == 0L){
			return  unitTimeFormat(minute) + ":" + unitTimeFormat(second);
		}

		return unitTimeFormat(hour) + ":" + unitTimeFormat(minute) + ":" +
				unitTimeFormat(second);

	}

	private String unitTimeFormat(long number) {
		return String.format("%02d", number);
	}

	// 时间的显示
	public String displayTime(String time){
		long createTime = parseTime(time);
		long nowTime = System.currentTimeMillis();
		// 现在的时间 - 创建时间
		long targetTime = nowTime - createTime;

		long monthAgo = targetTime / MONTH_TIME;
		long dayAgo = targetTime / DAY_TIME;
		long hourAgo = targetTime / HOUR_TIME;
		long minuteAgo = targetTime / MINUTE_TIME;

		if (minuteAgo < 1) {
			return "a moment ago";
		}
		if (hourAgo < 1 && minuteAgo >= 1 && minuteAgo < 60) {
			return minuteAgo + " minute ago";
		}
		if (dayAgo < 1 && hourAgo >= 1 && hourAgo < 24) {
			return hourAgo + " hour ago";
		}
		if (monthAgo < 1 && dayAgo >= 1 && dayAgo < 30) {
			return dayAgo + " day ago";
		}
		if (monthAgo >= 1 && monthAgo < 12) {
			return monthAgo + " month ago";
		}

		return "a year ago";

	}


		// 将字符串的时间编程毫秒值
	private long parseTime(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long target = 0L;
		try {
			target = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return target;
	}


}
