import java.util.Arrays;

/**
 * Homework#6 B-Tree
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.16
 */
public class Tree {

	private Node[] nodes;

	private Tree[] subTrees;

	private int m;

	/**
	 * @param m
	 *            m >= 1, m 개의 subtree
	 */
	public Tree(int m) {
		this.m = m;
		this.nodes = new Node[m - 1];
		this.subTrees = new Tree[m];
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getNumberOfKeyNode() {
		int count = 0;
		for (Node node : nodes) {
			if (node != null && !"".equals(node.getKey())) {
				count++;
			}
		}
		return count;
	}
	
	public int getNumberOfSubTree() {
		int count = 0;
		for (Tree tree: subTrees) {
			if (tree != null) {
				count++;
			}
		}
		return count;
	}

	public void setNode(Node node, int index) {
		if (this.m - 1 <= index) {
			// TODO IndexOutOfException
			return;
		}
		this.nodes[index] = node;
		Arrays.sort(this.nodes, 0, getNumberOfKeyNode()); // 값 입력하고 node 정렬
	}

	public Node getNode(int index) {
		if (this.m - 1 <= index) {
			// TODO IndexOutOfException
			return null;
		}
		return this.nodes[index];
	}

	public void setSubTree(Tree tree, int index) {
		if (this.m <= index) {
			// TODO IndexOutOfException
			return;
		}
		subTrees[index] = tree;
	}

	public Tree getSubTree(int index) {
		if (this.m <= index) {
			// TODO IndexOutOfException
			return null;
		}
		return this.subTrees[index];
	}
	
	public void deleteKey(String key) {
		Node[] newNodes = new Node[this.m-1];
		int j=0;
		for(int i=0; i< getNumberOfKeyNode(); i++){
			Node node = nodes[i];
			if(key.equals(node.getKey())){
				continue;
			}
			newNodes[j++] = node;
		}
		this.nodes = newNodes;
	}

	public Node[] getNodes() {
		return nodes;
	}

	public Tree[] getSubTrees() {
		return subTrees;
	}

	@Override
	public String toString() {
		return Arrays.toString(nodes) + "\n\tSub{" + Arrays.toString(subTrees)+"}";
	}

	

}
