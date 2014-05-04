/**
 * Homework#1 Travesal Project
 * 
 * 14.05.03 + bf(balanced factor)
 * 
 * @author Francis
 * @version 0.2
 * @since 14.05.03
 */
public class Tree {

	private String key;

	private Integer bf;

	private Tree left;

	private Tree right;

	public Tree() {
		super();
	}

	/**
	 * @param key
	 *            key
	 * @param bf
	 *            balnaced factor (균형 인수)
	 * @param left
	 *            left child
	 * @param right
	 *            right child
	 */
	public Tree(String key, Integer bf, Tree left, Tree right) {
		super();
		this.key = key;
		this.bf = bf;
		this.left = left;
		this.right = right;
	}

	public Integer getBf() {
		return bf;
	}

	public String getKey() {
		return key;
	}

	public Tree getLeft() {
		return left;
	}

	public Tree getRight() {
		return right;
	}

	public void setBf(Integer bf) {
		this.bf = bf;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLeft(Tree left) {
		this.left = left;
	}

	public void setRight(Tree right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Tree [key=" + key + ", bf=" + bf + "]";
	}

}
