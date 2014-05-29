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

	public Tree deleteBTree(Tree root, String key) {
		// TODO find key node
		// 동시에 선행키나 후행키가 사용가능한지 여부 판단
		if (root == null) { // 삭제 할것이 없음으로 null 반환
			return null;
		}

		Tree p = null; // Tree 를 가리키는 포인터 Temp 용
		Tree current = root; // 길을 찾기위한 pointer
		boolean found = false;

		Stack<Tree> stackTree = new Stack<Tree>();
		Stack<Integer> stackIndex = new Stack<Integer>();
		stackTree.push(root);

		int nodePosition = -1;
		cBlock: while (current != null) {
			Integer pointIndex = 0;
			nBlock: for (int i = 0; i < current.getM() - 1; i++) {
				Node node = current.getNode(i);
				if (node == null)// node가 null이면 해당 tree를 더 돌필요가 없음으로 break
					break nBlock;
				
				if (node.getKey().equals(key)) {
					// 값이 Found 되면 해당 Parent가 입력할 공간
					logger.info("Found : Exist value : " + key);
					found = true;
					nodePosition = i;
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
		
		if(!found){// 현재 Tree에 값이 존재 하지 않기 때문에 root를 return
			return root;
		}
		
		// Leaf Node가 아니면 Leaf Node로 변경해야함. 후행키
		Tree select = stackTree.pop();
		Tree beforePost = select;
		logger.info("후행키 탐색 전" + select.toString());
		if(select.getNumberOfSubTree() >= 2){
			stackTree.push(select);
			logger.info("후행키 탐색 Node Position " + nodePosition);
			current = select.getSubTree(nodePosition+1);
			if(current != null){
				stackTree.push(current);
				stackIndex.push(nodePosition+1);
			}
			
			while(current != null){
				p = current.getSubTree(0);
				if(p != null){
					stackTree.push(p);
					stackIndex.push(0);
				}
				current = p;
			}
			select = stackTree.pop();
			beforePost.deleteKey(key);
			beforePost.setNode(new Node(select.getNode(0).getKey()), beforePost.getNumberOfKeyNode());
		}
		logger.info("후행키 탐색 후" + select.toString());
		
		boolean finished = false;
		boolean isPre = false; // 선행키
		boolean isPost = false; // 후행키
		// TODO Delete Node
		Tree parent = null;
		
		do {
			if(root == select && root.getNumberOfSubTree() == 0){
				finished = true;
				select.deleteKey(key);
				logger.info("Confirm Delete # " + select);
				logger.info("Delete Key Node");
				break;
			}
			parent = stackTree.pop();//삭제될 Node의 부모노드
			int index = stackIndex.pop();
			logger.info("Parent " + parent);
			logger.info("Index " + index);
			if(parent != null){
				if(index == 0){//Confirm Post
					p = parent.getSubTree(1);
					if(p != null){
						if(p.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
							isPost = true;
						}
					}
				}else if(index == subTreeSize-1){// Confirm Pre
					p = parent.getSubTree(subTreeSize-2);
					if(p != null){
						if(p.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
							isPre = true;
						}
					}
				}else{//Confirm Pre & Post
					p = parent.getSubTree(index+1); // Post
					if(p != null){
						if(p.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
							isPost = true;
						}	
					}
					p = parent.getSubTree(index-1); // Pre
					if(p != null){
						if(p.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
							isPre = true;
						}
					}
				}
			}
			logger.info("isPost [" + isPost + "], isPre [" + isPre + "]");
			if ( (select.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1) ) ) {
				// Current node is root or is not too small
				finished = true;
				select.deleteKey(key);
				logger.info("Confirm Delete # " + select);
				logger.info("Delete Key Node");
			} else if (isPre) {
				// A-sibling > minimum 재분배 가능
				Tree pre = parent.getSubTree(index-1);
				// Node값 변경
				select.setNode(parent.getNode(index-1), 0);// Parent를 가져오고
				parent.setNode(pre.getNode(pre.getNumberOfKeyNode()-1), index-1);// Parent SubTree Node를 가져오고
				pre.deleteKey(pre.getNode(pre.getNumberOfKeyNode()-1).getKey());// SubTree에서의 값을 지우고
				// Tree 변경전 node 존재시 뒤로 미룸
				if(select.getSubTree(0) != null){// TODO 0 이후도 해야될것 같다고 생각됨
					select.setSubTree(select.getSubTree(0), 1);
				}
				// Tree값 변경
				select.setSubTree(pre.getSubTree(subTreeSize-1), 0); // pre의 마지막 subtree 가져오고
				pre.setSubTree(null, subTreeSize-1);//지우고
				finished = true;
				logger.info("Redistribution Pre");
			}else if(isPost){ 
				// A-sibling > minimum 재분배 가능
				Tree post = parent.getSubTree(index+1);
				// Node 변경
				select.setNode(parent.getNode(index), 0); // Parent를 가져오고
				parent.setNode(post.getNode(0), index); // Parent SubTree Node를 가져오고
				post.deleteKey(post.getNode(0).getKey()); // SubTree에서의 값을 지우고
				// Tree 변경
				select.setSubTree(post.getSubTree(0), 1); // 뒤 Tree의 가장 앞 subtree를 가져와서 select마지막에 넣는다
				// Post Tree index 당기기
				for(int i=0; i<post.getSubTrees().length; i++){
					Tree copy = post.getSubTree(i+1);
					if(copy != null){
						post.setSubTree(copy, i);
					}
				}
				post.setSubTree(null, post.getSubTrees().length-1);
				finished = true;
				logger.info("Redistribution Post");
			}else {
				// TODO Merger A-sibling
				if(index == 0){
					if(parent.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
						finished = true;
						select.setNode(parent.getNode(0), 0);
						
						Tree post = parent.getSubTree(1);
						int j = 1;
						for(int i=0; i<post.getNumberOfSubTree(); i++){
							Tree tree = post.getSubTree(i);
							select.setSubTree(tree, j++);
						}
						logger.info("Index0 Step 1" + select);
						
						j = 1;
						for(int i=0; i<post.getNumberOfKeyNode(); i++){
							Node node = post.getNode(i);
							select.setNode(node, j++);
						}
						logger.info("Index0 Step 2" + select);
						
						parent.deleteKey(parent.getNode(0).getKey());
						parent.setSubTree(select, 0);
						for(int i=1; i<parent.getSubTrees().length; i++){
							Tree copy = parent.getSubTree(i+1);
							if(copy != null){
								parent.setSubTree(copy, i);
							}
						}
						parent.setSubTree(null, parent.getSubTrees().length-1);
						
						
					}else{
						int j = 0;
						Stack<String> stackKey = new Stack<String>();
						for (int i = 0; i < parent.getNumberOfKeyNode(); i++) {
							Node node = parent.getNode(i);
							select.setNode(node, j++);
							stackKey.push(node.getKey());
						}
						
						Tree post = parent.getSubTree(index+1);
						for (int i = 0; i < post.getNumberOfKeyNode(); i++) {
							Node node = post.getNode(i);
							select.setNode(node, j++);
						}
						parent.setSubTree(null, index+1); // Post SubTree는 합병으로 사라지고
						select = parent; // 지워야할 노드는 Parent로 변경됨.
						
						while(!stackKey.isEmpty()){
							parent.deleteKey(stackKey.pop());
						}
					}					
					logger.info("Merge Temp Index == 0 "+ select.toString());
				}else if(index == subTreeSize-1){ // 마지막 Node였던경우
					int j =0;
					
					Tree pre = parent.getSubTree(index-1);
					parent.setSubTree(null, index-1);
					for (int i = 0; i < pre.getNumberOfKeyNode(); i++) {
						Node node = pre.getNode(i);
						Tree tree = pre.getSubTree(i);
						select.setNode(node, j);
						select.setSubTree(tree, j++);
					}
					
					select.setNode(parent.getNode(index-1), j);
					parent.deleteKey(parent.getNode(index-1).getKey());
					
					parent.setSubTree(select, index-1);
					parent.setSubTree(null, index);
					finished = true;
				}else{
					int j = 0;
					select.setSubTree(select.getSubTree(0), 2);// 합병시 subTree가 이동됨으로먼저하고
					
					Tree pre = parent.getSubTree(index-1); // 왼쪽 노드부터 합병임으로 합병하고
					parent.setSubTree(null, index-1);// 왼쪽 노드는 삭제 됨
					for (int i = 0; i < pre.getNumberOfKeyNode(); i++) {
						Node node = pre.getNode(i);
						Tree tree = pre.getSubTree(i);
						select.setNode(node, j);
						select.setSubTree(tree, j++);
					}
					logger.info("Step 1 " +select);
					select.setSubTree(pre.getSubTree(pre.getNumberOfKeyNode()), j);
					Stack<String> stackKey = new Stack<String>();
					for (int i = 0; i < index; i++) {// 부모노드입력시키면
						Node node = parent.getNode(i);
						select.setNode(node, j++);
						stackKey.push(node.getKey());
					}
					logger.info("Step 2 " +select);
					
					// Index 변경
					parent.setSubTree(select, 0); //index 0에 새로 만든것을 넣음
					parent.setSubTree(null, index); // index 0 으로 변경 하면서 null로 변경
					if(parent.getNumberOfKeyNode() > (Math.round(subTreeSize / 2.0) - 1)){
						finished = true;
						for(int i=index; i<parent.getSubTrees().length; i++){
							Tree copy = parent.getSubTree(i+1);
							if(copy != null){
								parent.setSubTree(copy, i);
							}
						}
						parent.setSubTree(null, parent.getSubTrees().length-1);
					}
					
					//값지움
					while(!stackKey.isEmpty()){
						parent.deleteKey(stackKey.pop());
					}
					logger.info("Merge Temp Index != 0 "+ select);
					select = parent;
					logger.info("Merge Changer Select " + select);
				}
				logger.info("Merge");
			}
		
		} while (!finished && !stackTree.isEmpty());
		
		if(root.getNumberOfKeyNode() == 0){
			this.height--;
			root = root.getSubTree(0);
		}
		logger.info("##################################");
		logger.info("Root\n"+ root);
		logger.info("##################################");
		return root;
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
					if (i < Math.round(treeIndex / 2.0))
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
					int moveIndex = this.subTreeSize - 1;
					while (moveIndex > 0 && startIndex < moveIndex) {
						parentTree
								.setSubTree(
										parentTree.getSubTree(moveIndex - 1),
										moveIndex);
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
					height++;
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
