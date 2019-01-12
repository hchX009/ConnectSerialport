import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.Enumeration;

public class SerialOperation
{
    //使用了RXTX，RXTX是一个提供串口和并口通信的开源java类库
    //定义通讯端口管理类postId
    private CommPortIdentifier portId;
    //定义通讯端口管理类列表postList
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
                    serialPort = (SerialPort)portId.open(Object.class.getSimpleName(),1000);
                } catch (PortInUseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
