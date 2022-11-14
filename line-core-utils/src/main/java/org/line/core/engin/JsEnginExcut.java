package org.line.core.engin;

import com.hand.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2022/11/8 15:51
 * @Description:
 */
public class JsEnginExcut {

    private static String regExpStr = "function jsRegExp(str,regExp){ var reg = new RegExp(regExp) ; return reg.test(str)  }";

    private static final Logger logger = LogManager.getLogger(JsEnginExcut.class);

    /**
     * @param rules 规则集合
     * @description 处理校验-执行全部通过与否逻辑
     */
    public static boolean execRules(List<ServiceEngineRulePo> rules, BasicEnginPo basicEnginPo) {
        if (CollectionUtils.isEmpty(rules)) {
            logger.info("JsEnginExcut.execRules; 校验规则集合为空，直接返回true");
            return Boolean.TRUE;
        }
        Integer alls = rules.get(0).getAlls();
        if (alls == null) {
            throw new BusinessException("规则配置alls为null!");
        }
        //true:全部通过才返回true,
        return alls.equals(1) ? doAllSuccess(rules, basicEnginPo) : doAnySuccess(rules, basicEnginPo);
    }


    private static boolean doAllSuccess(List<ServiceEngineRulePo> rules, BasicEnginPo basicEnginPo) {
        for (ServiceEngineRulePo rule : rules) {
            //是否校验成功
            boolean reFlag = execRules(rule, basicEnginPo);
            //必须全部通过, 有一个校验结果为false,则返回false
            if (!reFlag) {
                return false;
            }
        }
        return true;
    }

    private static boolean doAnySuccess(List<ServiceEngineRulePo> rules, BasicEnginPo basicEnginPo) {
        for (ServiceEngineRulePo rule : rules) {
            //是否校验成功
            boolean reFlag = execRules(rule, basicEnginPo);
            //不必全部通过，有一个校验结果为true,则返回true
            if (reFlag) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param rule 规则集合
     * @description 组装 JsEnginParam
     * 处理校验-执行方法/正则逻辑
     */
    public static boolean execRules(ServiceEngineRulePo rule, BasicEnginPo basicEnginPo) {
        JsEnginParam param = new JsEnginParam();
        //字段名是字符串
        String[] split = rule.getTargetField().split(",");
        //字段值Object
        Object[] values = new Object[split.length];

        //获取配置字段中的实际值
        for (int i = 0; i < split.length; i++) {
            Object value = ReflectionUtils.getFieldValue(split[i + 1], basicEnginPo);
            values[i + 1] = value;
        }

        Object[] args = new Object[split.length + 1];
        String codeType = rule.getCodeType();
        //内容类型 ，funct : 方法 ， regex：正则
        //方法应是高度自由，入参应该不定数组，整个方法都需要自己实现
        if ("funct".equals(codeType)) {
            param.setJsStr(rule.getCodeContent());
        } else if ("regex".equals(codeType)) {
            param.setJsStr(regExpStr);
            //正则内容作为最后一个入参
            System.arraycopy(values, 0, args, 0,
                    values.length);
            args[args.length] = rule.getCodeContent();
        } else {
            throw new BusinessException("非法配置 codeType = [" + codeType + "]");
        }
        param.setArgs(args);
        return JsEnginUtils.exec(param);
    }

}
