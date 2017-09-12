import org.apache.thrift.TException;

public class HelloImpl implements Hello.Iface {
    @Override
    public String helloString(String para) throws TException {

        return "Hi : " + para;
    }

    @Override
    public int helloInt(int para) throws TException {
        System.out.println("para = " + para);
        return para;
    }

    @Override
    public boolean helloBoolean(boolean para) throws TException {
        System.out.println("HelloImpl.helloBoolean");
        return false;
    }

    @Override
    public void helloVoid() throws TException {
        System.out.println("HelloImpl.helloVoid");
    }

    @Override
    public String helloNull() throws TException {

        System.out.println("HelloImpl.helloNull");
        return null;
    }
}
