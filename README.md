# Do-Java-Online
Java Online Executor
### 背景
最近一直在做LeetCode上的算法题，像这种在线编程网站都会提供一个在线编写，运行代码的窗口，如下图。
![img/1.jpg](img/1.jpg)
正好最近在看周志明老师的《深入理解Java虚拟机》这本书，在书中的第九章《类加载及执行子系统的案例与实战》中详细讲解了“如何实现远程执行功能”，与上述在线执
行器的思路一致。
结合TangBean同学的[OnlineExecutor](https://github.com/TangBean/OnlineExecutor)项目，后续会完善多个相关功能：
- [ ] 用户注册登录
- [ ] 上传文件，显示
- [ ] 美化页面
### 展示
![img/2.jpg](img/2.jpg)
### 原理
在程序实现的过程中，我们需要解决三个问题：
- [如何编译页面编写的Java代码]
- [如何执行编译之后的Java代码]
- [如何收集Java代码的执行结果]
### 祝进步 
