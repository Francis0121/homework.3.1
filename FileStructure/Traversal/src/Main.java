/**
 * Tree의 최상단 rootNode를 생성하면서 동시에 다른 subtree의 노드도 같이 입력해준다.
 * 
 * Travesal 이라는 class에 구현되어있는 pre, in, post order method를 사용해서 Tree를 탐색한다.
 * 
 * @author Francis
 * 
 * @version v0.1 14.03.04
 */
public class Main {

	public static void main(String[] args) {

		// Tree Node 생성
		Tree node = new Tree("A", 
					new Tree("B", 	new Tree("D"), 	null), 
					new Tree("C", 	new Tree("E", 	null, new Tree("G")), 
									new Tree("F", 	new Tree("H"), null)));

		Traversal traversal = new Traversal();

		System.out.print("Pre Order : ");
		traversal.preOrder(node);
		System.out.println();

		System.out.print("In Order : ");
		traversal.inOrder(node);
		System.out.println();

		System.out.print("PostOrder : ");
		traversal.postOrder(node);
		System.out.println();
	}

}
