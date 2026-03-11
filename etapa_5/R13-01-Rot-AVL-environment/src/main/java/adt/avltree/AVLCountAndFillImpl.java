package adt.avltree;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}
		@Override
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			int balance = calculateBalance(node);
			BSTNode<T> left = (BSTNode<T>) node.getLeft();
			BSTNode<T> right = (BSTNode<T>) node.getRight();
			BSTNode<T> newRoot = null;
			if (balance > 1) {
				int leftBalance = calculateBalance(left);
				if (leftBalance >= 0) {
					LLcounter++;
					newRoot = Util.rightRotation(node);
				} else {
					LRcounter++;
					Util.leftRotation(left);
					newRoot = Util.rightRotation(node);
				}
			} else if (balance < -1) {
				int rightBalance = calculateBalance(right);
				if (rightBalance <= 0) {
					RRcounter++;
					newRoot = Util.leftRotation(node);
				} else {
					RLcounter++;
					Util.rightRotation(right);
					newRoot = Util.leftRotation(node);
				}
			}
			if (node == this.root && newRoot != null) {
				this.root = newRoot;
			}
		}
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null) {
			for (T element : array) {
				if (element != null) {
					this.insert(this.root, element);
				}
			}
		}
	}

	private void insert(adt.bst.BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			BSTNode<T> left = new BSTNode<>();
			BSTNode<T> right = new BSTNode<>();
			left.setParent(node);
			right.setParent(node);
			node.setLeft(left);
			node.setRight(right);
		} else {
			if (element.compareTo(node.getData()) < 0) {
				insert((BSTNode<T>) node.getLeft(), element);
			} else if (element.compareTo(node.getData()) > 0) {
				insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}
}
