import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> q;
        String input;

        q = new RandomizedQueue<String>();
        try {
            do {
                input = StdIn.readString();
                if (input != null) {
                    q.enqueue(input);
                }
            } while (input != null);
        } catch (NoSuchElementException e) {
        }

        int count = Integer.parseInt(args[0]);
        for (int i = 0; i < count; i++) {
            System.out.println(q.dequeue());
        }
    }
}
