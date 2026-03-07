package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

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
        	insert(this.root, element);
       }
   	}

   	private void insert(BSTNode<T> node, T element) {
       	if (node.isEmpty() &&) {
           	node.setData(element);
           	BSTNode<T> left = new BSTNode<>();
           	node.setLeft(left);
           	BSTNode<T> right = new BSTNode<>();
           	node.setRight(right);
           	left.setParent(node);
           	right.setParent(node);
       	} else {
           	if (element.compareTo(node.getData()) < 0) {
               	insert((BSTNode<T>) node.getLeft(), element);
           	} else if (element.compareTo(node.getData()) > 0) {
               	insert((BSTNode<T>) node.getRight(), element);
           	}
           	this.rebalance(node);
       	}
   	}

	@Override
   	public void remove(T element) {
       	BSTNode<T> node = this.search(element);
       	if (!node.isEmpty() && element != null) {
           	if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
               	node.setData(null);
				node.setLeft(null);
				node.setRight(null);
               	this.rebalanceUp(node);
           	} else if ((!node.getLeft().isEmpty() && node.getRight().isEmpty()) || (node.getLeft().isEmpty() && !node.getRight().isEmpty())) {
               	if (!node.getParent().isEmpty()) {
                   	if (node == node.getParent().getLeft()) {
                       	if (!node.getLeft().isEmpty()) {
                           	node.getParent().setLeft(node.getLeft());
                           	node.getLeft().setParent(node.getParent());
                       	} else {
                           	node.getParent().setLeft(node.getRight());
                           	node.getRight().setParent(node.getParent());
                       	}
                   	} else {
                       	if (!node.getLeft().isEmpty()) {
                           	node.getParent().setRight(node.getLeft());
                           	node.getLeft().setParent(node.getParent());
                       	} else {
                           	node.getParent().setRight(node.getRight());
                           	node.getRight().setParent(node.getParent());
                       	}
                   	}
					this.rebalanceUp(node);
               	} else {
                   	if (!node.getLeft().isEmpty()) {
						this.root.getLeft().setParent(null);
                       	this.root = (BSTNode<T>) node.getLeft();
                   	} else {
						this.root.getRight().setParent(null);
                       	this.root = (BSTNode<T>) node.getRight();
                   	}
               	}


           	} else {
               	BSTNode<T> sucessor = this.sucessor(node.getData());
               	T sucessorData = sucessor.getData();
               	remove(sucessor.getData());
               	node.setData(sucessorData);
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
		if (!node != null || !node.isEmpty()) {
			int balance = calculateBalance(node);
       		if (balance > 1) {
           		int leftBalance = calculateBalance((BSTNode<T>) node.getLeft());
           		if (leftBalance >= 0) {
               		rotateRight(node);
           		} else {
               		rotateLeft((BSTNode<T>) node.getLeft());
               		rotateRight(node);
           		}
       		} else if (balance < -1) {
           		int rightBalance = calculateBalance((BSTNode<T>) node.getRight());
           		if (rightBalance <= 0) {
               		rotateLeft(node);
           		} else {
               		rotateRight((BSTNode<T>) node.getRight());
               		rotateLeft(node);
           		}
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
