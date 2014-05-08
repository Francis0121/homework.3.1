/**
 * Homework#1 Travesal Project
 * 
 * 14.05.04 + Print Node Balance Factor
 * 
 * @author Francis
 * @version 0.2
 * @since 14.05.04
 */
public class Traversal {

	private Integer height = 9;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void inOrder(Tree node) {
		if (node == null)
			return;
		if (node.getLeft() != null)
			inOrder(node.getLeft());
		System.out.print(node.getKey() + "[" + node.getBf() + ", "
				+ node.getHeight() + "] ");
		if (node.getHeight() > this.height) {
			height = node.getHeight();
		}
		if (node.getRight() != null)
			inOrder(node.getRight());
	}

}
