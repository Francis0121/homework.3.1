/**
 * <h1>Tree를 구성할 Object</h1>
 * 
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.04.29
 */
public class Tree {

	private Item item;

	private Tree left;

	private Tree right;

	public Tree() {
		super();
	}

	/**
	 * @param item
	 *            현재 Tree의 Value
	 * @param left
	 *            현재 Tree가 가리키는 왼쪽 Node
	 * @param right
	 *            현재 Tree가 가리키는 오른쪽 Node
	 * 
	 */
	public Tree(Item item, Tree left, Tree right) {
		this.item = item;
		this.left = left;
		this.right = right;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Tree getLeft() {
		return left;
	}

	public void setLeft(Tree left) {
		this.left = left;
	}

	public Tree getRight() {
		return right;
	}

	public void setRight(Tree right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "Tree : 	item=" + item + "\n" +
						"left=" + left + "\n" +
						"right=" + right;
	}

}
