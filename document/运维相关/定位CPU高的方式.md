top : 先查看进程情况

![image-20221007150913126](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20221007150913126.png)


top -Hp 22589（进程号）
 ps -mp 22589（进程号） -o THREAD,tid,time   查看线程占用率

![image-20221007151840124](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20221007151840124.png)

找到cpu 占用率高的线程，转换为16进制

printf "%x\n" 15859（线程号）

![image-20221007151819109](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20221007151819109.png)



jstack  22589（进程号）|grep 3df3 （16位线程号）>> problem.txt

查看相关信息 标注了哪些代码， 类，行数 啊什么的