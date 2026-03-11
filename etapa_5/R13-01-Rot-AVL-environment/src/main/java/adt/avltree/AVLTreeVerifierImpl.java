package adt.avltree;

import adt.bst.BSTNode;
import adt.bst.BSTVerifierImpl;

/**
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeVerifierImpl<T extends Comparable<T>> extends BSTVerifierImpl<T> implements AVLTreeVerifier<T> {

	private AVLTreeImpl<T> avlTree;

	public AVLTreeVerifierImpl(AVLTree<T> avlTree) {
		super(avlTree);
		this.avlTree = (AVLTreeImpl<T>) avlTree;
	}

	public boolean isAVLTree() {
		return this.isBST() && this.isAVLTree(this.getAVLTree().getRoot());
	}

	private boolean isAVLTree(BSTNode<T> node) {
		boolean answer = true;
		if (node != null && !node.isEmpty()) {
			int balance = Math.abs(this.avlTree.calculateBalance(node));
      		answer = balance <= 1;
			if (answer) {
            	answer = isAVLTree((BSTNode<T>) node.getLeft()) && 
                     isAVLTree((BSTNode<T>) node.getRight());
      		}
		}
		return answer;
	}

	private AVLTreeImpl<T> getAVLTree() {
		return this.avlTree;
	}

}
