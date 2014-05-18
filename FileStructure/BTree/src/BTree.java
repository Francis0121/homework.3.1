import java.util.Arrays;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Homework#6 B-Tree
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.16
 * 
 */
public class BTree {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private int subTreeSize;

	private int height;
	
	/**
	 * @param subTreeSize
	 *            >= 1 m의 크기
	 */
	public BTree(int subTreeSize) {
		super();
		this.subTreeSize = subTreeSize;
		this.height = 1;
	}

	public int getHeight() {
		return height;
	}

	public Tree insertBTree(Tree root, String key) {

		if (root == null) { // root 가 null이면 가장 첫번째에 값을 넣고 시작한다.
			// 현재는 Pointer 값과 공간 값이 다들어가도록 구성되어있음
			Tree tree = new Tree(this.subTreeSize);
			tree.setNode(new Node(key), 0);
			return tree;
		}

		Tree p = null; // Tree 를 가리키는 포인터 Temp 용
		Tree current = root; // 길을 찾기위한 pointer

		Stack<Tree> stackTree = new Stack<Tree>();
		Stack<Integer> stackIndex = new Stack<Integer>();
		stackTree.push(root);

		cBlock: while (current != null) {
			Integer pointIndex = 0;
			nBlock: for (int i = 0; i < current.getM() - 1; i++) {
				Node node = current.getNode(i);
				if (node == null) // node가 null이면 해당 tree를 더 돌필요가 없음으로 break
					break nBlock;
				if (node.getKey().equals(key)) { // 값이 found 되면 break 입력X
					logger.warn("Found : Exist value : " + key);
					break cBlock;
				}
				if (key.compareTo(node.getKey()) < 0) {
					pointIndex = i;
					logger.info("Down " + key + " pointIndex " + pointIndex);
					p = current.getSubTree(pointIndex);
					break;
				} else {
					pointIndex = i + 1;
					logger.info("UP " + key + " pointIndex " + pointIndex);
					p = current.getSubTree(pointIndex);
				}
			}

			if (p != null) {
				logger.info("PUSH " + p);
				logger.info("Index " + pointIndex);
				stackTree.push(p);
				stackIndex.push(pointIndex);
			}
			current = p;
		}

		boolean finish = false; // 삽입이 완료가 되었음을 나타내는 Flag
		logger.info("Stack Size - " + stackTree.size());
		current = stackTree.pop();
		Tree last = null;
		do {
			if (!(current.getNumberOfKeyNode() == (current.getM() - 1))) {
				Node node = current.getNode(current.getNumberOfKeyNode() - 1);
				node = new Node(key);
				current.setNode(node, current.getNumberOfKeyNode());
				finish = true;
				logger.info("Current " + node);
			} else {
				Node[] nodes = current.getNodes();
				int bigIndex = nodes.length + 1;
				Node[] copyNodes = new Node[bigIndex];
				int centerIndex = bigIndex / 2;
				for (int i = 0; i < nodes.length; i++) {
					copyNodes[i] = nodes[i];
				}
				copyNodes[bigIndex - 1] = new Node(key);
				Arrays.sort(copyNodes);
				key = copyNodes[centerIndex].getKey();
				Tree left = new Tree(this.subTreeSize);
				Tree right = new Tree(this.subTreeSize);

				// Node Setting
				int leftIndex = 0, rightIndex = 0;
				for (int i = 0; i < bigIndex; i++) {
					if (i == bigIndex / 2) {
						continue;
					} else if (i < bigIndex / 2) {
						left.setNode(copyNodes[i], leftIndex++);
					} else {
						right.setNode(copyNodes[i], rightIndex++);
					}
				}

				Tree[] trees = current.getSubTrees();
				int treeIndex = trees.length + 1;
				Tree[] copyTrees = new Tree[treeIndex];
				for (int i = 0; i < trees.length; i++) {
					copyTrees[i] = trees[i];
				}
				copyTrees[treeIndex - 1] = last;
				leftIndex = 0;
				rightIndex = 0;
				for (int i = 0; i < treeIndex; i++) {
					if (i < Math.round(treeIndex/2.0))
						left.setSubTree(copyTrees[i], leftIndex++);
					else
						right.setSubTree(copyTrees[i], rightIndex++);
				}

				if (!stackTree.isEmpty()) { // 여기서 지정
					Tree parentTree = stackTree.pop();
					Integer startIndex = stackIndex.pop();
					// 마지막 Node에 저장한다면 상단에서 제거 할 수있음으로 유지?
					last = parentTree.getSubTree(this.subTreeSize - 1);
					
					// 하나씩 옆으로 이동
					int moveIndex = this.subTreeSize-1;
					while(moveIndex > 0 && startIndex < moveIndex){
						parentTree.setSubTree(parentTree.getSubTree(moveIndex-1), moveIndex);
						moveIndex--;
					}
					
					parentTree.setSubTree(left, startIndex);
					if (startIndex + 1 == this.subTreeSize) {
						last = right;
					} else {
						parentTree.setSubTree(right, startIndex + 1);
					}

					current = parentTree;
					logger.info("More than one");
				} else {// Tree의 레벨증가
					height ++;
					Tree newRoot = new Tree(this.subTreeSize);
					newRoot.setNode(new Node(key), 0);
					newRoot.setSubTree(left, 0);
					newRoot.setSubTree(right, 1);
					finish = true;
					root = newRoot;
					logger.info("New root node");
				}
			}
		} while (!finish);

		return root;
	}
}
