//package com.trevorbernard.disruptor.examples;
package disruptor.examples;

import java.util.concurrent.ExecutorService;
import java.util.UUID;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class Simple {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {


        int cnt = 4000 * 10;
        long start = System.currentTimeMillis();

        long sum = 0;
        for (int i = 0; i < cnt; i++) {
//            System.out.println("i = " + i);
            sum += i + 2;
//            System.currentTimeMillis();
        }
        long end = System.currentTimeMillis();
        System.out.println( sum + "     () = " + ( end - start ));

        System.out.println("end = " + (10000000 / 25));

        test();
    }

    private static void test() {
        ExecutorService exec = Executors.newCachedThreadPool();
        // Preallocate RingBuffer with 1024 ValueEvents
        Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, 1024, exec);
        final EventHandler<ValueEvent> handler = new EventHandler<ValueEvent>() {
            // event will eventually be recycled by the Disruptor after it wraps
            public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                System.out.println("Sequence: " + sequence);
                System.out.println("ValueEvent: " + event.getValue());
            }
        };
        // Build dependency graph
        disruptor.handleEventsWith(handler);
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        for (long i = 10; i < 2000000; i++) {
            String uuid = UUID.randomUUID().toString();
            // Two phase commit. Grab one of the 1024 slots
            long seq = ringBuffer.next();
            ValueEvent valueEvent = ringBuffer.get(seq);
            valueEvent.setValue(uuid);
            ringBuffer.publish(seq);
        }
        disruptor.shutdown();
        exec.shutdown();
    }
}
