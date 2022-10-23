package com.snail.officialaccount.service.impl.biz;

import com.alibaba.fastjson2.JSONObject;
import com.snail.officialaccount.constant.NotifyEnum;
import com.snail.officialaccount.constant.NotifyType;
import com.snail.officialaccount.controller.message.BaseMessage;
import com.snail.officialaccount.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 22:45
 */
@Slf4j
@Component
public class NotifyFactory implements ApplicationContextAware {

    /**
     * 策略列表
     */
    private final Map<NotifyEnum, MessageService> notifyMap = new HashMap<>();

    /**
     * 工厂获取事件执行策略对象
     *
     * @param notifyType
     * @return
     */
    public MessageService loadWeChatNotify(NotifyEnum notifyType) {
        MessageService notify = notifyMap.get(notifyType);
        //对于没配置的策略 返回一个默认的空实现即可
        return Optional.ofNullable(notify).orElse(this::defaultNotify);
    }

    /**
     * 工厂提供默认空实现
     *
     * @param json
     * @return
     */
    public BaseMessage defaultNotify(Map<String, String> json) {
        return null;
    }


    /**
     * 扫描带有NotifyType注解的bean组装成map
     * 新加策略时 在类上加入注解@NotifyType(...)即可
     * 支持枚举多个策略事件
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> notifyBeanMap = applicationContext.getBeansWithAnnotation(NotifyType.class);
        Map<NotifyEnum[], MessageService> annoValueBeanMap = notifyBeanMap.values().stream()
                .filter(obj -> ArrayUtils.contains(obj.getClass().getInterfaces(), MessageService.class))
                .map(obj -> (MessageService) obj)
                .collect(Collectors.toMap(obj -> obj.getClass().getAnnotation(NotifyType.class).value(), Function.identity()));
        annoValueBeanMap.forEach((key, value) -> Arrays.stream(key).forEach(type -> notifyMap.put(type, value)));
    }
}