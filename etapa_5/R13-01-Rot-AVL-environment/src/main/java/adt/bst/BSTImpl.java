package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	} 

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BSTNode<T> node) {
		int result = -1;
		if (!node.isEmpty()) {

			// Para saber a altura de forma eficiente, precisamos saber em que lado da BST está a folha de
			// maior nível. Por esse motivo, utilizamos Math.max, comparamos a sub-árvore da ESQUERDA com
			// a da DIREITA para descobrir em qual lado está o maior nível.

			result = 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T> node.getRight())));
		}
		return result
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> result = null;
		if (element != null) {
			result = search(this.root, element);
		}
		return result;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if (!node.isEmpty()) {
			if (!node.getData().equals(element) == 0) {
				if (element.compareTo(node.getData()) > 0) {
					search((BSTNode<T>) node.getRight(), element);
				} else {
					search((BSTNode<T>) node.getLeft(), element);
				}
			}
		}
		return node;
	}

	@Override
	public void insert(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(this.root);
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> result = null;
		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				result = this.maximum((BSTNode<T>) node.getRight());
			}
			else {
				result = node;
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(this.root);
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> result = null;
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				result = this.minimum((BSTNode<T>) node.getLeft());
			}
			else {
				result = node;
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = null;
		if (element != null) {

			// Nao precisa do elemento, e sim do nó, por isso é necessário buscar o nó na árvore
			// para poder buscar o sucessor

			result = sucessor(search(element));
		}
		return result;
	}

	// O sucessor ele geralmente será o nó que está à direita do nó x -> Porém
	// há a possibilidade desse nó possuir um nó a esquerda, ou seja
	// um nó menor que ele, logo o correto seria procurar o valor MíNIMO da sub-árvore. 
	// Entretanto, há a possibilidade do nó x não ter um filho a direita, quando isso acontece, 
	// quer dizer que o sucessor é um nó que está acima do nó x na hierarquia.
	// "Mas como eu vou saber qual nó é esse? Qual parente é?" Bem, vamos verificar os nós filho
	// desse parente. Se o nó x for o nó filho a direita do parente, então o nó x é maior que o seu
	// pai, dessa forma, seu pai não pode ser seu sucessor. Se o nó x estiver à esquerda do parente,
	// então ele é menor que seu pai, logo, seu pai é o seu sucessor (isso se o nó x não tiver um filho
	// a direita).

	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> result = null;

		// O que acontece se o nó filho a DIREITA, estiver vazio? A chance desse nó a DIREITA ser o 
		// sucessor existe, entretando, isso em um caso positivo que esse nó a DIREITA não possua um nó
		// a ESQUERDA. Por isso, é necessário que buscamos não o nó a direita, e sim o valor mínimo daquela
		// sub-árvore, sendo o nó a direita sua raíz.

		if (!node.getRight().isEmpty()) {

			// Código anterior: result = node.getRight();

			result = minimo(node.getRight());
		}

		// Se o nó a DIREITA estiver vazio, quer dizer que o sucessor vai estar nos nós que estão antes
		// do nó x. Para saber qual parente é esse, precisamos levar dois casos em consideração.
		// Caso 1 - O nó x é o nó a DIREITA do seu pai: se o nó x for o nó a direita do seu pai,
		// então quer dizer que o nó x é maior que seu pai, não podendo ser ele seu sucessor, logo
		// subimos a comparação para o AVÔ.
		// Caso 2 - O nó x é o nó a ESQUERDA do seu pai: Se isso acontecer, e o nó x não possuir um filho
		// a direita, então quer dizer que o nó sucessor de x, é o seu próprio PAI.

		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			// "Enquanto eu não chegar no topo da árvore (pai não é nulo) 
			// E o nó atual for o filho à direita do seu pai, continue subindo."

			while (!parent != null && parent.getRight().equals(node)) {
				node = parent;
				parent = (BSTNode<T>) parent.getParent();
			}

			// Se o pai chegar a ser nulo (chegamos na raíz), quer dizer que chegamos no
			// maior valor da árvore, e como o maior valor não tem sucessor, então o valor será nulo.

			result = parent;
		}
		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = null;
		if (element != null) {
			result = predecessor(search(element));
		}
		return result;
	}

	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> result = null;
		if (!node.getLeft().isEmpty()) {
			result = maximo(node.getLeft());
		}
		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			while (!parent != null && parent.getLeft().equals(node)) {
				node = parent;
				parent = (BSTNode<T>) parent.getParent();
			}
			result = parent;
		}
		return result;
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
