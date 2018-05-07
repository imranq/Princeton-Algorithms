import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
// construct an empty deque
    private Node first, last;
    private int size;
           
    public Deque() {
        
    }
    
    private class Node {
        public Item item;
        public Node next;
        public Node previous;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }// is the deque empty?
    
    public int size() {
        return size;
    }// return the number of items on the deque
    
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        
        if (isEmpty()) { //previous and next are null
            last = first;
        } else { 
            oldFirst.previous = first;
            first.next = oldFirst;
        }

        size++;
    } // add the item to the front
    
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        
        if (isEmpty()) {
            first = last;
        } else {
            last.previous = oldLast;            
        }
           
        size++;
    }// add the item to the end
    
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item result = first.item;
        first = first.next;
        first.previous = null;
        size--;
        
        if (isEmpty()) last = first;
          
        return result;
    } // remove and return the item from the front
    
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();   
        
        Item result = last.item;
        last = last.previous;
        last.next = null;
        size--;
        
        
        if (isEmpty()) last = first;
        
        return result;
    }// remove and return the item from the end
    
    public Iterator<Item> iterator() 
    {
        return new DequeIterator();
    } // return an iterator over items in order from front to end
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addLast(1);
        deque.addLast(3);
        deque.addFirst(4);
        deque.removeLast();
        deque.addFirst(6);
        deque.addLast(5);
        deque.addFirst(29);
        deque.addFirst(2);
        deque.addFirst(9);
        deque.addFirst(24);
        deque.addFirst(243);
        
        deque.addFirst(24);
        
        for (int d : deque) {
            System.out.println(d);
        }
        
    }// unit testing (optional)
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() {   throw new UnsupportedOperationException();  }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}