package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return - noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> newNode = node;

		if (node != null && !node.isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> pivot = (BSTNode<T>) node.getRight();

			node.setRight(pivot.getLeft());
			if (!pivot.getLeft().isEmpty()) {
				pivot.getLeft().setParent(node);
			}

			pivot.setLeft(node);
			pivot.setParent(node.getParent());
			node.setParent(pivot);

			if (pivot.getParent() != null) {
				if (pivot.getParent().getLeft().equals(node)) {
					pivot.getParent().setLeft(pivot);
				} else {
					pivot.getParent().setRight(pivot);
				}
			}
			newNode = pivot;
		}

		return newNode;

	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> newNode = node;

		if (node != null && !node.isEmpty() && !node.getLeft().isEmpty()) {
			BSTNode<T> pivot = (BSTNode<T>) node.getLeft();

			node.setLeft(pivot.getRight());
			if (!pivot.getRight().isEmpty()) {
				pivot.getRight().setParent(node);
			}

			pivot.setRight(node);
			pivot.setParent(node.getParent());
			node.setParent(pivot);

			if (pivot.getParent() != null) {
				if (pivot.getParent().getLeft().equals(node)) {
					pivot.getParent().setLeft(pivot);
				} else {
					pivot.getParent().setRight(pivot);
				}
			}
			newNode = pivot;
		}

		return newNode;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
