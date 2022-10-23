package com.snail.officialaccount.constant;

import java.util.Objects;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 22:47
 */
public enum NotifyEnum {

    //菜单点击事件
    CLICK_EVENT("event", "click"),
    //关注
    SUBSCRIBE_EVENT("event", "subscribe"),
    //取关
    UNSUBSCRIBE_EVENT("event", "unsubscribe"),
    //已关注时的扫码事件
    SCAN_EVENT("event", "scan"),
    //上报地理位置事件
    LOCATION_EVENT("event", "LOCATION"),
    //点击菜单跳转链接时的事件推送
    VIEW_EVENT("event", "VIEW"),


    //文字消息回复
    TEXT("text", null),
    //图片消息
    IMAGE("image", null),
    //语音消息
    VOICE("voice", null),
    //视频消息
    VIDEO("video", null),
    //小视频
    SHORTVIDEO("shortvideo", null),
    //地理消息
    LOCATION("location", null),
    //链接
    LINK("link", null),
    ;


    /**
     * 消息类型
     */
    private final String msgType;
    /**
     * 事件
     */
    private final String event;

    NotifyEnum(String msgType, String event) {
        this.msgType = msgType;
        this.event = event;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getEvent() {
        return this.event;
    }

    /**
     * 解析事件类型
     *
     * @param msgType
     * @return
     */
    public static NotifyEnum resolveEvent(String msgType) {
        for (NotifyEnum notifyEnum : NotifyEnum.values()) {
            if (Objects.equals(msgType, notifyEnum.getMsgType())) {
                return notifyEnum;
            }
        }
        return null;
    }
}
