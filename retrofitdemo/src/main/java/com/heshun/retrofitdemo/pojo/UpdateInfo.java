package com.heshun.retrofitdemo.pojo;

/**
 * author：Jics
 * 2016/10/20 16:01
 */
public class UpdateInfo {

	/**
	 * succ : true
	 * statusCode : 200
	 * msg : 获取最新版本成功!
	 * data : {"body":{"versionNo":9,"versionName":"1.0.4","loadAddress":"/upload/20160718192032_eSuzhou_V9_1.0.4_20160718.apk","content":"二维码识别率、优雅的崩溃以","sign":"6391bbed0d49eefa5bc70f001be532ef"}}
	 * time : 1476855557046
	 */

	private boolean succ;
	private int statusCode;
	private String msg;
	/**
	 * body : {"versionNo":9,"versionName":"1.0.4","loadAddress":"/upload/20160718192032_eSuzhou_V9_1.0.4_20160718.apk","content":"二维码识别率、优雅的崩溃以","sign":"6391bbed0d49eefa5bc70f001be532ef"}
	 */

	private DataBean data;
	private long time;

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}


}
