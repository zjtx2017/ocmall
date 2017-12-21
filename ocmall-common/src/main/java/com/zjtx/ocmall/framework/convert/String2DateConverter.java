package com.zjtx.ocmall.framework.convert;

import com.zjtx.ocmall.framework.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class String2DateConverter implements Converter<String, Date> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Date convert(String source) {
        logger.debug("=====================String2DateConverter InIt===============");
        if(StringUtils.isBlank(source)){
            return null;
        }
        return  DateUtils.convert(source);
    }
}
