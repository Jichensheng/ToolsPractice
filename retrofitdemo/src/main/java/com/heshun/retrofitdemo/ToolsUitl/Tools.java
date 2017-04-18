package com.heshun.retrofitdemo.ToolsUitl;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * author：Jics
 * 2016/10/27 10:42
 */
public class Tools {
	/**
	 * 获取cpu核心数
	 *
	 * @return
	 */
	public static int getNumCores() {
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new CpuFilter());
			Log.d("OnlineStudy", "CPU Count: " + files.length);
			return files.length;
		} catch (Exception e) {
			Log.d("OnlineStudy", "CPU Count: Failed.");
			e.printStackTrace();
			return 1;
		}
	}
}
