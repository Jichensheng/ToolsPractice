package com.heshun.retrofitdemo.transmission;

/**
 * 下载事件
 */
public class FileLoadEvent {
    /**
     * 文件大小
     */
    long total;
    /**
     * 已下载(上传)大小
     */
    long progress;

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
