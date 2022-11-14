package org.line.core.engin;

/**
 * @Author: yangcs
 * @Date: 2022/11/12 9:04
 * @Description:
 */
public class JsEnginParam {

    //js 内容
    private String jsStr;
    // 入参列表
    private Object[] args;

    public String getJsStr() {
        return jsStr;
    }

    public void setJsStr(String jsStr) {
        this.jsStr = jsStr;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
