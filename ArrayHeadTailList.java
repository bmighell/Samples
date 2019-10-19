/**
 * @author Bryce Mighell
 */

import java.util.Arrays;

public class ArrayHeadTailList<T> implements HeadTailListInterface<T> {

    private T[] listArray;
    private int numberOfElements;

    public ArrayHeadTailList(int capacity) {

        if (capacity >= 0) {
            @SuppressWarnings("unchecked")
            T[] tempArray = (T[])new Object[capacity];
            listArray = tempArray;
            numberOfElements = 0;
        } else {
            throw new NegativeArraySizeException("Attempt to create an array " +
                    "with a capacity below 0.");
        }
    }

    /**
     * Adds a new entry to the beginning of the list.
     * Entries currently in the list are shifted down.
     * The list size is increased by 1.
     *
     * @param newEntry The object to be added as a new entry.
     */
    public void addFront(T newEntry) {

            addElement(1, newEntry, 0);
    }

    /**
     * Adds a new entry to the end of the list.
     * Entries currently in the list are unaffected.
     * The list size is increased by 1.
     *
     * @param newEntry The object to be added as a new entry.
     */
    public void addBack(T newEntry) {

        addElement(0, newEntry, numberOfElements);
    }



    /**
     * Removes an entry from the beginning of the list.
     * Entries currently in the list are shifted up.
     * The list size is decreased by 1.
     *
     * @return A reference to the removed entry or null if the list is empty.
     */
    public T removeFront() {

        return !isEmpty() ?
                removeElement(1, 0, 0) :
                null;
    }

    /**
     * Removes an entry from the end of the list.
     * Entries currently in the list are unaffected.
     * The list size is decreased by 1.
     *
     * @return A reference to the removed entry or null if the list is empty.
     */
    public T removeBack() {

        return !isEmpty() ?
                removeElement(0, -1, numberOfElements - 1) :
                null;
    }


    /** Removes all entries from this list. */
    public void clear() {

        Arrays.fill(listArray, null);

        numberOfElements = 0;
    }


    /**
     * Retrieves the entry at a given position in this list.
     *
     * @param givenPosition An integer that indicates the position of the desired entry.
     * @return A reference to the indicated entry or null if the index is out of bounds.
     */
    public T getEntry(int givenPosition) {
        return (givenPosition >= 0 && givenPosition < numberOfElements) ?
                listArray[givenPosition] :
                null;
    }

    /**
     * Displays the contents of the list to the console, in order.
     */
    public void display() {

        System.out.print(numberOfElements + " elements; capacity = " +
                listArray.length + "\t");

        if (!isEmpty()) {

            System.out.print("[");

            for (int i = 0; i < numberOfElements - 1; i++) {
                System.out.print(listArray[i] + ", ");
            }
            System.out.println(listArray[numberOfElements - 1] + "]");
        }
    }

    /**
     * Determines the position in the list of a given entry.
     * If the entry appears more than once, the first index is returned.
     *
     * @param anEntry the object to search for in the list.
     * @return the first position the entry that was found or -1 if the object is not found.
     */
    public int indexOf(T anEntry) {
        return getIndex(anEntry, 0, numberOfElements, 1);
    }

    /**
     * Determines the position in the list of a given entry.
     * If the entry appears more than once, the last index is returned.
     *
     * @param anEntry the object to search for in the list.
     * @return the last position the entry that was found or -1 if the object is not found.
     */
    public int lastIndexOf(T anEntry) {
        return getIndex( anEntry, numberOfElements - 1, -1, -1);
    }

    /**
     * Determines whether an entry is in the list.
     *
     * @param anEntry the object to search for in the list.
     * @return true if the list contains the entry, false otherwise
     */
    public boolean contains(T anEntry) {
        return indexOf(anEntry) != -1;
    }


    /**
     * Gets the length of this list.
     *
     * @return The integer number of entries currently in the list.
     */
    public int size() {
        return numberOfElements;
    }

    /**
     * Checks whether this list is empty.
     *
     * @return True if the list is empty, or false if the list contains one or more elements.
     */
    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    private boolean isFull() {
        return numberOfElements == listArray.length;
    }

    private void expandArray() {
        listArray = Arrays.copyOf(listArray, listArray.length * 2);
    }

    private void changeArray(int sourceStart, int destStart, int lengthMod, T newEntry, int whereToAdd) {

        System.arraycopy(listArray, sourceStart, listArray, destStart, numberOfElements + lengthMod);

        if (whereToAdd != -1) {
            listArray[whereToAdd] = newEntry;
        }
    }

    private T removeElement(int sourceStart, int lengthMod, int removedEntryIndex) {
        T removedEntry = listArray[removedEntryIndex];

        changeArray(sourceStart, 0, lengthMod, null, -1);

        numberOfElements--;

        return removedEntry;
    }

    private void addElement(int destStart, T newEntry, int whereToAdd) {

        if (isFull()) {
            expandArray();
        }

        changeArray(0, destStart, 0, newEntry, whereToAdd);

        numberOfElements++;
    }

    private int getIndex(T anEntry, int start, int stop, int step) {
        for (int i = start; i != stop; i+= step) {
            if (anEntry.equals(listArray[i])) {
                return i;
            }
        }

        return -1;
    }
}
