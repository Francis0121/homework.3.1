/**
 * Homework#6 B-Tree
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.16
 */
public class Traversal {
	public void inOrder(Tree tree) {
		if (tree == null)
			return;
		Tree[] subTrees = tree.getSubTrees();
		Node[] nodes = tree.getNodes();
		for (int i = 0; i < tree.getM(); i++) {
			Tree subTree = subTrees[i];
			inOrder(subTree);
			if (i + 1 < tree.getM() && nodes[i] != null)
				System.out.print(nodes[i].getKey() + " ");
		}
	}
}
