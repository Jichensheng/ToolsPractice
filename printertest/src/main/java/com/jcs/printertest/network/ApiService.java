package com.jcs.printertest.network;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author：Jics
 * 2017/7/31 13:29
 */
public interface ApiService {
	/**
	 *
	 * @param page 分页中的第page页
	 * @param count 每页个数
	 * @return
	 */
	@GET("magazine/cover/{page}/{count}")
	Observable<ResponseBody> getMagazineCover(@Path("page") int page, @Path("count") int count);

	/**
	 *
	 * @param id 某期杂志的编号
	 * @return
	 */
	@GET("magazine/contents/{volId}")
	Observable<ResponseBody> getContents(@Path("volId") int id);

	/**
	 *
	 * @param id 文章id获取文章正文
	 * @return
	 */
	@GET("magazine/article/{articleId}")
	Observable<ResponseBody> getArticle(@Path("articleId") String id);

	/**
	 *
	 * @param versionNo 本地版本号
	 * @return
	 */
	@GET("magazine/version/{versionNo}")
	Observable<ResponseBody> checkUpdata(@Path("articleId") String versionNo);



}
