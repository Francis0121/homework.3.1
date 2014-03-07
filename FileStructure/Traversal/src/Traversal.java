/**
 * Tree를 탐색하는 클래스.
 * 
 * @author Francis
 * 
 * @version v0.1 14.03.04
 */
public class Traversal {

	public void preOrder(Tree node) {
		System.out.print(node.getValue() + " ");
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
		System.out.print(node.getValue() + " ");
	}

	public void inOrder(Tree node) {
		if (node.getLeft() != null)
			inOrder(node.getLeft());
		System.out.print(node.getValue() + " ");
		if (node.getRight() != null)
			inOrder(node.getRight());
	}
}
