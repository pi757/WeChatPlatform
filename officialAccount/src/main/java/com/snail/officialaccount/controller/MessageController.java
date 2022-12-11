package com.snail.officialaccount.controller;

import com.snail.officialaccount.config.WeChatConfig;
import com.snail.officialaccount.constant.NotifyEnum;
import com.snail.officialaccount.constant.WXConstant;
import com.snail.officialaccount.controller.message.BaseMessage;
import com.snail.officialaccount.controller.message.TextMessage;
import com.snail.officialaccount.service.MessageService;
import com.snail.officialaccount.service.impl.biz.NotifyFactory;
import com.snail.officialaccount.util.XmlUtil;
import com.snail.officialaccount.util.mp.AesException;
import com.snail.officialaccount.util.mp.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/18 22:34
 */
@RestController
@Slf4j
@RequestMapping
public class MessageController {

    @Resource
    private NotifyFactory notifyFactory;

    @Resource
    private WeChatConfig weChatConfig;


    @RequestMapping(value = "/wechat")
    public String textMessage(@RequestParam("timestamp") String timestamp,
                              @RequestParam("nonce") String nonce,
                              @RequestParam(value = "msg_signature",required = false) String msgSignature,
                              @RequestParam(value = "echostr", required = false) String echostr,
                              @RequestBody(required = false) String postData) {
        if(StringUtils.isEmpty(postData)) {
            //此处用于公众平台配置的初步接入
            return echostr;
        }
        WXBizMsgCrypt pc = weChatConfig.getWxBizMsgCrypt();
        //签名校验 数据解密
        String decryptXml;
        try {
            decryptXml = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
        } catch (AesException e) {
            log.error("消息解密异常");
            throw new RuntimeException(e);
        }
        log.info("解密后消息：{}", decryptXml);
        Map<String, String> stringMap = XmlUtil.xmlToMap(decryptXml);
        //获取推送事件类型  可以拿到的事件: 1 关注/取消关注事件  2:扫描带参数二维码事件 3: 用户已经关注公众号 扫描带参数二维码事件 ...等等
        String msgId = stringMap.get("MsgId");
        NotifyEnum notifyEnum = NotifyEnum.resolveEvent(stringMap.get("MsgType"));
        if (notifyEnum == null) {
            log.error("没有定义该类型处理事件：{}", stringMap);
            return WXConstant.SUCCESS;
        }
        log.info("触发事件：{}", notifyEnum.getMsgType());
        MessageService infoType = notifyFactory.loadWeChatNotify(notifyEnum);
        //执行具体的策略 得到给微信的响应信息 微信有重试机制  需要考虑幂等性
        BaseMessage baseMessage = infoType.messageHandle(stringMap);
        String result = "success";
        if (baseMessage != null) {
        result = beanToXml(baseMessage);
        }
        log.info("Msg响应的POST结果: 授权策略对象: [{}] 解密后信息: [{}] 返回给微信的信息: [{}]", infoType.getClass().getSimpleName(), stringMap, result);
        return result;
    }

    /**
     * 将消息对象转换成xml
     *
     * @param msg
     * @return
     */
    private String beanToXml(BaseMessage msg) {
        XStream xStream = new XStream();
        xStream.processAnnotations(new Class[]{TextMessage.class});
        return xStream.toXML(msg);
    }
}
