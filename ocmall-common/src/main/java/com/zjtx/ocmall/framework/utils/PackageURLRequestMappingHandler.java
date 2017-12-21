package com.zjtx.ocmall.framework.utils;

import com.zjtx.ocmall.framework.constants.SysConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * RequestMapping处理器, 将Action类的包名转换成url的版本号.
 * 例：
 * 	packageBase: com.mobanker.tkj.api.action
 * 	replacement: v:|_:.|, 即v替换成空字符, 下划线替换成圆点
 * 	url root: /api/
 * 	com.mobanker.tkj.api.action.UserAction 类的RequestMapping值为:user, 某成员方法的RequestMapping值为: /getUser, 则 转换后的完整url为: /api/user/getUser
 * 	com.mobanker.tkj.api.action.v5_1_0.UserAction 类的RequestMapping值为user, 某成员方法的RequestMapping值为: /getUser, 则 转换后的完整url为: /api/5.1.0/user/getUser
 *
 * @author zhou shengzong
 *
 */
public class PackageURLRequestMappingHandler extends RequestMappingHandlerMapping {
    private String packageBase;
	/*
	 * format: $old1:$new1|$old2:$new2
	 */

    private String replacement = "v:|_:.|";

    /**
     * 包名前缀, 即包名截取该值后开始转换成url的path
     * @param packageBase
     */
    public void setPackageBase(String packageBase) {
        this.packageBase = (null == packageBase) ? null : packageBase.trim();
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        return super.createHandlerMethod(handler, method);
    }

    protected RequestMappingInfo createRequestMappingInfo(String pattern) {
        String[] patterns = (null == pattern) ? null
                : this.resolveEmbeddedValuesInPatterns(new String[] { pattern });
        return new RequestMappingInfo(new PatternsRequestCondition(patterns,
                this.getUrlPathHelper(), this.getPathMatcher(),
                this.useSuffixPatternMatch(), this.useTrailingSlashMatch(),
                this.getFileExtensions()), null, null, null, null, null,
                null);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

        if(null != info){
            StringBuilder prefix = new StringBuilder(SysConstants.ROOT_URL_PREFIX);
            Package pack = handlerType.getPackage();
            String fullName = pack.getName();
            String pbase = this.packageBase;

            if(StringUtils.containsIgnoreCase(handlerType.toString(), "login")){
                String[] names = fullName.split("\\.");
                String name = names[names.length-1];
                name = this.replace(name);
                prefix.append('/');
                prefix.append(name);


            }else if(null != pbase && !pbase.isEmpty() && 0 == fullName.indexOf(pbase)){
                pbase = pbase.endsWith(".") ? pbase : (pbase += '.');
                fullName = fullName.substring(this.packageBase.length());
                String[] names = fullName.split("\\.");
                for(String name : names){
                    name = this.replace(name);
                    prefix.append('/');
                    prefix.append(name);
                }
            }

            String prefixStr = prefix.toString().replace("//", "/");
            if(prefix.length() > 1){
                info = createRequestMappingInfo(prefixStr).combine(info);
            }
        }

        return info;
    }

    private String replace(String str) {
        if (null != str && !str.isEmpty() && null != replacement
                && !replacement.isEmpty()) {
            String[] segs = replacement.split("\\|");
            for (String seg : segs) {
                String[] pairs = seg.split(":");
                String oldVal = pairs[0];
                String newVal = "";
                if (pairs.length > 1) {
                    newVal = pairs[1];
                }
                str = str.replace(oldVal, newVal);
            }
        }

        return str;
    }
}
