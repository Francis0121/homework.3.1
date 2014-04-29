import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Francis
 * 
 * @version 0.1
 * 
 * @since 14.04.29
 */
public class BinaraySearchTree {

	private static Logger logger = LoggerFactory
			.getLogger(BinaraySearchTree.class);

	public static final String HASH_KEY[] = { "Parent", "Me" };

	public static int count = 0;

	public void countTree(Tree root) {
		if (root.getLeft() != null) {
			countTree(root.getLeft());
		} else if (root.getRight() != null) {
			countTree(root.getRight());
		} else {
			count++;
		}
	}

	/**
	 * @param root
	 *            검색할 Binary Search Tree의 Root Node
	 * @param item
	 *            검색할 Item
	 */
	public HashMap<String, Tree> searchBST(Tree parent, Tree root, Item item) {
		Tree p = root;
		if (p == null) {
			return null;
		}
		if (p.getItem().getKey().equals(item.getKey())) {
			HashMap<String, Tree> map = new HashMap<>();
			map.put(HASH_KEY[0], parent);
			map.put(HASH_KEY[1], p);
			return map;
		}
		if (p.getItem().getKey().compareTo(item.getKey()) > 0) {
			return searchBST(p, p.getLeft(), item);
		} else {
			return searchBST(p, p.getRight(), item);
		}
	}

	/**
	 * @param root
	 *            삭제할 Binary Search Tree의 Root Node
	 * @param delItem
	 *            삭제할 Item
	 */
	public void deleteBST(TreeWrap treeWrap, Item delItem) {
		Tree root = treeWrap.getTree();
		Tree parent = root;
		Tree grandParent = null;
		boolean left = true;
		boolean right = true;

		while (parent != null) {
			if (delItem.getKey().compareTo(parent.getItem().getKey()) < 0) {
				grandParent = parent;
				parent = parent.getLeft();
				left = true;
				right = false;
			} else if (delItem.getKey().compareTo(parent.getItem().getKey()) > 0) {
				grandParent = parent;
				parent = parent.getRight();
				left = false;
				right = true;
			} else {
				break;
			}
		}

		if (parent == null) {
			return;
		} else {
			int degree = 0;
			boolean pleft = false;
			boolean pright = false;

			if (parent.getLeft() != null) {
				degree += 1;
				pleft = true;
			}

			if (parent.getRight() != null) {
				degree += 1;
				pright = true;
			}

			switch (degree) {
			case 0:
				if (grandParent != null) {
					if (left) {
						grandParent.setLeft(null);
					} else if (right) {
						grandParent.setRight(null);
					}
				} else {
					treeWrap.setTree(null);
				}
				logger.debug("Leaf node");
				break;

			case 1:
				if (left) {
					if (pleft) {
						if (grandParent != null) {
							grandParent.setLeft(parent.getLeft());
							setHeight(parent.getLeft());
						} else {
							// Java는 Call by referecen 임으로 parent를 바꾼다고 해도
							// 이전 클래스에서 변수 값이 반영되지 않아 Wrap 클래스를 만들어 set 으로 대체한다.
							// parent = parent.getLeft();
							treeWrap.setTree(parent.getLeft());
						}
					} else if (pright) {
						if (grandParent != null) {
							grandParent.setLeft(parent.getRight());
							setHeight(parent.getRight());
						} else {
							// parent = parent.getRight();
							treeWrap.setTree(parent.getRight());
						}
					}
				} else if (right) {
					if (pleft) {
						if (grandParent != null) {
							grandParent.setRight(parent.getLeft());
							setHeight(parent.getLeft());
						} else {
							// parent = parent.getLeft();
							treeWrap.setTree(parent.getLeft());
						}
					} else if (pright) {
						if (grandParent != null) {
							grandParent.setRight(parent.getRight());
							setHeight(parent.getRight());
						} else {
							// parent = parent.getRight();
							treeWrap.setTree(parent.getRight());
						}
					}
				}
				logger.debug("One node exist");
				break;
			case 2:
				getHeight(parent.getLeft());
				Integer lHeight = this.map.size();
				logger.debug(this.map.toString() + " size : " + lHeight);
				this.map.clear();

				getHeight(parent.getRight());
				Integer rHeight = this.map.size();
				logger.debug(this.map.toString() + " size : " + rHeight);
				this.map.clear();

				if (lHeight < rHeight) {
					Tree change = parent.getRight();
					Tree grandChange = parent;
					while (change.getLeft() != null) {
						grandChange = change;
						change = change.getLeft();
					}

					Item item = change.getItem();
					item.setHeight(parent.getItem().getHeight());
					parent.setItem(item);

					if (grandChange.getItem().getHeight() != 0) {
						if (change.getRight() != null) {
							grandChange.setLeft(change.getRight());
							setHeight(change.getRight());
						} else {
							grandChange.setLeft(null);
						}
					} else {
						if (change.getRight() != null) {
							grandChange.setRight(change.getRight());
							setHeight(change.getRight());
						} else {
							grandChange.setRight(null);
						}
					}

				} else { // lHeight <= rHeight Left로 처리
					Tree change = parent.getLeft();
					Tree grandChange = parent;
					while (change.getRight() != null) {
						grandChange = change;
						change = change.getRight();
					}

					Item item = change.getItem();
					item.setHeight(parent.getItem().getHeight());
					parent.setItem(item);

					if (grandChange.getItem().getHeight() != 0) {
						if (change.getLeft() != null) {
							grandChange.setRight(change.getLeft());
							setHeight(change.getLeft());
						} else {
							grandChange.setRight(null);
						}
					} else {
						if (change.getLeft() != null) {
							grandChange.setLeft(change.getLeft());
							setHeight(change.getLeft());
						} else {
							grandChange.setLeft(null);
						}
					}
				}
				logger.debug("Two node exist");
				break;
			}

		}

		// HashMap<String, Tree> map = searchBST(null, root, delItem);
		// Tree p = map.get(HASH_KEY[1]); // 주어진 키를 가진 노드
		// Tree parent = map.get(HASH_KEY[0]); // 삭제할 노드의 부모노드
		//
		// if (p == null) {
		// logger.debug("No exist value [ " + delItem.getKey() + "]");
		// return;
		// }
		// logger.debug("Root : " + root.getItem().toString());
		// logger.debug("P : " + p.getItem().toString());
		// // logger.debug("Parent : " + parent.getItem().toString());
		//
		// if (p.getLeft() == null && p.getRight() == null) {
		// if (parent.getLeft() == p) {
		// parent.setLeft(null);
		// } else {
		// parent.setRight(null);
		// }
		// logger.debug("Leaf Node Delete " + p.getItem().getKey());
		// } else if (p.getLeft() == null || p.getRight() == null) {
		// if (p.getLeft() != null) {
		// if (parent.getLeft() == p) {
		// parent.setLeft(p.getLeft());
		// setHeight(parent.getLeft());
		// } else {
		// parent.setRight(p.getLeft());
		// setHeight(parent.getRight());
		// }
		// } else {
		// if (parent.getLeft() == p) {
		// parent.setLeft(p.getRight());
		// setHeight(parent.getLeft());
		// } else {
		// parent.setRight(p.getRight());
		// setHeight(parent.getRight());
		// }
		// }
		//
		// logger.debug("Right or Left Delete " + p.getItem().getKey());
		// } else if (p.getLeft() != null || p.getRight() != null) {
		//
		// getHeight(p.getLeft());
		// Integer lHeight = this.map.size();
		// logger.debug(this.map.toString() + " size : " + lHeight);
		// this.map.clear();
		//
		// getHeight(p.getRight());
		// Integer rHeight = this.map.size();
		// logger.debug(this.map.toString() + " size : " + rHeight);
		// this.map.clear();
		//
		// if (lHeight < rHeight) {
		// Tree q = p.getRight();
		// while (q.getLeft() != null) {
		// q = q.getLeft();
		// }
		// Item item = p.getItem();
		// item.setKey(q.getItem().getKey());
		//
		// deleteBST(p.getRight(), p.getItem());
		// } else { // lHeight <= rHeight Left로 처리
		// Tree q = p.getLeft();
		// while (q.getRight() != null) {
		// q = q.getRight();
		// }
		// Item item = p.getItem();
		// item.setKey(q.getItem().getKey());
		//
		// deleteBST(p.getLeft(), q.getItem());
		// }
		// }
	}

