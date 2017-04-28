package com.heshun.retrofitrxjava.entity;

public class HeadDefault {
	private int totalPage;

	private int newsType;

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public int getNewsType() {
		return this.newsType;
	}

	@Override
	public String toString() {
		return "HeadDefault{" +
				"totalPage=" + totalPage +
				", newsType=" + newsType +
				'}';
	}
}