package com.seckill.exception;

/**
 * 文件描述：自定义异常
 * 创建作者：陈苗
 * 创建时间：2016年6月3日 22:33
 */
public class CustomException extends Exception {
    /**
     * 构造函数
     * @param message 错误信息内容
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * 获取错误信息
     * @return 错误信息内容
     */
    public String getMessage() {
        return super.getMessage();
    }
}
