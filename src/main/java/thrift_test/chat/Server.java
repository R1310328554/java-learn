import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;

public class Server {
    /**
     * 阻塞式、多线程处理
     *
     * @param args
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        nio();
    }

    private static void nio() {
        try {
            //传输通道 - 非阻塞方式
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(7911);

            //异步IO，需要使用TFramedTransport，它将分块缓存读取。
            TTransportFactory transportFactory = new TFramedTransport.Factory();

            //使用高密度二进制协议
            TProtocolFactory proFactory = new TCompactProtocol.Factory();

            //设置处理器 HelloImpl
            TProcessor processor = new Hello.Processor(new HelloImpl());

            //创建服务器
            TServer server = new TThreadedSelectorServer(
                    new TThreadedSelectorServer.Args(serverTransport)
                            .protocolFactory(proFactory)
                            .transportFactory(transportFactory)
                            .processor(processor)
            );

            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bio() {
        try {
            //设置传输通道，普通通道
            TServerTransport serverTransport = new TServerSocket(7911);

            //使用高密度二进制协议
            TProtocolFactory proFactory = new TCompactProtocol.Factory();

            //设置处理器HelloImpl
            TProcessor processor = new Hello.Processor(new HelloImpl());

            //创建服务器
            TServer server = new TThreadPoolServer(
                    new TThreadPoolServer.Args(serverTransport)
                            .protocolFactory(proFactory)
                            .processor(processor)
            );

            System.out.println("Start server on port 7911...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
