import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{

	private Node first = null;
	private Node last = null;
	
	private class Node {
		public Item item;
		public Node next;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Deque<Integer> deck = new Deque<Integer>();

		int N = 10;
		
		for (int i = 0; i < N; i++) {
			deck.addFirst(i);
		}
		
		for (int i = 0; i < N; i++) {
			System.out.println(deck.removeFirst());
		}
	}
	
	public Deque() {
		
	}
	
   public boolean isEmpty() {
	   return first == null;
   }
   
   public int size() {
	   
	   if (isEmpty()) {
		   return 0;
	   }
	   
	   Node current = first;
	   
	   int count = 1;
	   while (current.next != null) {
		   count++;
		   current = current.next;
	   }
	   
	   return count;
   }
   
   public void addFirst(Item item) {
	   
	   if (item == null) {
		   throw new NullPointerException();
	   }
	   
	   Node oldFirst = first;
	   
	   first = new Node();
	   first.item = item;
	   first.next = oldFirst;
	   
	   if (oldFirst == null) {
		   last = first;
	   }
   }
   
   public void addLast(Item item) {
	   if (item == null) {
		   throw new NullPointerException();
	   }
	   
	   Node newLast = new Node();
	   newLast.item = item;
	   newLast.next = null;
	   
	   Node oldLast = last;	   
	   oldLast.next = last;	   
   }
   
   public Item removeFirst() {
	   Item retVal = first.item;
	   first = first.next;	   
	   
	   if (isEmpty()) {
		   last = null;
	   }
	   
	   return retVal;
   }
   
   public Item removeLast() {
	   if (isEmpty()) {
		   throw new NoSuchElementException();
	   }	   
	   
	   Item item = null;
	   
	   if (size() == 1) {
		   item = first.item;
		   first = null;
		   
		   return item;
	   }
	   
	   item = last.item;
	   
	   Node current = first;
	   
	   while (current.next != null) {
		   if (current.next.equals(last)) {
			   current.next = null;
			   
			   if (size() == 1) {
				   first = current;
				   last = null;				   
			   } else {
				   last = current;				   
			   }			   			   
		   } else {
			   current = current.next;
		   }
		   
	   }
	   
	   return item;
   }
   
   public Iterator<Item> iterator() {
	   return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item> {
	
	   private Node current = first;
	   
	   public boolean hasNext() {
		   return current != null;
	   }
	   
	   public void remove() {
		   throw new UnsupportedOperationException();
	   }
	   
	   public Item next() {
		   
		   if (current == null) {
			   throw new NoSuchElementException();
		   }
		   
		   Item item = current.item;
		   current = current.next;
		   
		   return item;
	   }
   }
}
