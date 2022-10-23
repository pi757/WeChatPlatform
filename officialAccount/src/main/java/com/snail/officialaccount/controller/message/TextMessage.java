package com.snail.officialaccount.controller.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/21 23:45
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
public class TextMessage extends BaseMessage{
    // 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
    @XStreamAlias("Content")
    private String content;

    public TextMessage(Map<String, String> requestMap, String content) {
        super(requestMap);
        // 设置文本msgType为text
        this.setMsgType("text");
        this.content = content;
    }
}
