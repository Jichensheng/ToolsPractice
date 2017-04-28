package com.heshun.retrofitrxjava.entity;

/**
 * author：Jics
 * 2017/4/27 13:49
 */
public class Pic {
	private int orgId;

	private String thumImage;

	private String indexImage;

	private String title;

	private String content;

	private int clickCount;

	private int isindex;

	private String link;

	private int newsType;

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getOrgId() {
		return this.orgId;
	}

	public void setThumImage(String thumImage) {
		this.thumImage = thumImage;
	}

	public String getThumImage() {
		return this.thumImage;
	}

	public void setIndexImage(String indexImage) {
		this.indexImage = indexImage;
	}

	public String getIndexImage() {
		return this.indexImage;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public int getClickCount() {
		return this.clickCount;
	}

	public void setIsindex(int isindex) {
		this.isindex = isindex;
	}

	public int getIsindex() {
		return this.isindex;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return this.link;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public int getNewsType() {
		return this.newsType;
	}

	@Override
	public String toString() {
		return "Pic{" +
				"orgId=" + orgId +
				", thumImage='" + thumImage + '\'' +
				", indexImage='" + indexImage + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", clickCount=" + clickCount +
				", isindex=" + isindex +
				", link='" + link + '\'' +
				", newsType=" + newsType +
				'}';
	}
}

