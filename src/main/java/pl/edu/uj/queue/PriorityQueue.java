package pl.edu.uj.queue;


import java.util.ArrayList;

public class PriorityQueue<T> {
    private final ArrayList<CustomPair<T, Integer>> arr;
    PriorityQueue() {
        arr = new ArrayList<>();
    }

    public void add(T item, int priority)
    {
        System.out.println("Inserting data(" + item + ") of priority(" + priority + ")");
        arr.add(new CustomPair<>(item, priority));
    }

    public T get() throws EmptyQueueException
    {
        if (isEmpty()) {
            throw new EmptyQueueException("Underflow\nProgram Terminated");
        }
        int maxPriority = arr.get(0).getValue();
        int minIndex = 0;
        for(int i=1; i<arr.size(); i++){
            if(arr.get(i).getValue()>maxPriority) {
                maxPriority = arr.get(i).getValue();
                minIndex = i;
            }
        }
        T value = arr.get(minIndex).getKey();
        arr.remove(minIndex);
        return value;
    }

    public boolean isEmpty() {

        return (arr.size() == 0);
    }

    public int size(){

        return arr.size();
    }

    public static void main(String[] args) throws EmptyQueueException {
        PriorityQueue<Integer> intQueue = new PriorityQueue<>();
        intQueue.add(12, 1);
        intQueue.add(11, 6);
        intQueue.add(14, 1);
        intQueue.add(4, 5);
        intQueue.add(112, 6);
        System.out.println("Queue length before get(): " + intQueue.size() + "\nGot: " + intQueue.get() +
                "\nQueue length after get(): " + intQueue.size());
    }
}
