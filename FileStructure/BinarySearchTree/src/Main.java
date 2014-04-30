import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Binary Search Tree</h1>
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.04.29
 */
public class Main {

	public static final String[] NODE = { "one", "two", "thr", "fou", "fiv",
			"six", "sev", "eig", "nin", "ten", "ele", "twe" };

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		Traversal traversal = new Traversal();

		Tree tree1 = null;
		Tree tree2 = null;
		System.out.println("--------------------------");
		System.out.println("----- Start Insert -------");
		for (int i = 0; i < NODE.length; i++) {
			tree1 = bst.insertBST(tree1, new Item(NODE[i]));
			tree2 = bst.insertBST(tree2, new Item(NODE[i]));
			System.out.print("Insert : ");
			traversal.inOrder(tree1);
			System.out.println("");
		}

		TreeWrap treeWrap = new TreeWrap();
		treeWrap.setTree(tree1);

		System.out.println("--------------------------");
		System.out.println("----Start Delete Order----");
		for (int i = 0; i < NODE.length; i++) {
			bst.deleteBST(treeWrap, new Item(NODE[i]));
			System.out.print("Delete Order: ");
			traversal.inOrder(treeWrap.getTree());
			System.out.println();
		}

		treeWrap.setTree(tree2);

		System.out.println("--------------------------");
		System.out.println("---Start Delete Reverse---");

		for (int i = NODE.length - 1; i >= 0; i--) {
			bst.deleteBST(treeWrap, new Item(NODE[i]));
			System.out.print("Delete Reverse Order: ");
			traversal.inOrder(treeWrap.getTree());
			System.out.println();
		}

		logger.debug(tree1.toString());
	}

}
