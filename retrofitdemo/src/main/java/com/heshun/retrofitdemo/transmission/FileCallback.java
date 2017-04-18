package com.heshun.retrofitdemo.transmission;

import com.heshun.retrofitdemo.http.RxBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Retrofit 文件下载回调处理核心
 * 使用时：有三个方法需要我们自己实现或重写
 * 分别是onSuccess()，onFailure()，progress()
 * progress()方法有两个参数，progress和total,分别表示文件已下载的大小和总大小
 * 我们只需要将这两个参数不断更新到UI上即可
 */
public abstract class FileCallback implements Callback<ResponseBody> {

	/**
	 * 订阅下载进度
	 * 统一管理订阅与取消
	 */
	private CompositeSubscription rxSubscriptions = new CompositeSubscription();
	/**
	 * 目标文件存储的文件夹路径
	 */
	private String destFileDir;
	/**
	 * 目标文件存储的文件名
	 */
	private String destFileName;

	public FileCallback(String destFileDir, String destFileName) {
		this.destFileDir = destFileDir;
		this.destFileName = destFileName;
		subscribeLoadProgress();
	}

	public void onSuccess(File file) {
		unsubscribe();
	}

	public abstract void progress(long progress, long total);

	//接收http响应的回调
	@Override
	public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
		try {
			saveFile(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public File saveFile(Response<ResponseBody> response) throws IOException {
		InputStream is = null;
		byte[] buf = new byte[2048];
		int len;
		FileOutputStream fos = null;
		try {
			is = response.body().byteStream();
			File dir = new File(destFileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, destFileName);
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			onSuccess(file);
			return file;
		} finally {
			try {
				if (is != null) is.close();
			} catch (IOException e) {
			}
			try {
				if (fos != null) fos.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 订阅文件下载进度
	 */
	private void subscribeLoadProgress() {
		//使用CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅
		rxSubscriptions.add(RxBus.getDefault()
				.toObservable(FileLoadEvent.class)
				//onBackpressureBuffer 会缓存所有当前无法消费的数据，直到 Observer 可以处理为止。
				// 可以指定缓冲的数量，如果缓冲满了则会导致数据流失败
				// onBackpressureBuffer操作符将监管Observable何时发射数据
				// 如果Observable比观察者接收的数据要更快的话，它必须把它们存储在缓存中并提供一个合适的时间发射数据
				.onBackpressureBuffer()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<FileLoadEvent>() {
					@Override
					public void call(FileLoadEvent fileLoadEvent) {
						/**
						 * progress()方法在是在RxBus收到FileLoadEvent后才调用的
						 * 1）RxBus发送FileLoadEvent对象；
						 * 2）FileLoadEvent中包含当前下载进度和文件总大小；
						 * 3）FileCallback订阅RxBus发送的FileLoadEvent；
						 * 4）根据接收到的FileLoadEvent更新下载Dialog的UI。
						 * FileResponseBody中发送FukeKiadEvent
						 */
						progress(fileLoadEvent.getProgress(), fileLoadEvent.getTotal());
					}
				}));
	}

	/**
	 * 取消订阅，防止内存泄漏
	 */
	private void unsubscribe() {
		if (!rxSubscriptions.isUnsubscribed()) {
			rxSubscriptions.unsubscribe();
		}
	}

}
