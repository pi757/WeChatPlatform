package com.snail.officialaccount.constant;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 0:00
 */
public class WXConstant {


    public static final String SUCCESS = "success";
    /**
     * 微信公众号access_token_key 用于保存在redis中的key
     */
    public static final String ACCESS_TOKEN_KEY = "wechat:accessToken:%s";

    /**
     * 获取微信公众号的access_token
     */
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

}
