import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
//    private static final int START_MAX = 10;
    private Item[] rq;
    private int size;
    
    
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        size = 0;
        rq = (Item[]) new Object[10];
    }
   
    public boolean isEmpty()                 // is the randomized queue empty?
    {
        return size == 0;
    }
    
    public int size()                        // return the number of items on the randomized queue
    {
        return size;
    }
    
    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException();
        if (size >= rq.length) resize(rq.length * 2);
        rq[size] = item;
        size++;
    }
    
    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException(); 
        
        int r = StdRandom.uniform(size);
        Item result = rq[r];
        rq[r] = rq[size-1];
        rq[--size] = null;
        
        if (size > 0 && rq.length >= 4*size) resize(rq.length / 2);
        
        return result;    
    }
    
    public Item sample()                     // return a random item (but do not remove it)
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(size);
        return rq[index];
    }
    
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator();
    }
    
    private void resize(int n)
    {
        Item[] copy = (Item[]) new Object[n];
        for (int i = 0; i < size; i++) {
               copy[i] = rq[i];
        }
        rq = copy;
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> q;
        int i;

        q = new RandomizedQueue<Integer>();
        for (i = 0; i < 50; i++) {
            q.enqueue(i);
            assert q.size() == i + 1;
        }

        i = q.dequeue();
        StdOut.println("deque: " + i);
        assert q.size() == 49;

        for (i = 0; i < 5; i++) {
            StdOut.println("sample: " + q.sample());
        }

        for (i = 0; i < 40; i++) {
            q.dequeue();
        }

        q = new RandomizedQueue<Integer>();
        for (i = 0; i < 20; i++) {
            q.enqueue(i);
        }

        for (int j : q) {
            StdOut.println(j);
        }

    }
    
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int n = size;
        private int[] order;
        
        public RandomizedQueueIterator()
        {
            order = new int[n];
            
            for (int j=0; j < n; j++) {
                order[j] = j;
            }
            
            StdRandom.shuffle(order);
            
        }
            
            
        public boolean hasNext() { return n != 0; }
        public void remove() {   throw new UnsupportedOperationException();  }

        public Item next()
        {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return rq[order[--n]];
        }
    }
}