package com.snail.officialaccount.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @version 1.0
 * @Author jiaSong.pi
 * @Date 2022/9/19 22:28
 */
@Slf4j
public class XmlUtil {


    /**
     * @param xml 要转换的xml字符串
     * @return 转换成map后返回结果
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> respMap;
        try {
            respMap = new HashMap<>();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            Element root = doc.getRootElement();
            xmlToMap(root, respMap);
        } catch (Exception e) {
            log.error("xml转换异常");
            throw new RuntimeException("xml转换异常");
        }
        return respMap;
    }

    /**
     * map对象转行成xml
     *
     * @param map
     * @return
     * @throws IOException
     */
    public static String mapToXml(Map<String, Object> map) {
        StringWriter sw = null;
        try {
            Document d = DocumentHelper.createDocument();
            Element root = d.addElement("xml");
            mapToXml(root, map);
            sw = new StringWriter();
            XMLWriter xw = new XMLWriter(sw);
            xw.setEscapeText(false);
            xw.write(d);
        } catch (Exception e) {
            log.error("转换成xml异常");
            throw new RuntimeException("转换成xml异常");
        }
        return sw.toString();
    }


    /**
     * 递归转换
     *
     * @param root
     * @param map
     * @return
     * @throws IOException
     */
    private static Element mapToXml(Element root, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Element element = root.addElement(entry.getKey());
                mapToXml(element, (Map<String, Object>) entry.getValue());
            } else {
                root.addElement(entry.getKey()).addText(entry.getValue().toString());
            }
        }
        return root;
    }


    /**
     * 递归转换
     *
     * @param tmpElement
     * @param respMap
     * @return
     */
    private static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> respMap) {
        if (tmpElement.isTextOnly()) {
            respMap.put(tmpElement.getName(), tmpElement.getText());
            return respMap;
        }
        Iterator<Element> eItor = tmpElement.elementIterator();
        while (eItor.hasNext()) {
            Element element = eItor.next();
            xmlToMap(element, respMap);
        }
        return respMap;
    }
}
