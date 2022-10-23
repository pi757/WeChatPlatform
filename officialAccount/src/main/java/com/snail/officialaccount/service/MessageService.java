package com.snail.officialaccount.service;

import com.alibaba.fastjson2.JSONObject;
import com.snail.officialaccount.controller.message.BaseMessage;

import java.util.Map;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/18 22:35
 */
public interface MessageService {

    BaseMessage messageHandle(Map<String, String> requestJson);

}
