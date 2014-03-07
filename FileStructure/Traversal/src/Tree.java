/**
 * Node를 저장해 두는 DTO
 * 
 * @author Francis
 * 
 * @version v0.1 14.03.04
 */
public class Tree {

	private String value; // 값

	private Tree left; // 트리의 왼쪽노드

	private Tree right; // 트리의 오른쪽노드

	public Tree() {
		super();
	}

	public Tree(String value) {
		super();
		this.value = value;
	}

	public Tree(String value, Tree left, Tree right) {
		super();
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}
