import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author GABRIELLE GERMANSON
 * @version 1.0
 */

/**
 * No collaboration or outside resources used.
 */

public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        } else {
            backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
            int index = 1;
            for (T info: data) {
                if (info == null) {
                    throw new IllegalArgumentException("No element in data "
                            + "can be null.");
                } else {
                    backingArray[index] = info;
                    index++;
                    size++;
                }
            }
            for (int i = size / 2; i > 0; i--) {
                downheap(i);
            }
        }
    }

    /**
     * Helper method to down switch elements in a heap
     *
     * @param i index of parent working with
     */
    private void downheap(int i) {
        //base case if parent is lesser than both children, do nothing
        //        doesn't need to be coded
        T min;
        T currParent = backingArray[i];
        if (backingArray[i * 2 + 1] == null) {
            if (backingArray[i * 2].compareTo(currParent) < 0) {
                min = backingArray[i * 2];
                backingArray[i] = min;
                backingArray[i * 2] = currParent;
            }
        } else if (backingArray[i * 2].compareTo(currParent) < 0
                && backingArray[i * 2].compareTo(backingArray[i * 2 + 1]) < 0) {

            min = backingArray[i * 2];
            backingArray[i] = min;
            backingArray[i * 2] = currParent;
            if ((i * 2) <= size / 2) {
                downheap(i * 2);
            }

        } else if (backingArray[i * 2 + 1].compareTo(currParent) < 0
                && backingArray[i * 2 + 1].compareTo(backingArray[i * 2]) < 0) {

            min = backingArray[i * 2 + 1];
            backingArray[i] = min;
            backingArray[i * 2 + 1] = currParent;
            if ((i * 2 + 1) <= size / 2) {
                downheap(i * 2 + 1);
            }
        }
    }

    /**
     * Helper method to up switch elements in a heap
     *
     * @param i index of parent working with
     */
    private void upheap(int i) {
        T child = backingArray[i];
        T parent = backingArray[i / 2];
        if (child.compareTo(parent) < 0) {
            backingArray[i / 2] = child;
            backingArray[i] = parent;
            if ((i / 2) > 1) {
                upheap(i / 2);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        } else {
            if (size + 1 == backingArray.length) {
                T[] newArray = (T[]) new Comparable[2 * backingArray.length];
                if (size != 0) {
                    for (int i = 1; i <= size; i++) {
                        newArray[i] = backingArray[i];
                    }
                }
                backingArray = newArray;
            }
            backingArray[size + 1] = data;
            size++;
            if ((size) != 1) {
                upheap(size);
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty and can't get.");
        } else {
            T ans;
            if (size == 1) {
                ans = backingArray[1];
                backingArray[1] = null;
                size--;
            } else {
                ans = backingArray[1];
                T tempRoot = backingArray[size];
                backingArray[size] = null;
                backingArray[1] = tempRoot;
                size--;
                if (size != 1) {
                    downheap(1);
                }
            }
            return ans;
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty and can't get.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (backingArray.length == 1) {
            return true;
        } else {
            return backingArray[1] == null;
        }
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