	private HashMap<Integer, Boolean> map = new HashMap<>();

	private void getHeight(Tree node) {
		this.map.put(node.getItem().getHeight(), true);
		if (node.getLeft() != null)
			getHeight(node.getLeft());
		if (node.getRight() != null)
			getHeight(node.getRight());
	}

	private void setHeight(Tree node) {
		Item item = node.getItem();
		item.setHeight(item.getHeight() - 1);
		if (node.getLeft() != null)
			setHeight(node.getLeft());
		if (node.getRight() != null)
			setHeight(node.getRight());
	}

	/**
	 * @param root
	 *            입력할 Binary Search Tree의 Root Node
	 * @param newItem
	 *            입력할 Item
	 * @return
	 */
	public Tree insertBST(Tree root, Item newItem) {
		Tree q = null;
		Tree p = root;
		Integer height = 0;
		while (p != null) {
			if (newItem.getKey().equals(p.getItem().getKey())) {
				logger.error("Exist key value [" + newItem.getKey() + "]");
			}
			q = p;
			height += 1;
			if (newItem.getKey().compareTo(p.getItem().getKey()) < 0) {
				p = p.getLeft();
			} else {
				p = p.getRight();
			}
		}

		newItem.setHeight(height);
		Tree newTree = new Tree(newItem, null, null);

		if (root == null) {
			root = newTree;
		} else if (newItem.getKey().compareTo(q.getItem().getKey()) < 0) {
			q.setLeft(newTree);
		} else {
			q.setRight(newTree);
		}

		return root;
	}
}
