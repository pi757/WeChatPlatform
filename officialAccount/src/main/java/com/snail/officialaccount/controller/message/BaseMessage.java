package com.snail.officialaccount.controller.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/21 23:42
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
public class BaseMessage {

    // 接收方帐号（收到的OpenID）
    @XStreamAlias("ToUserName")
    private String toUserName;

    // 开发者微信号
    @XStreamAlias("FromUserName")
    private String fromUserName;

    // 消息创建时间 （整型）
    @XStreamAlias("CreateTime")
    private Integer createTime;

    // 消息类型
    @XStreamAlias("MsgType")
    private String msgType;

    public BaseMessage(Map<String, String> requestMap) {
        this.toUserName = requestMap.get("FromUserName");
        this.fromUserName = requestMap.get("ToUserName");
        this.createTime = (int) System.currentTimeMillis();
    }

}
