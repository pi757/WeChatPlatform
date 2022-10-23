package com.snail.officialaccount.constant;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 22:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface NotifyType {

    /**
     * 事件推送的类型 支持枚举多个事件
     * @return
     */
    NotifyEnum[] value();
}
