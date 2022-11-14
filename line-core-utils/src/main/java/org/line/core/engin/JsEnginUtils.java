package org.line.core.engin;

import com.alibaba.fastjson.JSONObject;
import com.hand.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.*;

/**
 * @Author: yangcs
 * @Date: 2022/11/8 15:51
 * @Description:
 */
public class JsEnginUtils {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
    private static Compilable compEngine = (Compilable) engine;
    private static Invocable invoke = (Invocable) engine;


    private static final Logger logger = LogManager.getLogger(JsEnginUtils.class);


    /**
     * @param param.jsStr js内容
     * @param param.args  入参列表
     * @description 实际执行js引擎方法
     */
    public static boolean exec(JsEnginParam param) {
        logger.info("JsEnginUtils.exec  param = [{}]", JSONObject.toJSON(param));
        CompiledScript script;
        try {
            script = compEngine.compile(param.getJsStr());
            script.eval();
            Object maxNum = invoke.invokeFunction("jsFunc", param.getArgs());
            return Boolean.class.cast(maxNum);
        } catch (ScriptException e) {
            logger.error("JsCode编译异常，请检查配置信息。param = [{}]", JSONObject.toJSON(param));
            throw new BusinessException("JsCode编译异常，请检查配置信息");
        } catch (NoSuchMethodException e) {
            logger.error("JsCode运行异常，请检查配置信息。param = [{}]", JSONObject.toJSON(param));
            throw new BusinessException("JsCode运行异常，请检查配置信息");
        }
    }

}
