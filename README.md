# connect_serialport
这是一个使用Java编写得串口通讯读写程序
使用了RXTX串口通讯类库，JDK最好使用Java SE 8(我也不知道为啥用11会有玄学bug)
RXTX下载地址：
http://rxtx.qbang.org/wiki/index.php/Download
RXTX是一个提供串口和并口通信的开源java类库，由该项目发布的文件均遵循LGPL协议。
该程序源码保存在/src文件夹中
Main.class为程序主函数
SerialOperation.class中定义了串口初始化方法，串口读写方法和串口关闭方法
具体的方法实现改天再说吧
/lib文件夹中包含了RXTXcomm.jar类库(那我上面放下载地址干啥……)
