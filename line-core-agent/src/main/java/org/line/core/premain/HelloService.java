package org.line.core.premain;

public class HelloService {

    public void sayHello(String name) {
        System.out.println("      HelloService.sayHello开始");
        System.out.println("         Hello " + name + "!");
        System.out.println("      HelloService.sayHello结束");
    }

}