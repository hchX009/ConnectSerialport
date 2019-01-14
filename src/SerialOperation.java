import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class SerialOperation implements SerialPortEventListener {
    //使用了RXTX，RXTX是一个提供串口和并口通信的开源java类库
    //定义通讯端口管理类postId
    private CommPortIdentifier portId;    //定义通讯端口管理类列表postList
    //Enumeration接口中有一些方法可以枚举对象元素里的元素
    private Enumeration<CommPortIdentifier> portList;
    //RS232串口
    private SerialPort[] serialPort;
    //输入输出流
    private InputStream inputStream;
    private OutputStream outputStream;
    //定义串口数量
    private int serialNum = 0;

    //初始化串口函数
    public void seralportInit() {
        //获取系统所有通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        Enumeration<CommPortIdentifier> portListcount = CommPortIdentifier.getPortIdentifiers();
        while (portListcount.hasMoreElements()) {
            serialNum++;
            portListcount.nextElement();
        }
        //定义串口数组
        serialPort = new SerialPort[serialNum];
        //System.out.println(serialNum);
        for (int i = 0; i < serialNum; i++)
        {
            portId = portList.nextElement();
            //判断是否为端口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                System.out.println("找到串口"+portId.getName());
                //打开串口
                try
                {
                    //打开串口
                    serialPort[i] = (SerialPort)portId.open(Object.class.getSimpleName(), 1000);
                    //设置串口可监听
                    serialPort[i].addEventListener(this);
                    //设置串口通讯参数：波特率，数据位，停止位，校验方式
                    serialPort[i].setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    //获取输出流
                    outputStream = serialPort[i].getOutputStream();

                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:	//通讯中断
            case SerialPortEvent.OE:	//溢位错误
            case SerialPortEvent.FE:	//帧错误
            case SerialPortEvent.PE:	//奇偶校验错误
            case SerialPortEvent.CD:	//载波检测
            case SerialPortEvent.CTS:	//清除发送
            case SerialPortEvent.DSR:	//数据设备准备好
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:  //输出缓存区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE:    //有数据到达
                readSerialPort();
                break;
            default:
                break;
        }
    }

    //读取串口返回信息，判断是否为所需的串口
    private void readSerialPort() {
        byte[] readBuffer = new byte[1024];
    }

    //关闭串口
    private void closeSerialPort() {

    }
}
