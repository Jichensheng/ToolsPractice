package com.jcs.wugetui;

/**
 * author：Jics
 * 2017/8/8 14:17
 */
public class BeanSimaple {
	private String cid;
	private String content;

	public BeanSimaple(String cid, String content) {
		this.cid = cid;
		this.content = content;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "BeanSimaple{" +
				"cid='" + cid + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
