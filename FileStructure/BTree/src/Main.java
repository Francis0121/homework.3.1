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

	public static void main(String[] args) {

		Tree root = null;
		BTree tree = new BTree(3);
		Traversal traversal = new Traversal();
		for (String in : inputs) {
			root = tree.insertBTree(root, in);
			System.out.println("[ Insert ] : " + in);
			System.out.println("[ Height ] : " + tree.getHeight());
			traversal.inOrder(root);
			System.out.println();
		}

		root = null;
		tree = new BTree(4);
		for (String in : inputs) {
			root = tree.insertBTree(root, in);
			System.out.println("[ Insert ] : " + in);
			System.out.println("[ Height ] : " + tree.getHeight());
			traversal.inOrder(root);
			System.out.println();
		}

	}

}
