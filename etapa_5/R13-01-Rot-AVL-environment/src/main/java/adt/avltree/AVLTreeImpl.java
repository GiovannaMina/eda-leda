package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * A CLASSE AVLTree herda de BSTImpl. VOCE PRECISA SOBRESCREVER A IMPLEMENTACAO
 * DE BSTIMPL RECEBIDA COM SUA IMPLEMENTACAO "OU ENTAO" IMPLEMENTAR OS SEGUITNES
 * METODOS QUE SERAO TESTADOS NA CLASSE AVLTREE:
 *  - insert
 *  - preOrder
 *  - postOrder
 *  - remove
 *  - height
 *  - size
 *
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert(this.root, element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
    if (element != null) {
        if (node.isEmpty()) {
            node.setData(element);
            BSTNode<T> leftNode = new BSTNode<>();
            BSTNode<T> rightNode = new BSTNode<>();
            leftNode.setParent(node);
            rightNode.setParent(node);
            node.setLeft(leftNode);
            node.setRight(rightNode);
        } else {
            if (element.compareTo(node.getData()) < 0) {
                insert((BSTNode<T>) node.getLeft(), element);
            } else if (element.compareTo(node.getData()) > 0) {
                insert((BSTNode<T>) node.getRight(), element);
            }
            this.rebalance(node);
        }
    }
}


@Override
public void remove(T element) {
    BSTNode<T> elementNode = search(element);
    if (element != null && !elementNode.isEmpty()) {
        if (elementNode.isLeaf()) {
            elementNode.setData(null);
            elementNode.setLeft(null);
            elementNode.setRight(null);
            this.rebalanceUp(elementNode);
        } else if ((!elementNode.getLeft().isEmpty() && elementNode.getRight().isEmpty())
                || (elementNode.getLeft().isEmpty() && !elementNode.getRight().isEmpty())) {
            if (!elementNode.equals(this.root)) {
                BSTNode<T> child;
                if (!elementNode.getLeft().isEmpty()) {
                    child = (BSTNode<T>) elementNode.getLeft();
                } else {
                    child = (BSTNode<T>) elementNode.getRight();
                }
                if (elementNode.getParent().getLeft().equals(elementNode)) {
                    elementNode.getParent().setLeft(child);
                } else {
                    elementNode.getParent().setRight(child);
                }
                child.setParent(elementNode.getParent());
                this.rebalanceUp(child);
            } else {
                if (!elementNode.getLeft().isEmpty()) {
                    elementNode.getLeft().setParent(null);
                    this.root = (BSTNode<T>) elementNode.getLeft();
                } else {
                    elementNode.getRight().setParent(null);
                    this.root = (BSTNode<T>) elementNode.getRight();
                }
            }
        } else {
            T replacement;
            BSTNode<T> successorNode = sucessor(elementNode.getData());
            if (successorNode != null) {
                replacement = successorNode.getData();
            } else {
                replacement = predecessor(elementNode.getData()).getData();
            }
            remove(replacement);
            elementNode.setData(replacement);
            this.rebalance(elementNode);
        }
    }
}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int answer = 0;
		if (!node.isEmpty()) {
			answer = this.height((BSTNode<T>) node.getLeft()) - this.height((BSTNode<T>) node.getRight());
		}
		return answer;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			int balance = calculateBalance(node);
			BSTNode<T> left = (BSTNode<T>) node.getLeft();
			BSTNode<T> right = (BSTNode<T>) node.getRight();
			BSTNode<T> newRoot = null;
			if (balance > 1) {
				int leftBalance = calculateBalance(left);
				if (leftBalance >= 0) {
					newRoot = Util.rightRotation(node);
				} else {
					Util.leftRotation(left);
					newRoot = Util.rightRotation(node);
				}
			} else if (balance < -1) {
				int rightBalance = calculateBalance(right);
				if (rightBalance <= 0) {
					newRoot = Util.leftRotation(node);
				} else {
					Util.rightRotation(right);
					newRoot = Util.leftRotation(node);
				}
			}
			if (node == this.root && newRoot != null) {
				this.root = newRoot;
			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		if (parent != null && !parent.isEmpty()) {
			rebalance((BSTNode<T>) parent);
			rebalanceUp((BSTNode<T>) parent);
		}
   	}

}
