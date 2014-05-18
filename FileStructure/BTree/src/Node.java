/**
 * Homework#6 B-Tree
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.16
 */
public class Node implements Comparable<Node> {

	private String key;

	public Node() {
		super();
	}

	public Node(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int compareTo(Node node) {
		if (node == null)
			return 1;

		if (node.getKey().compareTo(key) > 0)
			return -1;
		else if (node.getKey().compareTo(key) < 0)
			return 1;
		else
			return 0;

	}

	@Override
	public String toString() {
		return "Node [key=" + key + "]";
	}

}
