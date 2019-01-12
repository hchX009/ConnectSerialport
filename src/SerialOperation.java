import gnu.io.*;

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

    //初始化串口函数
    public void seralportInit()
    {
        //获取系统所有通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements())
        {
            portId = portList.nextElement();
            //判断是否为端口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                System.out.println("找到串口"+portId.getName());
                //打开串口
                try
                {
                    serialPort = (SerialPort)portId.open(Object.class.getSimpleName(), 1000);

                    serialPort.addEventListener(this);

                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);


                } catch (PortInUseException e) {
                    e.printStackTrace();
                } catch (TooManyListenersException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }
}
