package com.seckill.exception;

/**
 * 文件描述：秒杀关闭异常
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 12:59
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
