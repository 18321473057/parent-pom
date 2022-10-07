package org.line.core.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.*;


//通过ClassFileTransformer接口，可以在类加载之前，重写字节码
public class MyTransformer implements ClassFileTransformer {

    /**
     * 参数：
     * loader - 定义要转换的类加载器；如果是引导加载器，则为 null
     * className - 完全限定类内部形式的类名称和 The Java Virtual Machine Specification 中定义的接口名称。例如，"java/util/List"。
     * classBeingRedefined - 如果是被重定义或重转换触发，则为重定义或重转换的类；如果是类加载，则为 null
     * protectionDomain - 要定义或重定义的类的保护域
     * classfileBuffer - 类文件格式的输入字节缓冲区（不得修改）
     * 返回：
     * 一个格式良好的类文件缓冲区（转换的结果），如果未执行转换,则返回 null。
     * 抛出：
     * IllegalClassFormatException - 如果输入不表示一个格式良好的类文件
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
       //需要判定是不是目标类
        if (!className.equals("org/line/core/service/FooService")) {
            return null; // 如果返回null则字节码不会被修改
        }

//        if (!className.equals("com/hand/portal/common/controller/PortalAnnounceController")) {
//            return null; // 如果返回null则字节码不会被修改
//        }

        try {
            //借助JavaAssist工具，进行字节码插桩
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get("org.line.core.service.FooService");
            if(cc.isFrozen()){
                cc.defrost();
            }
            CtMethod personFly = cc.getDeclaredMethod("dodo");

//            CtClass cc = pool.get("com.hand.portal.common.controller.PortalAnnounceController");
//            CtMethod personFly = cc.getDeclaredMethod("list");


            //在目标方法前后，插入代码
            personFly.insertBefore("System.out.println(\"---MyTransformer- sql开始执行----------- ---\");");
            personFly.insertAfter("System.out.println(\"---MyTransformer- sql结束执行------------- ---\");");

            return cc.toBytecode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        String classStr = "public class Demo{}";
//        ClassPool classPool = ClassPool.getDefault();
//        CtClass ctClass = classPool.makeClass(classStr);
//        Class<?> aClass = ctClass.toClass();
//        System.out.println(aClass.newInstance());

        return null;
    }
}