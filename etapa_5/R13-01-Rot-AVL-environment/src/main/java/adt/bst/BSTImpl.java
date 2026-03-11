package adt.bst;


import java.util.LinkedList;


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


    public int height(BSTNode<T> node) {
        int height = -1;
        if (!node.isEmpty()) {
            height = 1 + Math.max(height((BSTNode<T>) node.getLeft()), height((BSTNode<T>) node.getRight()));
        }
        return height;
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
        BSTNode<T> result = node;
        if (!node.isEmpty()) {
            if (!node.getData().equals(element)) {
                if (element.compareTo(node.getData()) < 0) {
                    result = search((BSTNode<T>) node.getLeft(), element);
                } else if (element.compareTo(node.getData()) > 0) {
                    result = search((BSTNode<T>) node.getRight(), element);
                }
            }
        }
        return result;
    }


   @Override
    public void insert(T element) {
        if (element != null) {
            insert(this.root, element);
        }
    }

    private void insert(BSTNode<T> node, T element) {
        if (node.isEmpty()) {
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
        }
    }


   @Override
    public BSTNode<T> maximum() {
        BSTNode<T> max = null;
        if (!this.isEmpty()) {
            max = this.maximum(this.root);
        }
        return max;
    }

    private BSTNode<T> maximum(BSTNode<T> node) {
        BSTNode<T> result = node;
        if (!node.getRight().isEmpty()) {
            result = maximum((BSTNode<T>) node.getRight());
        }
        return result;
    }

    @Override
    public BSTNode<T> minimum() {
        BSTNode<T> min = null;
        if (!this.isEmpty()) {
            min = this.minimum(this.root);
        }
        return min;
    }

    private BSTNode<T> minimum(BSTNode<T> node) {
        BSTNode<T> result = node;
        if (!node.getLeft().isEmpty()) {
            result = minimum((BSTNode<T>) node.getLeft());
        }
        return result;
    }

    @Override
    public BSTNode<T> sucessor(T element) {
        BSTNode<T> elementNode = search(element);
        BSTNode<T> sucessor = null;
        if (element != null) {
            sucessor = sucessor(elementNode);
        }
        return sucessor;
    }


    private BSTNode<T> sucessor(BSTNode<T> node) {
        BSTNode<T> result = null;
        if (!node.isEmpty()) {
            if (!node.getRight().isEmpty()) {
                result = minimum((BSTNode<T>) node.getRight());
            } else {
                BSTNode<T> parent = (BSTNode<T>) node.getParent();
                while (parent != null && !parent.isEmpty() && parent.getRight().equals(node)) {
                    node = parent;
                    parent = (BSTNode<T>) node.getParent();
                }
                result = parent;
            }
        }
        return result;
    }


    @Override
    public BSTNode<T> predecessor(T element) {
        BSTNode<T> node = search(element);
        BSTNode<T> predecessor = null;
        if (element != null) {
            predecessor = predecessor(node);
        }
        return predecessor;
    }

    private BSTNode<T> predecessor(BSTNode<T> node) {
        BSTNode<T> result = null;
        if (!node.getLeft().isEmpty()) {
            result = (BSTNode<T>) node.getLeft();
            while (!result.getRight().isEmpty()) {
                result = (BSTNode<T>) result.getRight();
            }
        } else {
            BSTNode<T> parent = (BSTNode<T>) node.getParent();
            while (parent != null && !parent.isEmpty() && parent.getLeft().equals(node)) {
                node = parent;
                parent = (BSTNode<T>) node.getParent();
            }
            result = parent;
        }
        return result;
    }

    @Override
    public void remove(T element) {
        BSTNode<T> node = search(element);
        if (!node.isEmpty()) {
            if (node.isLeaf()) {
                node.setData(null);
                node.setLeft(null);
                node.setRight(null);
            } else if ((!node.getLeft().isEmpty() && node.getRight().isEmpty()) ||
                    (node.getLeft().isEmpty() && !node.getRight().isEmpty())) {
                if (!node.equals(this.root)) {
                    if (node.getParent().getLeft().equals(node)) {
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
                } else {
                    if (!node.getLeft().isEmpty()) {
                        node.getLeft().setParent(null);
                        this.root = (BSTNode<T>) node.getLeft();
                    } else {
                        node.getRight().setParent(null);
                        this.root = (BSTNode<T>) node.getRight();
                    }
                }
            } else {
                BSTNode<T> sucessor = sucessor(node);
                node.setData(sucessor.getData());
                remove(sucessor.getData());
            }
        }
    }


    @Override
    public T[] preOrder() {
        LinkedList<T> list = new LinkedList<>();
        preOrder(this.root, list);
        return (T[]) list.toArray(new Comparable[this.size()]);
    }

    private void preOrder(BSTNode<T> node, LinkedList<T> list) {
        if (!node.isEmpty()) {
            list.add(node.getData());
            preOrder((BSTNode<T>) node.getLeft(), list);
            preOrder((BSTNode<T>) node.getRight(), list);
        }
    }


    @Override
    public T[] order() {
        LinkedList<T> list = new LinkedList<>();
        order(this.root, list);
        return (T[]) list.toArray(new Comparable[this.size()]);
    }

    private void order(BSTNode<T> node, LinkedList<T> list) {
        if (!node.isEmpty()) {
            order((BSTNode<T>) node.getLeft(), list);
            list.add(node.getData());
            order((BSTNode<T>) node.getRight(), list);
        }
    }

    @Override
    public T[] postOrder() {
        LinkedList<T> list = new LinkedList<>();
        postOrder(this.root, list);
        return (T[]) list.toArray(new Comparable[this.size()]);
    }

    private void postOrder(BSTNode<T> node, LinkedList<T> list) {
        if (!node.isEmpty()) {
            postOrder((BSTNode<T>) node.getLeft(), list);
            postOrder((BSTNode<T>) node.getRight(), list);
            list.add(node.getData());
        }
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