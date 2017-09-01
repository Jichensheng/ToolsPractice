package com.jcs.music.utils;

/**
 * author：Jics
 * 2017/9/1 16:39
 */
public class Test {

	public static void main(String[] args){
		long time=154*1000;
		long timeE=254*1000;
		System.out.println(formatTime(time,timeE));
	}
	public static String formatTime(long start,long end){
		int minute= (int) (start/1000/60);
		int second= (int) (start/1000%60);

		int minuteE= (int) (end/1000/60);
		int secondE= (int) (end/1000%60);

		return String.format("%02d:%02d/%02d:%02d",minute,second,minuteE,secondE);
	}
}
