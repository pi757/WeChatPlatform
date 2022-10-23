package com.snail.officialaccount.config;

import com.snail.officialaccount.util.mp.WXBizMsgCrypt;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/18 23:57
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig implements InitializingBean {
    private String appID;
    private String secret;
    private String token;
    private String encodingAESKey;
    /**
     * 微信加解密工具
     */
    private WXBizMsgCrypt wxBizMsgCrypt;

    /**
     * 创建全局唯一的微信加解密工具
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        wxBizMsgCrypt = new WXBizMsgCrypt(this.token, this.encodingAESKey, this.appID);
    }
}
