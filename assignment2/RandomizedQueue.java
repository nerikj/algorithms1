import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] q;
    private int size;
    private int tail;

    public RandomizedQueue() {
        q = new Object[2];
        size = 0;
        tail = -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        q[++tail] = item;
        size++;
        
        if (tail >= q.length - 1) {
            Object[] newQ = new Object[q.length * 2];
            for (int i = 0; i < q.length; i++) {
                newQ[i] = q[i];
            }
            q = newQ;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int indexToRemove;
        do {
            indexToRemove = StdRandom.uniform(0, tail + 1);
        } while (q[indexToRemove] == null);

        Item t = (Item) q[indexToRemove];
        q[indexToRemove] = null;

        // System.out.println("dequeue() size " + size + " tail " + tail + "
        // indexToRemove " + indexToRemove);

        size--;
        if (indexToRemove == tail) {
            tail--;
        }
        // System.out.println("dequeue() size " + size + " tail " + tail);

        return t;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int indexToRemove;
        do {
            indexToRemove = StdRandom.uniform(0, tail + 1);
        } while (q[indexToRemove] == null);
        return (Item) q[indexToRemove];
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = 0;

        public boolean hasNext() {
            return current <= tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (q[current] == null) {
                current++;
                return next();
            } else {
                return (Item) q[current++];
            }
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> d = new RandomizedQueue<String>();
        print(d);

        d.enqueue("1");
        print(d);

        d.enqueue("2");
        print(d);

        d.enqueue("3");
        print(d);

        d.dequeue();
        print(d);

        d.enqueue("4");
        print(d);

        for (String s1 : d) {
            for (String s2 : d) {
                System.out.println("s1: " + s1);
                System.out.println("s2: " + s2);
            }
        }
    }

    private static void print(RandomizedQueue<String> d) {
        for (String s : d) {
            System.out.print(s + " ");
        }
        System.out.println("\n---");
    }
}
