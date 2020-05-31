# connect_serialport

这是一个使用Java编写的串口通讯读写程序，为了实现单片机硬件和上位机之间的串口通讯功能。 

使用了RXTX串口通讯类库，JDK最好使用Java SE 8(我也不知道为啥用11会有玄学bug) 
RXTX是一个提供串口和并口通信的开源java类库，由该项目发布的文件均遵循LGPL协议。 
RXTX下载地址：http://rxtx.qbang.org/wiki/index.php/Download  

/lib文件夹中包含了RXTXcomm.jar类库(那我上面放下载地址干啥……)  
/src文件夹中包含了该程序源码保存在  
+ Main.java为程序主函数  
+ SerialOperation.java中定义了串口初始化方法，串口读写方法和串口关闭方法  
+ DealData.java中定义了处理日期的方法  
/out文件夹中是代码编译后的文件
