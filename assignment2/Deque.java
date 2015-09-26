import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node preFirst;
    private Node postLast;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        preFirst = new Node();
        postLast = new Node();
        preFirst.next = postLast;
        postLast.prev = preFirst;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldFirst = preFirst.next;
        Node n = new Node();
        n.item = item;
        n.next = oldFirst;
        n.prev = preFirst;
        oldFirst.prev = n;
        preFirst.next = n;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node last = postLast.prev;
        Node n = new Node();
        n.item = item;
        n.prev = last;
        n.next = postLast;
        last.next = n;
        postLast.prev = n;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node oldFirst = preFirst.next;

        preFirst.next = oldFirst.next;
        oldFirst.next.prev = oldFirst.prev;
        size--;

        return oldFirst.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node oldLast = postLast.prev;

        postLast.prev = oldLast.prev;
        oldLast.prev.next = oldLast.next;
        size--;

        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = preFirst.next;

        public boolean hasNext() {
            return current != postLast;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        d.addFirst("2");
        d.addLast("3");
        d.addFirst("1");
        print(d);

        System.out.print("removeFirst: " + d.removeFirst() + "\n");
        print(d);

        System.out.print("removeFirst: " + d.removeFirst() + "\n");
        print(d);

        System.out.print("removeFirst: " + d.removeFirst() + "\n");
        print(d);

        d.addFirst("3");
        d.addFirst("2");
        d.addFirst("1");
        print(d);

        System.out.print("removeLast: " + d.removeLast() + "\n");
        print(d);

        System.out.print("removeLast: " + d.removeLast() + "\n");
        print(d);

        System.out.print("removeLast: " + d.removeLast() + "\n");
        print(d);
    }

    private static void print(Deque<String> d) {
        for (String s : d) {
            System.out.print(s + " ");
        }
        System.out.println("");
    }
}
