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


   private int height(BSTNode<T> node) {
       int height = -1;
       if (!node.isEmpty()) {
           height = 1 + Math.max(height((BSTNode<T>)node.getLeft()), height((BSTNode<T>)node.getRight()));
       }
       return height;
   }


   @Override
   public BSTNode<T> search(T element) {
       return search(this.root, element);
   }
    private BSTNode<T> search(BSTNode<T> node, T element) {
       BSTNode<T> result = new BSTNode<>();
       if (node.isEmpty() || element.equals(node.getData())) {
           result = node;
       }
       else if (element.compareTo(node.getData()) < 0) {
           result = search((BSTNode<T>)node.getLeft(), element);
       }
       else {
           result = search((BSTNode<T>)node.getRight(), element);
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
       }
       else {
           if (element.compareTo(node.getData()) < 0) {
               insert((BSTNode<T>) node.getLeft(), element);
           }
           else if (element.compareTo(node.getData()) > 0) {
               insert((BSTNode<T>) node.getRight(), element);
           }
       }
   }


   @Override
   public BSTNode<T> maximum() {
       return maximum(this.root);
   }
    private BSTNode<T> maximum(BSTNode<T> node) {
       BSTNode<T> result = null;
       if (!node.isEmpty()) {
           BSTNode<T> nextMax = this.maximum((BSTNode<T>)node.getRight());
           if (nextMax != null && !nextMax.isEmpty()) {
               result = nextMax;
           }
           else {
               result = node;
           }
       }
       return result;
   }


   @Override
   public BSTNode<T> minimum() {
       return this.minimum(this.root);
   }
    private BSTNode<T> minimum(BSTNode<T> no) {
       BSTNode<T> result = null;
       if (!no.isEmpty()) {
           BSTNode<T> nextMin = this.minimum((BSTNode<T>)no.getLeft());
           if (nextMin != null && !nextMin.isEmpty()) {
               result = nextMin;
           } else {
               result = no;
           }
       }
       return result;
   }


   @Override
   public BSTNode<T> sucessor(T element) {
       BSTNode<T> result = null;
       BSTNode<T> node = search(element);
       if (!node.isEmpty()) {
           result = sucessor(node);
       }
       return result;
      
   }
   private BSTNode<T> sucessor(BSTNode<T> node) {
       BSTNode<T> result = null;
       if (!node.getRight().isEmpty()) {
           result = minimum((BSTNode<T>)node.getRight());
       } else {
           BSTNode<T> parent = (BSTNode<T>)node.getParent();
           while (parent != null && node == parent.getRight()) {
               node = parent;
               parent = (BSTNode<T>)parent.getParent();
           }
           result = parent;
       }
       return result;
   }


   @Override  
   public BSTNode<T> predecessor(T element) {
       BSTNode<T> result = null;
       BSTNode<T> node = search(element);
       if (!node.isEmpty()) {
           result = predecessor(node);
       }
       return result;
   }
   private BSTNode<T> predecessor(BSTNode<T> node) {
       BSTNode<T> result = null;
       if (!node.getLeft().isEmpty()) {
           result = this.maximum((BSTNode<T>)node.getLeft());
       } else {
           BSTNode<T> pai = (BSTNode<T>)node.getParent();
           while (pai != null && node == pai.getLeft()) {
               node = pai;
               pai = (BSTNode<T>)pai.getParent();
           }
           result = pai;
       }
       return result;
   }


   @Override
   public void remove(T element) {
       BSTNode<T> node = this.search(element);
       if (!node.isEmpty()) {
           if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
               node.setData(null);
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
               } else {
                   if (!node.getLeft().isEmpty()) {
                       this.root = (BSTNode<T>)node.getLeft();
                       this.root.setParent(null);
                   } else {
                       this.root = (BSTNode<T>)node.getRight();
                       this.root.setParent(null);
                   }
               }
           } else {
               BSTNode<T> sucessor = this.sucessor(node);
               T sucessorData = sucessor.getData();
               remove(sucessor.getData());
               node.setData(sucessorData);
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


   private void order (BSTNode<T> node, LinkedList<T> list) {
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
           order((BSTNode<T>) node.getLeft(), list);
           order((BSTNode<T>) node.getRight(), list);
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
