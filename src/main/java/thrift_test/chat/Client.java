import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

    public static void main(String[] args) throws Exception {
        nio();

    }

    private static void nio() throws org.apache.thrift.TException {
        //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
        TTransport transport = new TFramedTransport(new TSocket("localhost", 7911));
        transport.open();

        //使用高密度二进制协议
        TProtocol protocol = new TCompactProtocol(transport);

        //创建Client
        Hello.Client client = new Hello.Client(protocol);

        long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
            client.helloBoolean(false);
            client.helloInt(111);
//            client.helloNull();
            client.helloString("360buy");
            client.helloVoid();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));

        //关闭资源
        transport.close();
    }
    private static void bio() throws org.apache.thrift.TException {
        // 设置传输通道 - 普通IO流通道
        TTransport transport = new TSocket("localhost", 7911);
        transport.open();

        //使用高密度二进制协议
        TProtocol protocol = new TCompactProtocol(transport);

        //创建Client
        Hello.Client client = new Hello.Client(protocol);

        long start = System.currentTimeMillis();
        for(int i=0; i<10000; i++){
            client.helloBoolean(false);
            client.helloInt(111);
            client.helloNull();
            client.helloString("dongjian");
            client.helloVoid();
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));

        //关闭资源
        transport.close();
    }

}
