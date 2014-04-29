/**
 * <h1>Homework1. Travesal Project</h1>
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.03.04
 */
public class Traversal {

	public void preOrder(Tree node) {
		System.out.print(node.getItem().getKey() + " ");
		if (node.getLeft() != null)
			preOrder(node.getLeft());
		if (node.getRight() != null)
			preOrder(node.getRight());
	}

	public void postOrder(Tree node) {
		if (node.getLeft() != null)
			postOrder(node.getLeft());
		if (node.getRight() != null)
			postOrder(node.getRight());
		System.out.print(node.getItem().getKey() + " ");
	}

	public void inOrder(Tree node) {
		if (node == null)
			return;
		if (node.getLeft() != null)
			inOrder(node.getLeft());
		System.out.print(node.getItem().getKey() + " ");
		if (node.getRight() != null)
			inOrder(node.getRight());
	}
}
