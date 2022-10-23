package com.snail.officialaccount.service.impl.biz;

import com.alibaba.fastjson2.JSONObject;
import com.snail.officialaccount.constant.NotifyEnum;
import com.snail.officialaccount.constant.NotifyType;
import com.snail.officialaccount.controller.message.BaseMessage;
import com.snail.officialaccount.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**取关
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 22:55
 */
@Slf4j
@NotifyType(NotifyEnum.UNSUBSCRIBE_EVENT)
public class UnsubscribeEventServiceImpl implements MessageService {
    @Override
    public BaseMessage messageHandle(Map<String, String> requestMap) {
        return null;
    }
}
