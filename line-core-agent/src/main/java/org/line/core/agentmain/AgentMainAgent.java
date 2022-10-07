package org.line.core.agentmain;

import org.line.core.premain.MyTransformer;
import org.line.core.service.FooService;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentMainAgent {

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        instrumentation.addTransformer(new MyTransformer(), true);
        instrumentation.retransformClasses(FooService. class); //指明哪些类需要重新加载
    }
    
}