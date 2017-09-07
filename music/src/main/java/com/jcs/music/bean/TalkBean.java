package com.jcs.music.bean;

/**
 * author：Jics
 * 2017/9/3 12:57
 */
public class TalkBean extends BaseBean {
	/**
	 * title : 白日梦
	 * contentsId : 530101
	 * author : 正南七白
	 * image : http: //xxx.xxx.xxx/xxx
	 * excerpt : 他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。
	 * praise : 999
	 */

	private String title;
	private int articleId;
	private String author;
	private String dj;//电台主播
	private String url;//电台音频地址
	private String image;
	private String excerpt;
	private int praise;

	public TalkBean(String title, int articleId, String author, String dj, String url, String image, String excerpt, int praise) {
		this.title = title;
		this.articleId = articleId;
		this.author = author;
		this.dj = dj;
		this.url = url;
		this.image = image;
		this.excerpt = excerpt;
		this.praise = praise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}
}
