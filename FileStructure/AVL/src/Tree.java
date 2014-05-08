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

	private Integer height;

	private Integer bf;

	private Tree left;

	private Tree right;

	public Tree() {
		super();
	}

	/**
	 * @param key
	 *            key
	 * @param height
	 *            높이
	 * @param bf
	 *            balnaced factor (균형 인수)
	 * @param left
	 *            left child
	 * @param right
	 *            right child
	 */
	public Tree(String key, Integer height, Integer bf, Tree left, Tree right) {
		super();
		this.key = key;
		this.height = height;
		this.bf = bf;
		this.left = left;
		this.right = right;
	}

	public Integer getBf() {
		return bf;
	}

	public Integer getHeight() {
		return height;
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

	public void setHeight(Integer height) {
		this.height = height;
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
		return "Tree [key=" + key + ", height=" + height + ", bf=" + bf
				+ ", left=" + left + ", right=" + right + "]";
	}

}
