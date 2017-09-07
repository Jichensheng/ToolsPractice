package com.jcs.music.bean;

/**
 * author：Jics
 * 2017/9/1 14:08
 */
public class MockBean {
	public static MusicBean musicBean ;
	public static MusicBean musicBean2 ;
	public static MusicBean musicBean3 ;

	public static TalkBean talkBean1;
	public static TalkBean talkBean2;
	public static TalkBean talkBean3;

	static {
		musicBean=new MusicBean();
		musicBean.setAlbumid(1826345);
		musicBean.setAlbummid("001sP1d63Zutvt");
		musicBean.setAlbumpic_big("http://enjoy.eastday.com/images/thumbnailimg/month_1612/201612301254518061.jpg");
		musicBean.setAlbumpic_small("http://enjoy.eastday.com/images/thumbnailimg/month_1612/201612301254518061.jpg");
		musicBean.setCollected(false);
		musicBean.setId(1l);
		musicBean.setDownUrl("http://dl.stream.qqmusic.qq.com/200506552.mp3?vkey=B184A6356B726EDAF0F1A645833D6B4FA939EA201C7984D6F00FDD7FF2556C3C703B23688C0E5143E8306E12A6064E612FB4B9DDC36214E5&guid=2718671044");
		musicBean.setSeconds(148);
		musicBean.setSingerid(2);
		musicBean.setSingername("by2");
		musicBean.setSongid(23);
		musicBean.setSongname("桃花旗袍");
		musicBean.setType(32);
		musicBean.setUrl("http://ws.stream.qqmusic.qq.com/200881259.m4a?fromtag=46");

		musicBean2=new MusicBean();
		musicBean2.setAlbumid(1826345);
		musicBean2.setAlbummid("001sP1d63Zutvt");
		musicBean2.setAlbumpic_big("http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaMRAp9W0piavJxrfufkHEvz5UGF3j0Wd5usjpgDBI1BMd1IVtCm3SX2xERGcZs0PDL7UTLJicnw41kg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
		musicBean2.setAlbumpic_small("http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaMRAp9W0piavJxrfufkHEvz5UGF3j0Wd5usjpgDBI1BMd1IVtCm3SX2xERGcZs0PDL7UTLJicnw41kg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
		musicBean2.setCollected(false);
		musicBean2.setId(1l);
		musicBean2.setDownUrl("http://yinyueshiting.baidu.com/data2/music/3fc178034aaefdb951d2286856b5c2f2/551729590/2719870081504364461128.mp3?xcode=93ff5847552166b186c86b4d2d72cc37");
		musicBean2.setSeconds(148);
		musicBean2.setSingerid(2);
		musicBean2.setSingername("抖森森");
		musicBean2.setSongid(23);
		musicBean2.setSongname("Where Is Your City Of Stars？");
		musicBean2.setType(32);
		musicBean2.setUrl("https://res.wx.qq.com/voice/getvoice?mediaid=MzA4NTM3MTA2NV8yNjUxMzkzNDY1");
//		musicBean2.setUrl("http://yinyueshiting.baidu.com/data2/music/3fc178034aaefdb951d2286856b5c2f2/551729590/2719870081504364461128.mp3?xcode=93ff5847552166b186c86b4d2d72cc37");

		musicBean3=new MusicBean();
		musicBean3.setAlbumid(1826345);
		musicBean3.setAlbummid("001sP1d63Zutvt");
		musicBean3.setAlbumpic_big("https://y.gtimg.cn/music/photo_new/T002R300x300M000003DFRzD192KKD.jpg?max_age=2592000");
		musicBean3.setAlbumpic_small("https://y.gtimg.cn/music/photo_new/T002R300x300M000003DFRzD192KKD.jpg?max_age=2592000");
		musicBean3.setCollected(false);
		musicBean3.setId(1l);
		musicBean3.setDownUrl("http://dl.stream.qqmusic.qq.com/200506552.mp3?vkey=B184A6356B726EDAF0F1A645833D6B4FA939EA201C7984D6F00FDD7FF2556C3C703B23688C0E5143E8306E12A6064E612FB4B9DDC36214E5&guid=2718671044");
		musicBean3.setSeconds(148);
		musicBean3.setSingerid(2);
		musicBean3.setSingername("周杰伦");
		musicBean3.setSongid(23);
		musicBean3.setSongname("七里香");
		musicBean3.setType(32);
		musicBean3.setUrl("http://yinyueshiting.baidu.com/data2/music/5539cec9b92f30db8a1cf0159e3b325f/551729501/2719319951504346461128mp3?xcode=93ff5847552166b137ff48ca31f559c2");

		/**
		 private String title;
		 private int articleId;
		 private String author;
		 private String dj;//电台主播
		 private String url;//电台音频地址
		 private String image;
		 private String excerpt;
		 private int praise;
		 */

		talkBean1=new TalkBean(
				"Where Is Your City Of Stars？",
				5352,
				"文章作者",
				"抖森森",
				"https://res.wx.qq.com/voice/getvoice?mediaid=MzA4NTM3MTA2NV8yNjUxMzkzNDY1",
				"http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaP7ea8dpJNLSUMLW8LGJPHzU3WEBlPJ7pFtMSb8tLSPJwBib8bic9dbadjb7r5gKvQVrDBc5DxCibVXA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
				"每个拥有过梦想的人都会懂。事实上，洛杉矶也只不过是city of stars 的一",
				198
		);
		talkBean2=new TalkBean(
				"桃花旗袍",
				5352,
				"文章作者",
				"By2",
				"http://ws.stream.qqmusic.qq.com/200881259.m4a?fromtag=46",
				"http://enjoy.eastday.com/images/thumbnailimg/month_1612/201612301254518061.jpg",
				"你曾经想成为一名演员么？很多人啊，都去La La Land追寻演员梦，可是",
				100
		);
		talkBean3=new TalkBean(
				"平如美棠",
				5352,
				"文章作者",
				"周杰伦",
				"https://res.wx.qq.com/voice/getvoice?mediaid=MzA4NTM3MTA2NV8yNjUxMzkxODkx",
				"http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaOgYc9iayiajNeibnsxuvpzicvThdNCS1ibtIDQZRAZ8CJGJ2fia3X7L3qic440AV0CSAxkIaeDFl6W5hdOQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1",
				"年少谈恋爱的时候，我们都衣食无忧。那时美棠便同我讲，情愿两人在乡间找一处僻静地方，有一片自己的园地，",
				100
		);
	}
}
