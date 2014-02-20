
public class Subset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			throw new IllegalArgumentException();
		}
		
		int maxItems = Integer.parseInt(args[0]);
		int count = 0;
		
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty()) {			
			String input = StdIn.readString();
			
			if (count < maxItems) {
				queue.enqueue(input);
				count++;
			}
		}		
		
		for (int i = 1; i <= count; i++) {
			System.out.println(queue.dequeue());
		}
	}

}
