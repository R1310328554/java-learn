package zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.List;

/**
 * Created by luokai on 2017/7/22 0022.
 */
public class Curator {
    public static void main(String[] args) throws Exception
    {
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.9.61.84:2181", new RetryNTimes(10, 5000));
        client.start();// 连接
        // 获取子节点，顺便监控子节点
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher()
        {
            public void process(WatchedEvent event) throws Exception
            {
                System.out.println("监控： " + event);
            }
        }).forPath("/");
        System.out.println(children);
        // 创建节点
        String result = client.create().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath("/test", "Data".getBytes());
        System.out.println(result);
        // 设置节点数据
        client.setData().forPath("/test", "111".getBytes());
        client.setData().forPath("/test", "222".getBytes());
        // 删除节点
        System.out.println(client.checkExists().forPath("/test"));
        client.delete().withVersion(-1).forPath("/test");
        System.out.println(client.checkExists().forPath("/test"));
        client.close();
        System.out.println("OK！");
    }


    private void saqwe() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("", new RetryNTimes(1, 22));
        curatorFramework.start();;

        try {
            String s = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("");
            System.out.println("s = " + s);

            curatorFramework.setData().forPath("");

            curatorFramework.delete().forPath("");


            CuratorWatcher www = new CuratorWatcher() {
                public void process(WatchedEvent watchedEvent) throws Exception {
                    System.out.println("watchedEvent = [" + watchedEvent + "]");
                }
            };
            curatorFramework.getChildren().usingWatcher(www);

            Watcher cw = new Watcher() {
                public void process(WatchedEvent watchedEvent) {

                }
            };
            curatorFramework.getData().usingWatcher(cw);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }

}
