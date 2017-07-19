package com.seckill.exception;

/**
 * 文件描述：重复秒杀异常
 * 创建作者：陈苗
 * 创建时间：2016年5月28日 12:57
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
