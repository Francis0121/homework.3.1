/**
 * Homework#5 AVL
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.03
 */
public class Main {

	private static String[] INPUT = { "k", "o", "m", "i", "n", "u", "v", "e",
			"r", "s", "t", "y", "c", "d", "a", "b" };

	public static void main(String[] args) {

		Tree tree = null;
		AVL avl = new AVL();
		Traversal traversal = new Traversal();

		for (int i = 0; i < INPUT.length; i++) {
			traversal.setHeight(0);
			tree = avl.insertAVL(tree, INPUT[i]);
			traversal.inOrder(tree);
			System.out.println("Height " + traversal.getHeight());
			System.out.println();
		}

	}

}
