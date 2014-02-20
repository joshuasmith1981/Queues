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

	/**
	 * Is the queue empty?
	 * @return Flag indicating whether the queue is empty
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Gets the size of the queue
	 * @return The number of elements in the queue
	 */
	public int size() {
		return N;
	}

	/**
	 * Adds an item to the queue
	 * @param item The item to be added
	 */
	public void enqueue(Item item) {
		if (N == items.length - 1) {
			resizeArray(items.length * 2);
		}

		items[N++] = item;
	}

	/**
	 * Deletes and returns a random item
	 * @return A randomly-selected item
	 */
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

		int randomIndex = StdRandom.uniform(0, N);

		Item item = items[randomIndex];
		items[randomIndex] = null;

		N--;

		// If we dequeue an item from the middle of the array,
		// collapse items above the current index to fill the gap.
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
	 * @return A randomly-selected item
	 */
	public Item sample() {

		if (N == 0) {
			throw new NoSuchElementException();
		}

		int randomIndex = StdRandom.uniform(0, N);

		return items[randomIndex];
	}

	/**
	 * Creates an independent iterator over items in a random order 
	 */
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

		private int[] indices;
		private int currentIndex = 0;

		public RandomIterator() {
			indices = new int[N];

			/*
			 * Set up an array of random indices,
			 * ensuring that a given index is not
			 * repeated.  The iterator uses these
			 * indices to produce random values
			 * from the queue without hitting
			 * a given index twice.
			 */
			for (int i = 0; i < N; i++) {
				
				boolean foundNewValue = false; 
				
				while (!foundNewValue) {
					int value = StdRandom.uniform(0, N);
					
					if (!valueAlreadyGenerated(value, i)) {
						indices[i] = value;
						foundNewValue = true;
					}
				}
			}
		}

		public boolean hasNext() {
			return currentIndex < indices.length;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			int index = indices[currentIndex++];
			
			return items[index];
		}
		
		private boolean valueAlreadyGenerated(int value, int currentPosition) {
			for (int i = 0; i < currentPosition; i++) {
				if (indices[i] == value) { 
					return true;
				}
			}
			
			return false;
		}
	}
}
