package org.line.core.premain;

public class DemoMain {

    public static void main(String[] args) {
        System.out.println("DemoMain.main开始");
        HelloService helloService = new HelloService();
        helloService.sayHello("一拳超人");
        System.out.println("DemoMain.main结束");
    }

}