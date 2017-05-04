package com.heshun.rrdownload.network.download;

/**
 * 下载进度listener
 * Created by Jcs on 16/5/11.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
