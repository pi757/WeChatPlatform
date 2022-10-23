package com.snail.officialaccount.service.impl.biz;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.snail.officialaccount.config.WeChatConfig;
import com.snail.officialaccount.constant.WXConstant;
import com.snail.officialaccount.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 23:01
 */
@Slf4j
@Component
public class WXService {

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 刷新微信公众号的access_token
     * https请求:
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * 微信返回数据:
     * {"access_token":"ACCESS_TOKEN","expires_in":7200}
     *
     * @return
     */
    public String refreshToken() {
        String redisKey = String.format(WXConstant.ACCESS_TOKEN_KEY, weChatConfig.getAppID());
        String url = String.format(WXConstant.WX_ACCESS_TOKEN_URL, weChatConfig.getAppID(), weChatConfig.getSecret());
        //HttpClient工具根据项目自行修改
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "");
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        OkHttpClient httpClient = HttpUtil.getInstance();
        JSONObject result = new JSONObject();
        Response response;
        try {
            response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("微信获取access_token返回异常：{}；{}", response.code(), response.message());
                throw new RuntimeException("微信获取access_token返回异常");
            }

        } catch (Exception e) {
            log.error("请求微信access_token失败", e);
            throw new RuntimeException("请求微信access_token失败");
        }
        String jsonString = "";
        try (ResponseBody body = response.body()) {
            jsonString = new String(body.bytes(), StandardCharsets.UTF_8);
            result = JSONObject.parseObject(jsonString);
        } catch (Exception e) {
            log.error("解析微信access_token响应失败:{}", jsonString, e);
            throw new RuntimeException("解析微信access_token响应失败");
        }
        log.info("获取微信公众号的access_token: {}", result);
        String accessToken = result.getString("access_token");
        if (Strings.isBlank(accessToken)) {
            log.error("获取access_token失败，错误原因：{}", result);
            throw new RuntimeException("解析微信access_token解析失败");
        }
        //redis工具根据项目自行修改
        redisTemplate.opsForValue().set(redisKey, accessToken, 7200, TimeUnit.MICROSECONDS);
        return accessToken;
    }
}
