package org.line.core.engin;

import com.alibaba.fastjson.JSONObject;
import com.hand.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * @Author: yangcs
 * @Date: 2022/11/8 15:51
 * @Description:
 */
public class ReflectionUtils {

    private static final Logger logger = LogManager.getLogger(ReflectionUtils.class);


    public static Object getFieldValue(String fieldName, BasicEnginPo basicEnginPo) {
        Class<? extends BasicEnginPo> enginPoClass = basicEnginPo.getClass();
        Field field = null;
        try {
            field = enginPoClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            logger.error("反射获取字段失败，请检查配置 fieldName=[{}],po = [{}], e={}", fieldName, JSONObject.toJSON(basicEnginPo), e);
            throw new BusinessException("反射获取字段失败，请检查配置 ");
        }
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(basicEnginPo);
        } catch (IllegalAccessException e) {
            logger.error("反射获取字段值失败，请检查配置 fieldName=[{}],po = [{}], e={}", fieldName, JSONObject.toJSON(basicEnginPo), e);
            throw new BusinessException("反射获取字段值失败，请检查配置 ");
        }

        if(value == null){
            logger.error("反射获取字段值为null", fieldName, JSONObject.toJSON(basicEnginPo));
        }

        return value;
    }
}
