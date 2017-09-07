package com.jcs.layouttest.fragment;

/**
 * author：Jics
 * 2017/9/5 11:14
 */
public class TBean {
	private String title;
	private String content;

	public TBean(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
