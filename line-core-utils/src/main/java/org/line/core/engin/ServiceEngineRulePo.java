package org.line.core.engin;

/**
 * @Author: yangcs
 * @Date: 2022/11/12 9:54
 * @Description:
 */
public class ServiceEngineRulePo {

    // 规则编码，可能多次出现，他们是一组校验规则
    private String engineRuleCode;
    //目标字段
    private String targetField;
    //需要被引擎执行的内容
    private String codeContent;
    //内容类型 ，funct : 方法 ， regex：正则
    private String codeType;
    //排序号
    private String topNumb;
    //全都通过 1：是   0 ：否
    private Integer alls;


    public String getEngineRuleCode() {
        return engineRuleCode;
    }

    public void setEngineRuleCode(String engineRuleCode) {
        this.engineRuleCode = engineRuleCode;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getTopNumb() {
        return topNumb;
    }

    public void setTopNumb(String topNumb) {
        this.topNumb = topNumb;
    }

    public Integer getAlls() {
        return alls;
    }

    public void setAlls(Integer alls) {
        this.alls = alls;
    }
}
