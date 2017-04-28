package com.heshun.retrofitrxjava.entity.stable;

/**
    Json模板
	 {
	 "succ": true,
	 "statusCode": 200,
	 "msg": "消息",
	 "data": {
         "head": { },
         "body": [ ]
	 },
	 "time": 1476842649455
	 }
 * head和body字段数据类型不固定，所以采用泛型
 * @param <T> head
 * @param <E> body 多数情况下是entity的集合
 */
public class HttpResult<T,E> {

    private boolean succ;
    private int statusCode;
    private String msg;
    private Long time;

    private Data<T, E> data;

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Data<T, E> getData() {
        return data;
    }

    public void setData(Data<T, E> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "time=" + time +
                ", msg='" + msg + '\'' +
                ", statusCode=" + statusCode +
                ", succ=" + succ +
                '}';
    }
}
