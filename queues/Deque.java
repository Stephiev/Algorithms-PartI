import java.util.Iterator;
import java.util.NoSuchElementException;


// NEED TO KEEP TRACK OF LAST

// will be using a linked list
public class Deque<Item> implements Iterable<Item> {
//    From Queue.java in book, a linked list implementation of a queue
//    http://introcs.cs.princeton.edu/java/43stack/Queue.java.html
    private int n;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue
    
    public Deque() {
        first = null;
        last  = null;
        n = 0;
    
    }                          // construct an empty deque
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        
        
        Node (Item item) {
            this.item = item;
            this.previous = null; // the first item will have previous as null
            this.next = null;  // the last item will have next as null
        }
    }
    
    public boolean isEmpty() { return first == null;}               // is the deque empty?
    
    public int size() {return n;}                        // return the number of items on the deque
    
    public void addFirst(Item item) {  // add the item to the front
        // same as a stack push
        System.out.println("Calling addFirst (same as push in a stack). Adding " + item.toString() + " to the FRONT of the deque");
        if (first != null) {
            System.out.println("'first' went from " + first.item.toString());
        } else {
            System.out.println("'first' went from null");
        }
        
        if (last != null) {
            System.out.println("'last' went from " + last.item.toString());
        } else {
            System.out.println("'last' went from null");
        }
        
         // If empty last and first are the same
         if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
        
            Node oldfirst = first; // grabbing the oldfirst
            first = new Node(item);   // creating new first
            first.next = oldfirst;  // setting the next of the new first to the oldfirst
            oldfirst.previous = first; // setting previous of the oldfirst to the new first
        }
        n++;

       
        System.out.println(" to FIRST " + first.item.toString());
        System.out.println(" to LAST " + last.item.toString());
    }        
    public void addLast(Item item) {
//   same as add(e) in a queue
        System.out.println("Calling addLast (same as add(e) in a queue). Adding " + item.toString() + " to the END of the deque");
          if (last != null) {
            System.out.print("'last' went from " + first.item.toString());
        } else {
            System.out.print("'last' went from null");
        }
        Node oldlast = last;
        last = new Node(item);
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
          oldlast.next = last;
        }

        n++;
        System.out.println(" to " + last.item.toString());
    
    }           // add the item to the end
    public Item removeFirst() { // remove and return the item from the front
        // same as pop in a stack
        System.out.print("Calling removeFirst (same as pop in a stack).");
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        System.out.println(" Removed " + item.toString() + " from the FRONT of the deque");
        System.out.println("'first' is now " + first.item.toString());
        return item; 
      
        
    }                
    public Item removeLast() {
        // same as remove in a queue
        Item item = first.item;
        first = first.next;
        if (isEmpty())  last = null;

        return item;
//      Item item = first.item;
//      return item;
    }                 // remove and return the item from the end
    
    // Iteratables have a method that returns an iterator 
    public Iterator<Item> iterator() {
         
        return new DequeIterator();
    }         // return an iterator over items in order from front to end
    
    
    // Iterators have methods, hasNext() and next(), some have remove() but causes bugs so will not implement
     private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {  // !this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> testDeque = new Deque<Integer>();
        testDeque.addFirst(10);
        testDeque.addFirst(15);
        testDeque.addFirst(5);
//        testDeque.removeFirst();
//        System.out.println(first);
//        testDeque.addLast(200);
                               
        Iterator<Integer> itr = testDeque.iterator();
//        System.out.println(itr.next());


    
    }   // unit testing
}



// keeping a pointer to the first and last node makes enqueue and dequeue a constant time operation, otherwise enqueue(added to the end) is linear 

