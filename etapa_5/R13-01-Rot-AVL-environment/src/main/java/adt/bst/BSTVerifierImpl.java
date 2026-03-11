package adt.bst;

/**
 * 
 * Performs consistency validations within a BST instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class BSTVerifierImpl<T extends Comparable<T>> implements BSTVerifier<T> {
	
	private BSTImpl<T> bst;

	public BSTVerifierImpl(BST<T> bst) {
		this.bst = (BSTImpl<T>) bst;
	}
	
	private BSTImpl<T> getBSt() {
		return bst;
	}

	@Override
	public boolean isBST() {
    	return isBST(this.bst.getRoot(), null, null);
	}

	private boolean isBST(BSTNode<T> node, T min, T max) {
    	boolean result = true;
    	if (node != null && !node.isEmpty()) {
        	T data = node.getData();
        	if ((min != null && data.compareTo(min) <= 0) ||
            	(max != null && data.compareTo(max) >= 0)) {
            	result = false;
        	} else {
            	result = isBST((BSTNode<T>) node.getLeft(), min, data) &&
                     	isBST((BSTNode<T>) node.getRight(), data, max);
        	}
    	}
    	return result;
	}
}
