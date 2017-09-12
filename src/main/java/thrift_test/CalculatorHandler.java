package thrift_test;

import thrift_test.shared.SharedStruct;
import thrift_test.tutorial.Calculator;
import thrift_test.tutorial.InvalidOperation;
import thrift_test.tutorial.Work;
import org.apache.thrift.TException;

public class CalculatorHandler implements  Calculator.Iface {

    @Override
    public SharedStruct getStruct(int key) throws TException {
        System.out.println("CalculatorHandler.getStruct");
        return null;
    }

    @Override
    public void ping() throws TException {
        System.out.println("CalculatorHandler.ping");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("CalculatorHandler.add");
        return 0;
    }

    @Override
    public int calculate(int logid, Work w) throws InvalidOperation, TException {
        System.out.println("CalculatorHandler.calculate");

        return 0;
    }

    @Override
    public void zip() throws TException {

        System.out.println("CalculatorHandler.zip");
    }
}
