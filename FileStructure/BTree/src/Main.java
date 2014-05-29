/**
 * Homework#6 B-Tree
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.16
 * 
 */
public class Main {

	private static String[] inputs = { "c", "s", "d", "t", "a", "m", "p", "i",
			"b", "w", "n", "g", "u", "r", "k", "e", "h", "o", "l", "j", "y",
			"q", "z", "f", "x", "v" };

	private static String[] deletes = { "r", "o", "t", "b", "w", "z", "v", "a",
			"s", "j", "g", "l", "n", "f", "c", "d", "m", "x", "q", "y", "k",
			"p", "h", "e", "i", "u" };

	// private static String[] deletes = { "r", "o", "t", "b", "w", "z", "v",
	// "a",
	// "s", "j", "g", "l", "n", "f", "c", "d", "m", "x", "q", "y", "k",
	// "p", "h", "e", "i", "u" };

	public static void main(String[] args) {

		Tree root = null;
		BTree tree = new BTree(3);
		Traversal traversal = new Traversal();
		// ~ M = 3
		System.out.println("Insert M = 3");
		for (String in : inputs) {
			root = tree.insertBTree(root, in);
			System.out.println("[ Insert ] : " + in);
			System.out.println("[ Height ] : " + tree.getHeight());
			traversal.inOrder(root);
			System.out.println();
		}

		System.out.println("Delete M = 3");
		for (String del : deletes) {
			root = tree.deleteBTree(root, del);
			System.out.println("[ Delete ] : " + del);
			System.out.println("[ Height ] : " + tree.getHeight());
			traversal.inOrder(root);
			System.out.println();
		}

		// ~ M = 4
		// root = null;
		// tree = new BTree(4);
		// System.out.println("Insert M = 4");
		// for (String in : inputs) {
		// root = tree.insertBTree(root, in);
		// System.out.println("[ Insert ] : " + in);
		// System.out.println("[ Height ] : " + tree.getHeight());
		// traversal.inOrder(root);
		// System.out.println();
		// }
		//
		// System.out.println("Delete M = 4");
		// for (String del : deletes) {
		// root = tree.deleteBTree(root, del);
		// System.out.println("[ Delete ] : " + del);
		// System.out.println("[ Height ] : " + tree.getHeight());
		// traversal.inOrder(root);
		// System.out.println();
		// }

	}

}
