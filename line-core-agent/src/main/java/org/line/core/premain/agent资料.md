- 首先需要引入 jabassist


- META-INFO/MANIFEST.MF  pom.xml中配置后，貌似不需要这个
>
Premain-Class: 包含 premain 方法的类（类的全路径名）
Agent-Class: 包含 agentmain 方法的类（类的全路径名）
Boot-Class-Path: 设置引导类加载器搜索的路径列表。查找类的特定于平台的机制失败后，引导类加载器会搜索这些路径。按列出的顺序搜索路径。列表中的路径由一个或多个空格分开。路径使用分层 URI 的路径组件语法。如果该路径以斜杠字符（“/”）开头，则为绝对路径，否则为相对路径。相对路径根据代理 JAR 文件的绝对路径解析。忽略格式不正确的路径和不存在的路径。如果代理是在 VM 启动之后某一时刻启动的，则忽略不表示 JAR 文件的路径。（可选）
Can-Redefine-Classes: true表示能重定义此代理所需的类，默认值为 false（可选）
Can-Retransform-Classes: true 表示能重转换此代理所需的类，默认值为 false （可选）
Can-Set-Native-Method-Prefix: true表示能设置此代理所需的本机方法前缀，默认值为 false（可选）
>
 

pom.xml/plugin

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <archive>
            <!--自动添加META-INF/MANIFEST.MF -->
            <manifest>
                <addClasspath>true</addClasspath>
            </manifest>
            <manifestEntries>
                <Premain-Class>clf.winner.java.agent.PreMainAgent</Premain-Class>
                <Agent-Class>clf.winner.java.agent.PreMainAgent</Agent-Class>
                <Can-Redefine-Classes>true</Can-Redefine-Classes>
                <Can-Retransform-Classes>true</Can-Retransform-Classes>
            </manifestEntries>
        </archive>
    </configuration>
</plugin>

- vm-options

-javaagent:D:\dev\workspace\org.line.core\parent-pom\line-core-agent\target\line-core-agent-1.2.0.SNAPSHOT.jar


JVMTI（Java Virtual Machine Tool Interface）是一套由 Java 虚拟机提供的了一套代理程序机制，可以支持第三方工具程序以代理的方式连接和访问 JVM。JVMTI 的功能非常丰富，包括虚拟机中线程、内存/堆/栈，类/方法/变量，事件/定时器处理等等。使用 JVMTI 一个基本的方式就是设置回调函数，在某些事件发生的时候触发并作出相应的动作，这些事件包括虚拟机初始化、开始运行、结束，类的加载，方法出入，线程始末等等。
 
 
 
 
 -----------------------------------------------------------------------------
 premain                   main方法之前启动   需要配置启动参数   
 agentmain                 main方法之后启动   不需要配置启动参数   