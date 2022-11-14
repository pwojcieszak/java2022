package pl.edu.uj.queue;

import org.junit.Test;

public class PriorityQueueTest {
    @Test(expected = EmptyQueueException.class)
    public void testEmptyIntQueue() throws EmptyQueueException {
        PriorityQueue<Integer> intQueue = new PriorityQueue<>();
        intQueue.get();
    }

    @Test(expected = EmptyQueueException.class)
    public void testEmptyStringQueue() throws EmptyQueueException {
        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.get();
    }

    @Test
    public void testQueueSuccessfully() throws EmptyQueueException{
        PriorityQueue<Integer> intQueue = new PriorityQueue<>();
        intQueue.add(12, 1);
        intQueue.add(11, 5);
        intQueue.add(15, 3);
        System.out.println(intQueue.get());

        PriorityQueue<String> stringQueue = new PriorityQueue<>();
        stringQueue.add("Jeden", 1);
        stringQueue.add("Dwa", 5);
        stringQueue.add("Trzy", 3);
        System.out.println(stringQueue.get());
    }
}
