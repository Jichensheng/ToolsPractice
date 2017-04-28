package com.heshun.retrofitrxjavaStep8.entity;

public class HeadTest {
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
		return "HeadTest{" +
				"totalPage=" + totalPage +
				", newsType=" + newsType +
				'}';
	}
}