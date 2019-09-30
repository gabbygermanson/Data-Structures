import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Your implementation of a BST.
 *
 * @author GABRIELLE GERMANSON
 * @version 1.0
 */


public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null.");
        } else {
            for (T info: data) {
                if (info == null) {
                    throw new IllegalArgumentException("No element in data "
                            + "can be null.");
                } else {
                    add(info);
                }
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null.");
        } else {
            root = addHelper(data, root);
        }
    }

    /**
     * Helper method for add method
     *
     * @param data the data to add
     * @param curr present node working with
     * @return root that may have been changed
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
            return curr;
        }
        return curr; //QUESTION
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null.");
        } else {
            BSTNode<T> dummy = new BSTNode<>(null);
            root = removeHelper(root, dummy, data);
            size--;
            return dummy.getData();
        }
    }

    /**
     * Helper method for remove method
     *
     * @param curr present node working with
     * @param dummy helper node to return data from a removed node
     * @param data the data looking for to remove it's node, if it exists
     * @return the root that may be altered
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, BSTNode<T> dummy, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        } else if (curr.getData().equals(data)) {
            if (curr.getLeft() == null && curr.getRight() == null) {
                dummy.setData(curr.getData());
                return null;
            } else if (curr.getLeft() == null || curr.getRight() == null) {
                if (curr.getLeft() != null) {
                    dummy.setData(curr.getData());
                    return curr.getLeft();
                } else if (curr.getRight() != null) {
                    dummy.setData(curr.getData());
                    return curr.getRight();
                }
            } else if (curr.getLeft() != null && curr.getRight() != null) {
                T dummy2 = findPred(curr, curr.getLeft());
                dummy.setData(curr.getData());
                curr.setData(dummy2);
            }
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), dummy, data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), dummy, data));
        }
        return curr; //QUESTION
    }

    /**
     * Helper method for remove method to find predecessor
     *
     * @param parent parent node
     * @param curr node to work with
     * @return the predecessor node
     */
    private T findPred(BSTNode<T> parent, BSTNode<T> curr) {
        if (curr.getRight() == null) {
            T temp = curr.getData();
            if (parent.getLeft() == curr) {
                if (curr.getLeft() != null) {
                    parent.setLeft(curr.getLeft());
                } else {
                    parent.setLeft(null);
                }
            } else {
                if (curr.getLeft() != null) {
                    parent.setRight(curr.getLeft());
                } else {
                    parent.setRight(null);
                }
            }
            return temp;
        } else {
            return findPred(curr, curr.getRight());
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null.");
        } else {
            return getHelper(root, data);
        }
    }

    /**
     * Helper method for get method
     *
     * @param curr current node working with
     * @param data value to get
     * @return data to be get
     */
    private T getHelper(BSTNode<T> curr, T data) {
        while (curr != null) {
            if (curr.getData().equals(data)) {
                return curr.getData();
            } else if (data.compareTo(curr.getData()) > 0) {
                curr = curr.getRight();
            } else if (data.compareTo(curr.getData()) < 0) {
                curr = curr.getLeft();
            }
        }
        if (curr == null) {
            throw new NoSuchElementException("Data was not found in tree.");
        }
        return null;
    } //QUESTION

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            return get(data).equals(data);
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    //    private boolean containsHelper(int data, Node node) {
    //        if (node == null) {
    //            return false;
    //        } else if (data < node.data) {
    //            return containsHelper(data, node.left);
    //        } else if (data > node.data) {
    //            return containsHelper(data, node.right);
    //        } else {
    //            return true;
    //        }
    //    }



    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> ans = new ArrayList<>();
        preorderHelper(root, ans);
        return ans;
    }

    /**
     * Helper method for preorder method
     *
     * @param curr current node working with
     * @param ans list to add values of traversal in
     */
    private void preorderHelper(BSTNode curr, List<T> ans) {
        if (curr != null) {
            ans.add((T) curr.getData());
            preorderHelper(curr.getLeft(), ans);
            preorderHelper(curr.getRight(), ans);
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> ans = new ArrayList<>();
        inorderHelper(root, ans);
        return ans;
    }

    /**
     * Helper method for inorder method
     *
     * @param curr current node working with
     * @param ans list to add values of traversal in
     */
    private void inorderHelper(BSTNode curr, List<T> ans) {
        if (curr != null) {
            inorderHelper(curr.getLeft(), ans);
            ans.add((T) curr.getData());
            inorderHelper(curr.getRight(), ans);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> ans = new ArrayList<>();
        postorderHelper(root, ans);
        return ans;
    }

    /**
     * Helper method for postorder method
     *
     * @param curr current node working with
     * @param ans list to add values of traversal in
     */
    private void postorderHelper(BSTNode curr, List<T> ans) {
        if (curr != null) {
            postorderHelper(curr.getLeft(), ans);
            postorderHelper(curr.getRight(), ans);
            ans.add((T) curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<BSTNode<T>> work = new LinkedList<>();
        List<T> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        work.add(root);
        while (work.size() != 0) {
            BSTNode<T> temp = work.getFirst();
            ans.add(temp.getData());
            if (temp.getLeft() != null) {
                work.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                work.add(temp.getRight());
            }
            work.removeFirst();
        }
        return ans;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return heightHelper(root);
        }

    }

    /**
     * Helper method for height method
     *
     * @param curr current node working with
     * @return height of root
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr.getLeft() != null && curr.getRight() != null) {
            return Math.max(heightHelper(curr.getLeft()),
                    heightHelper(curr.getRight())) + 1;
        } else if (curr.getLeft() != null && curr.getRight() == null) {
            return heightHelper(curr.getLeft()) + 1;
        } else if (curr.getRight() != null && curr.getLeft() == null) {
            return heightHelper(curr.getRight()) + 1;
        } else {
            return 0;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Elements to find path between"
                    + " can not be null.");
        } else if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException("One or both elements to find "
                    + "the path between are not in the tree.");
        } else {
            LinkedList<T> myList = new LinkedList<T>();
            if (data1.equals(data2)) {
                myList.add(data1);
                return myList;
            } else {
                BSTNode<T> ca = findCA(root, data1, data2);

                beforeCA(myList, ca, data1);

                afterCA(myList, ca, data2);
                return (List<T>) myList;
            }
        }
    }

    /**
     * Helper method for findPathBetween method that adds everything less
     * than common ancestor to the list
     *
     * @param list data structure to add path to
     * @param curr current node working with
     * @param data1 start point data of path
     */
    private void beforeCA(LinkedList<T> list, BSTNode<T> curr,
                                   T data1) {
        if (curr.getData().equals(data1)) {
            if (!(list.contains(data1))) {
                list.addFirst(data1);
            }
        } else if (curr.getData().compareTo(data1) < 0) {
            if (!(list.contains(curr.getData()))) {
                list.addFirst(curr.getData());
            }
            beforeCA(list, curr.getRight(), data1);
        } else if (curr.getData().compareTo(data1) > 0) {
            if (!(list.contains(curr.getData()))) {
                list.addFirst(curr.getData());
            }
            beforeCA(list, curr.getLeft(), data1);
        }
    }

    /**
     * Helper method for findPathBetween method that adds everything greater
     * than common ancestor to the list
     *
     * @param list data structure to add path to
     * @param curr current node working with
     * @param data2 end point data of path
     */
    private void afterCA(LinkedList<T> list, BSTNode<T> curr,
        T data2) {
        if (curr.getData().equals(data2)) {
            if (!(list.contains(data2))) {
                list.addLast(data2);
            }
        } else if (curr.getData().compareTo(data2) < 0) {
            if (!(list.contains(curr.getData()))) {
                list.addLast(curr.getData());
            }
            afterCA(list, curr.getRight(), data2);
        } else if (curr.getData().compareTo(data2) > 0) {
            if (!(list.contains(curr.getData()))) {
                list.addLast(curr.getData());
            }
            afterCA(list, curr.getLeft(), data2);
        }
    }


    /**
     * Method that finds least common ancestor between two elements for
     * findPathBetween method
     *
     * @param curr current node working with
     * @param dataOne first element data of path
     * @param dataTwo second element data of path
     * @return least common ancestor
     */
    private BSTNode<T> findCA(BSTNode<T> curr, T dataOne, T dataTwo) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(dataOne) > 0
                && curr.getData().compareTo(dataTwo) > 0) {
            return findCA(curr.getLeft(), dataOne, dataTwo);
        } else if (curr.getData().compareTo(dataOne) < 0
                && curr.getData().compareTo(dataTwo) < 0) {
            return findCA(curr.getRight(), dataOne, dataTwo);
        }
        return curr;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
