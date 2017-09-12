package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.ACL;
import sun.security.acl.AclImpl;

import java.io.IOException;
import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luokai on 2017/7/22 0022.
 */
public class ZooClientNative {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException
    {
        // 创建一个与服务器的连接 需要(服务端的 ip+端口号)(session过期时间)(Watcher监听注册)
        ZooKeeper zk = new ZooKeeper("10.9.61.84:2181", 3000, new Watcher()
        {
            // 监控所有被触发的事件
            public void process(WatchedEvent event)
            {
                System.out.println(" 发现:     " +  event.toString());
            }
        });
        System.out.println("OK!");
        // 创建一个目录节点
        /**
         * CreateMode:
         *       PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
         *       PERSISTENT_SEQUENTIAL（持久的且带顺序的）
         *       EPHEMERAL (短暂的，生命周期依赖于client session)
         *       EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)
         */
        List<ACL> acc = new ArrayList<ACL>();
//        Acl acl = new AclImpl();
        zk.create("/path01", "data01".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/path01/path01", "data01/data01".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/path01", false, null)));



        Watcher watcher = new Watcher()
        {
            // 监控所有被触发的事件
            public void process(WatchedEvent event)
            {
                System.out.println(" Disca:     " +  event.toString());
            }
        };

        // 取出子目录节点列表
        System.out.println(zk.getChildren("/path01", true));
        // 创建另外一个子目录节点
        zk.create("/path01/path02", "data01/data02".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zk.getChildren("/path01", true));
        // 修改子目录节点数据
        zk.setData("/path01/path01", "data01/data01-01".getBytes(), -1);
        zk.setData("/path01/path01", "data01/data01-02".getBytes(), -1);
        zk.setData("/path01/path01", "data01/data01-03".getBytes(), -1);
        byte[] datas = zk.getData("/path01/path01", true, null);
        String str = new String(datas, "utf-8");
        System.out.println(str);
        Thread.sleep(1000);
        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
        zk.delete("/path01/path01", -1);
        zk.delete("/path01/path02", -1);
        zk.delete("/path01", -1);
        System.out.println(str);
        zk.close();
        System.out.println("OK");
    }

}
