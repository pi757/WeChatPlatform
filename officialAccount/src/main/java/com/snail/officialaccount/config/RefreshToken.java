package com.snail.officialaccount.config;

import com.snail.officialaccount.service.impl.biz.WXService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/21 0:12
 */
@Component
public class RefreshToken implements InitializingBean {
    @Resource
    private WXService wxService;

    /**
     * 刷新token的定时线程
     */
    private ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("refresh-wx-access-token-%d").daemon(true).build());


    @Override
    public void afterPropertiesSet() {
        scheduledPool.scheduleAtFixedRate(() -> wxService.refreshToken(),0, 7000, TimeUnit.SECONDS);
    }
}
