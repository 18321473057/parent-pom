package org.line.core;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor; //导入方法依赖的package包/类
import org.line.core.service.FooService;

import java.util.List;

/**
 * Hello world!
 */


public class App {

    //premain
//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        new FooService().dodo();
//    }


    //agentmain
    public static void main(String[] args) throws Exception {
        //获取当前系统中所有 运行中的 虚拟机
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("org.line.core.App")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("D:/dev/workspace/org.line.core/parent-pom/line-core-agent/target/line-core-agent-1.2.0.SNAPSHOT.jar");
                Thread.sleep(10000L);
                virtualMachine.detach();
                new FooService().dodo();
            }
        }


    }

}

