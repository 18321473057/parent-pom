package org.line.core.premain;

import java.lang.instrument.Instrumentation;

public class PreMainAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
         //加入自定义转换器
        inst.addTransformer(new MyTransformer(), true); 
    }

}