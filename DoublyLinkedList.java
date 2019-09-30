/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Gabrielle Germanson
 * @version 1.0
 */

import java.util.NoSuchElementException;

public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("You entered an invalid "
                    + "index, please try again.");
        }
        if (data == null) {
            throw new IllegalArgumentException("You can't enter data that is "
                    + "null.");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack((data));
        } else if (index >= (size / 2)) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = (size - 1); i > index; i--) {
                curr = curr.getPrevious();
            }
            DoublyLinkedListNode<T> adding = new DoublyLinkedListNode<>(data,
                    curr.getPrevious(), curr);
            curr.getPrevious().setNext(adding);
            curr.setPrevious(adding);
            size++;
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            DoublyLinkedListNode<T> adding = new DoublyLinkedListNode<>(data,
                    curr.getPrevious(), curr);
            curr.getPrevious().setNext(adding);
            curr.setPrevious(adding);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter data that is "
                    + "null.");
        }
        if (head == null) {
            head = new DoublyLinkedListNode<>(data, null, null);
            tail = head;
            size++;
        } else {
            DoublyLinkedListNode<T> adding = new DoublyLinkedListNode<>(data,
                    null, head);
            head.setPrevious(adding);
            head = adding;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter data that is "
                    + "null.");
        }
        if (tail == null) {
            addToFront(data);
        } else {
            DoublyLinkedListNode<T> adding = new DoublyLinkedListNode<>(data,
                    tail, null);
            tail.setNext(adding);
            tail = adding;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index == 0) {
            return removeFromFront();
        } else if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You entered an invalid "
                    + "index, please try again.");
        } else if (index == (size - 1)) {
            return removeFromBack();
        } else if (index >= (size / 2)) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = (size - 1); i > index; i--) {
                curr = curr.getPrevious();
            }
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
            return curr.getData();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
            return curr.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("This list is empty and can't be"
                    + " removed from.");
        } else if (size == 1) {
            DoublyLinkedListNode<T> temp6 = head;
            head = null;
            tail = null;
            size = 0;
            return temp6.getData();
        } else {
            DoublyLinkedListNode<T> temp = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("This list is empty and can't be"
                    + " removed from.");
        } else if (size == 1) {
            return removeFromFront();
        } else {
            DoublyLinkedListNode<T> temp1 = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp1.getData();
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You entered an invalid "
                    + "index, please try again.");
        }
        if (head == null) {
            return null;
        }
        if (index >= (size / 2)) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = (size - 1); i > index; i--) {
                curr = curr.getPrevious();
            }
            return curr.getData();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can't enter data that is "
                    + "null.");
        } else if (size == 0) {
            throw new NoSuchElementException("The data was not found in this "
                    + "list.");
        } else if (tail.getData().equals(data)) {
            if (size == 1) {
                DoublyLinkedListNode<T> temp4 = head;
                head = null;
                tail = null;
                size = 0;
                return temp4.getData();
            } else {
                DoublyLinkedListNode<T> temp2 = tail;
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
                size--;
                return temp2.getData();
            }
        } else {
            DoublyLinkedListNode<T> curr = tail.getPrevious();
            for (int i = (size - 1); i > 0; i--) {
                if (curr.getData().equals(data)) {
                    if (i == 1) {
                        DoublyLinkedListNode<T> temp5 = head;
                        head.getNext().setPrevious(null);
                        head = head.getNext();
                        size--;
                        return temp5.getData();
                    } else {
                        curr.getPrevious().setNext(curr.getNext());
                        curr.getNext().setPrevious(curr.getPrevious());
                        size--;
                        return curr.getData();
                    }
                }
                curr = curr.getPrevious();
            }
            throw new NoSuchElementException("The data was not found in this "
                    + "list.");
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            Object[] ans = new Object[0];
            return ans;
        } else {
            Object[] wanted = new Object[size];
            wanted[0] = head.getData();
            if (size == 1) {
                return wanted;
            }
            DoublyLinkedListNode<T> curr = head;
            for (int i = 1; i < size; i++) {
                wanted[i] = curr.getNext().getData();
                curr = curr.getNext();
            }
            return wanted;
        }
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
