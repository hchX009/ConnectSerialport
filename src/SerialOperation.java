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
    private SerialPort serialPort;
    //输入输出流
    private InputStream inputStream;
    private OutputStream outputStream;
    //接收到的文本
    private String text;

    //初始化串口函数
    public void seralportInit() {
        //获取系统所有通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = portList.nextElement();
            //判断是否为端口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("找到串口" + portId.getName());
                //打开串口
                try {
                    //打开串口
                    serialPort = (SerialPort)portId.open(Object.class.getSimpleName(), 2000);
                    //设置串口数据时间有效
                    serialPort.notifyOnDataAvailable(true);
                    //设置串口通讯参数：波特率，数据位，停止位，校验方式
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    //发送协议，判断是否为所需串口，
                    writeSerialPort("test");
                    if(!readSerialPort().equals("test")){
                        System.out.println("串口" + portId.getName() + "不是要找的串口");
                        closeSerialPort(false);
                        continue;
                    } else {
                        System.out.println("串口" + portId.getName() + "连接成功！");
                        //设置串口可监听
                        serialPort.addEventListener(this);
                        break;
                    }
                } catch (PortInUseException e) {
                    //e.printStackTrace();
                    System.out.println("串口" + portId.getName() + "被占用");
                    closeSerialPort(false);
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                } catch (UnsupportedCommOperationException e) {
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

    //向串口输出信息
    public boolean writeSerialPort(String out){
        byte[] wirtebuffer = out.getBytes();
        try {
            //获取输出流
            outputStream = serialPort.getOutputStream();
            outputStream.write(wirtebuffer);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("向串口" + portId.getName() + "发送信息:" + out + " 完毕");
        return true;
    }

    //读取串口返回信息
    public String readSerialPort(){
        String in = "";
        byte[] readBuffer = new byte[1024];
        try {
            //获取输入流
            inputStream = serialPort.getInputStream();
            int len = inputStream.read(readBuffer);
            in = new String(readBuffer, 0, len).trim();
            System.out.println("串口" + portId.getName() + "读取到:" + in + " 长度为:" + len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    //关闭串口
    public void closeSerialPort(boolean flag) {
        if(serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            if (flag) serialPort.removeEventListener();
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serialPort.close();
            serialPort = null;
        }
        System.out.println("串口" + portId.getName() + "已关闭");
    }
}
