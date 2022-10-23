package com.snail.officialaccount.service.impl.biz;

import com.alibaba.fastjson2.JSONObject;
import com.snail.officialaccount.constant.NotifyEnum;
import com.snail.officialaccount.constant.NotifyType;
import com.snail.officialaccount.constant.WXConstant;
import com.snail.officialaccount.controller.message.BaseMessage;
import com.snail.officialaccount.controller.message.TextMessage;
import com.snail.officialaccount.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/** 文本
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 23:26
 */
@Slf4j
@NotifyType(NotifyEnum.TEXT)
public class TextServiceImpl implements MessageService {
    @Override
    public BaseMessage messageHandle(Map<String, String> requestMap) {
        return new TextMessage(requestMap, "收到，over!");
    }
}
