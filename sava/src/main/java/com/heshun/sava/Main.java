package com.heshun.sava;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * author：Jics
 * 2017/5/17 08:58
 */
public class Main {
	public static void main(String[] args) {
		/*for(Entry<String,String> entry:stringKey().entrySet()){
            printThread(entry).start();
		}*/

		List<String> names =new LinkedList<>();
		names.add("jcs");
		names.add("533");
	}

	public static Thread printThread(final Entry<String, String> entry) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(entry.getKey() + " " + Thread.currentThread().getName());
			}
		});
	}

	public static Map<String, String> stringKey() {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < 20; i++) {
			map.put("key" + i, "value" + i);
		}
		return map;
	}
}
