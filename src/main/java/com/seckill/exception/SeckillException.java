package com.seckill.exception;

/**
 * 文件描述：秒杀相关的业务异常
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 13:00
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
