package com.zjtx.ocmall.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {
    private static Map ctxPropertiesMap;

    public PropertyPlaceholderConfigurer() {
    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap();
        Iterator i$ = props.keySet().iterator();

        while(i$.hasNext()) {
            Object key = i$.next();
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }

    }

    /** @deprecated */
    @Deprecated
    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}

