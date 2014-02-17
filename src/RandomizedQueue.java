import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] items;
	private int N = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public RandomizedQueue() {
		items = (Item[]) new Object[1];	
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return items.length;				
	}
	
	public void enqueue(Item item) {
		if (N == items.length - 1) {
			resizeArray(items.length * 2);
		}
		
		items[N++] = item;
	}
	
	public Item dequeue() {
		// TODO: delete and return a random item		
	
		int randomIndex = StdRandom.uniform(0, N);
		
		Item item = items[randomIndex];
		
		N--;
		
		if (N > 0 && N == items.length / 4) {
			resizeArray(items.length / 2);
		}
		
		return null;
	}
	
	/**
	 * Returns (but does not delete) a random item
	 */
	public Item sample() {
		
		int randomIndex = StdRandom.uniform(0, N);
		
		return items[randomIndex];
	}
	
	public Iterator<Item> iterator() {
	   return new RandomIterator();
	}
	
	private void resizeArray(int size) {
		Item[] newArray = (Item[]) new Object[size];
		
		for (int i = 0; i < N; i++) {
			newArray[i] = items[i];
		}
		
		items = newArray;
	}
	   
	private class RandomIterator implements Iterator<Item> {
		public boolean hasNext() {
			return false;
	    }
	   
	    public void remove() {
	    	throw new UnsupportedOperationException();
	    }
	   
	    public Item next() {
	    	return null;
	    }
   }
}
