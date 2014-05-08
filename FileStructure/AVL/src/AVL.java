import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Homework#5 AVL
 * 
 * @author Francis
 * @version 0.1
 * @since 14.05.04
 */
public class AVL {

	private static Logger logger = LoggerFactory.getLogger(AVL.class);

	/**
	 * @param tree
	 *            root Tree
	 * @param string
	 *            insert key
	 * @return root Tree
	 */
	public Tree insertAVL(Tree root, String newKey) {
		// ~ Case 1. Null
		if (root == null) {
			logger.info("Root");
			return new Tree(newKey, 1, 0, null, null);
		}

		// Step 1 : newKey 삽입 위치 조사
		boolean found = false;
		// increase Value = 증가값
		int iValue;
		Tree p = root, q = null,
		// aNode, bNode 회전시킬 Node
		aNode = root, bNode = null,
		// grandParent (Balanced Tree로 구성된 후 연결할 부모 Node)
		parent = null,
		// newTree = y(새로 삽입되는 node)
		newTree = null;

		Integer height = 1;
		while (p != null && !found) {
			if (p.getBf() != 0) {
				aNode = p;
				parent = q;
			}

			height++;
			if (newKey.compareTo(p.getKey()) < 0) {
				q = p;
				p = p.getLeft();
			} else if (newKey.compareTo(p.getKey()) > 0) {
				q = p;
				p = p.getRight();
			} else {
				newTree = p;
				found = true;
			}
		}

		// Step 2:newKey를 삽입하고 균형화. newKey는 트리에 없음. q의 적절한 자식으로 삽입
		if (found) {
			logger.warn("Exist key value [ " + newKey + " ]");
			return root;
		}

		// 새로운 Tree 노드 삽입
		newTree = new Tree(newKey, height, 0, null, null);
		if (newKey.compareTo(q.getKey()) < 0) {
			q.setLeft(newTree);
		} else {
			q.setRight(newTree);
		}

		// BF 판단
		if (newKey.compareTo(aNode.getKey()) > 0) {
			p = aNode.getRight();
			bNode = p;
			iValue = -1;
		} else {
			p = aNode.getLeft();
			bNode = p;
			iValue = 1;
		}

		while (p != newTree) {
			if (newKey.compareTo(p.getKey()) > 0) {
				p.setBf(-1);
				p = p.getRight();
			} else {
				p.setBf(1);
				p = p.getLeft();
			}
		}

		// ~ Case 2. Balanced Tree(O)
		boolean unbalanced = true;
		int aHaveBf = aNode.getBf() + iValue;
		if (aHaveBf != 2 && aHaveBf != -2) {
			aNode.setBf(aHaveBf);
			unbalanced = false;
		}

		// ~ Case 3. Balanced Tree(X)
		if (unbalanced) {
			switch (iValue) {
			case 1: // Left
				if (bNode.getBf() == 1)
					doLL(aNode, bNode);
				else
					bNode = doLR(aNode, bNode);
				break;
			case -1: // Right
				if (bNode.getBf() == 1)
					bNode = doRL(aNode, bNode);
				else
					doRR(aNode, bNode);
				break;
			}
			if (parent == null)
				root = bNode;
			else if (aNode == parent.getLeft())
				parent.setLeft(bNode);
			else if (aNode == parent.getRight())
				parent.setRight(bNode);
		} // if(unbalanced == true)

		return root;
	}

	private void decreaseHeight(Tree node) {
		if (node == null)
			return;
		if (node.getLeft() != null)
			decreaseHeight(node.getLeft());
		node.setHeight(node.getHeight() - 1);
		if (node.getRight() != null)
			decreaseHeight(node.getRight());
	}

	private void increaseHeight(Tree node) {
		if (node == null)
			return;
		if (node.getLeft() != null)
			increaseHeight(node.getLeft());
		node.setHeight(node.getHeight() + 1);
		if (node.getRight() != null)
			increaseHeight(node.getRight());
	}

	private void doLL(Tree aNode, Tree bNode) {
		logger.info("LL");
		aNode.setHeight(aNode.getHeight() + 1);
		bNode.setHeight(bNode.getHeight() - 1);
		decreaseHeight(bNode.getLeft());
		increaseHeight(aNode.getRight());

		aNode.setLeft(bNode.getRight());
		bNode.setRight(aNode);
		aNode.setBf(0);
		bNode.setBf(0);
	}

	private Tree doLR(Tree aNode, Tree bNode) {
		logger.info("LR");
		Tree cNode = bNode.getRight();

		cNode.setHeight(cNode.getHeight() - 2);
		aNode.setHeight(aNode.getHeight() + 1);
		increaseHeight(aNode.getRight());
		decreaseHeight(cNode.getLeft());
		decreaseHeight(cNode.getRight());

		bNode.setRight(cNode.getLeft());
		aNode.setLeft(cNode.getRight());
		cNode.setLeft(bNode);
		cNode.setRight(aNode);

		switch (cNode.getBf()) {
		case 1:
			aNode.setBf(-1);
			bNode.setBf(0);
			break;
		case -1:
			bNode.setBf(1);
			aNode.setBf(0);
			break;
		case 0:
			bNode.setBf(0);
			aNode.setBf(0);
			break;
		}
		cNode.setBf(0);
		return cNode;
	}

	private void doRR(Tree aNode, Tree bNode) {
		logger.info("RR");
		aNode.setHeight(aNode.getHeight() + 1);
		bNode.setHeight(bNode.getHeight() - 1);
		decreaseHeight(bNode.getRight());
		increaseHeight(aNode.getLeft());

		aNode.setRight(bNode.getLeft());
		bNode.setLeft(aNode);
		aNode.setBf(0);
		bNode.setBf(0);
	}

	private Tree doRL(Tree aNode, Tree bNode) {
		logger.info("RL");
		Tree cNode = bNode.getLeft();

		cNode.setHeight(cNode.getHeight() - 2);
		aNode.setHeight(aNode.getHeight() + 1);
		increaseHeight(aNode.getLeft());
		decreaseHeight(cNode.getLeft());
		decreaseHeight(cNode.getRight());

		bNode.setLeft(cNode.getRight());
		aNode.setRight(cNode.getLeft());
		cNode.setLeft(aNode);
		cNode.setRight(bNode);

		switch (cNode.getBf()) {
		case 1:
			aNode.setBf(0);
			bNode.setBf(-1);
			break;
		case -1:
			aNode.setBf(1);
			bNode.setBf(0);
			break;
		case 0:
			bNode.setBf(0);
			aNode.setBf(0);
			break;
		}
		cNode.setBf(0);
		return cNode;
	}

}
