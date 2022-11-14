package org.line.core.engin;

/**
 * @Author: yangcs
 * @Date: 2022/11/8 15:54
 * @Description:
 */
public class AnalysisItemEnginPo extends BasicEnginPo {
    //放电时间
    private Double  dischargeTime;
    //二检说明
    private String  analysisExplain;

    public Double getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(Double dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getAnalysisExplain() {
        return analysisExplain;
    }

    public void setAnalysisExplain(String analysisExplain) {
        this.analysisExplain = analysisExplain;
    }
}



