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

		int count = 10000;
		
		for (int i = 0; i < count; i++) {
			queue.enqueue(Integer.valueOf(i));
		}
		
		for (int i = 0; i < count; i++) {
			System.out.println(String.format("Dequeued: %s", queue.dequeue()));
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

		private int[] indices;
		private int currentIndex = 0;

		public RandomIterator() {
			indices = new int[N];

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
