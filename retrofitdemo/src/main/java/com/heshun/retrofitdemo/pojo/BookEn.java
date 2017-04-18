package com.heshun.retrofitdemo.pojo;

import java.util.List;

/**
 * author：Jics
 * 2016/10/18 11:02
 */
public class BookEn {
	 String pubdate;
	 String id;
	 String publisher;
	 String title;
	 String author_intro;
	 public String summary;
	 ImagesEn images;
	List<Tags> tags;

	public List<Tags> getTags() {
		return tags;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public ImagesEn getImages() {
		return images;
	}

	public void setImages(ImagesEn images) {
		this.images = images;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor_intro() {
		return author_intro;
	}

	public void setAuthor_intro(String author_intro) {
		this.author_intro = author_intro;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
