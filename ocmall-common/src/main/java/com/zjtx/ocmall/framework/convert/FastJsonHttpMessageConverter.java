package com.zjtx.ocmall.framework.convert;

import com.alibaba.fastjson.serializer.EnumSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.zjtx.ocmall.framework.utils.ClassPathScanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Package Name: com.mobanker.ocmall.convert
 * Description:
 * Author: qiuyangjun
 * Create Date:2015/2/16
 */
public class FastJsonHttpMessageConverter extends com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter{
    /** Logger available to subclasses */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        /**
         * FastJson 1.1.42之前的版本反序列化对象时父对象属性无法被设值
         * 但是之后的版本Enum序列化使用的是toString方法
         * 临时解决方法强制使用Enum.name()方法序列化
         */
        List<String> classFilters = new ArrayList<String>();
        ClassPathScanHandler handler = new ClassPathScanHandler(true, true,classFilters);
        Set<Class<?>> calssList = handler.getPackageAllClasses("com.mobanker", true);
//        logger.debug("writeInternal calssList:{}",calssList);
        if(calssList!=null){
            for (Class<?> clazz : calssList) {
                if(clazz.isEnum() || (clazz.getSuperclass() != null && clazz.getSuperclass().isEnum())){
                    if(SerializeConfig.getGlobalInstance().get(clazz)==null) {
                        SerializeConfig.getGlobalInstance().put(clazz, EnumSerializer.instance);
                    }
                }
            }
        }
//        logger.debug("fastJson write object:{}",obj);
        try {
            super.writeInternal(obj, outputMessage);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
