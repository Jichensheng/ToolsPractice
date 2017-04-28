package com.heshun.retrofitrxjava.http;

/**
 *Json模板
 {
 "succ": true,
 "statusCode": 200,
 "msg": "消息",
 "data": {
 },
 "time": 1476842649455
 }

 错误码	原因
 400	服务器出了点小问题,请稍后重试
 402	参数错误
 1001	上传的文件大小超过最大限制
 1002	上传的文件后缀名不符
 1003	上传的文件已存在
 2001	所访问的资源已经被删除
 */
public class ApiException extends RuntimeException {

    public static final int ERROR_SERVER=400;
    public static final int ERROR_PARAM=402;
    public static final int ERROR_FILE_SIZE=1001;
    public static final int ERROR_FILE_EXTENSION=1002;
    public static final int ERROR_FILE_EXSIT=1003;
    public static final int ERROR_FILE_HASDELET=2001;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case ERROR_SERVER:
                message = "服务器出了点小问题,请稍后重试";
                break;
            case ERROR_PARAM:
                message = "参数错误";
                break;
            case ERROR_FILE_SIZE:
                message = "上传的文件大小超过最大限制";
                break;
            case ERROR_FILE_EXTENSION:
                message = "上传的文件后缀名不符";
                break;
            case ERROR_FILE_EXSIT:
                message = "上传的文件已存在";
                break;
            case ERROR_FILE_HASDELET:
                message = "所访问的资源已经被删除";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

