import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] items;
	private int N = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		
		for (int i = 0; i < 5; i++) {
			queue.enqueue(Integer.valueOf(i));
		}
		
		for (int i = 0; i < 5; i++) {
			Integer result = queue.dequeue();
			
			if (result != null) {
				System.out.println(String.format("Dequeued value: %s", result));
			} else {
				System.out.println("Dequeue() returned null value.");
			}
		}		
	}
	
	public RandomizedQueue() {
		items = (Item[]) new Object[1];	
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;				
	}
	
	public void enqueue(Item item) {
		if (N == items.length - 1) {
			resizeArray(items.length * 2);
		}
		
		items[N++] = item;		
	}
	
	public Item dequeue() {
		
		if (N == 0) {
			throw new NoSuchElementException();
		}
		
		if (N == 1) {
			Item lastItem = items[0];
			items[0] = null;
			N--;
			
			return lastItem;
		}
	
		int randomIndex = StdRandom.uniform(0, N - 1);		
		
		Item item = items[randomIndex];		
		items[randomIndex] = null;
		
		N--;		
		
		if (randomIndex < N) {
			compressArray(randomIndex + 1);
		}
		
		if (N > 0 && N == items.length / 4) {
			resizeArray(items.length / 2);
		}
		
		return item;
	}
	
	/**
	 * Returns (but does not delete) a random item
	 */
	public Item sample() {
		
		if (N == 0) {
			throw new NoSuchElementException();
		}
		
		int randomIndex = StdRandom.uniform(0, N);
		
		return items[randomIndex];
	}
	
	public Iterator<Item> iterator() {
	   return new RandomIterator();
	}
	
	private void resizeArray(int size) {		
		
		@SuppressWarnings("unchecked")
		Item[] newArray = (Item[]) new Object[size];
		
		for (int i = 0; i < N; i++) {
			newArray[i] = items[i];
		}
		
		items = newArray;
	}
	
	private void compressArray(int startingPosition) {
	
		for (int i = startingPosition; i < items.length; i++) {
			Item currentItem = items[i];
			
			if (currentItem != null) {					
				items[i - 1] = currentItem;
				items[i] = null;
			}
		}
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
