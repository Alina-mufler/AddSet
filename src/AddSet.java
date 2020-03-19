import java.util.*;

/**
 * Class copies functionality of NavigableSet.
 *
 * @version 2.5
 * @author Alina Fedorova
 */

public class AddSet<T> extends AbstractSet<T> implements NavigableSet<T> {
    /**
     * T - the type of elements maintained by this set.
     */
    private ArrayList<T> array;
    private Comparator<T> comparator;

    /**
     * Constructor - creating a new object with specific values
     *
     * @param array - ready list of users
     * @param comparator - comparator.
     */

    public AddSet(ArrayList<T> array, Comparator<T> comparator) {
        this.array = array;
        this.comparator = comparator;
    }


    /**
     * Constructor - creating a new object with new values.
     * @param comparator - comparator.
     */

    public AddSet(Comparator<T> comparator) {
        array = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * The function adds a new number to the end of the array list
     *
     * @param t specific element
     */

    public boolean add(T t) {
        array.add(t);
        return true;
    }

    /**
     * Returns the greatest element in this set strictly less than the given element, or null if there is no such element.
     *
     * @return the greatest element less than t, or null if there is no such element
     */

    public T lower(T t) {
        if (comparator.compare(array.get(0), t) < 0) {
            int m = array.indexOf(t);
            if (m != -1) return array.get(m - 1);
            else {
                for (int i = 0; i < array.size(); i++) {
                    if (comparator.compare(array.get(i), t) > 0) return array.get(i - 1);
                }
            }
        }
        return null;
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     *
     * @return the greatest element less than or equal to t, or null if there is no such element.
     */

    public T floor(T t) {
        if (comparator.compare(array.get(0), t) <= 0) {
            int m = array.indexOf(t);
            if (m == 0) return array.get(m);
            else return lower(t);
        }
        return null;
    }

    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     *
     * @return the least element greater than or equal to t, or null if there is no such element.
     */

    public T ceiling(T t) {
        if (comparator.compare(array.get(array.size() - 1), t) >= 0) {
            int m = array.indexOf(t);
            if (m == array.size() - 1) return array.get(m);
            else return higher(t);
        }
        return null;
    }

    /**
     * Returns the least element in this set strictly greater than the given element, or null if there is no such element.
     *
     * @return the least element greater than t, or null if there is no such element.
     */

    public T higher(T t) {
        if (comparator.compare(array.get(array.size() - 1), t) > 0) {
            int m = array.indexOf(t);
            if (m == -1) {
                for (int i = 0; i < array.size(); i++) {
                    if (comparator.compare(array.get(i), t) > 0) return array.get(i);
                }
            } else return array.get(m + 1);
        }
        return null;
    }

    /**
     * Retrieves and removes the first (lowest) element, or returns null if this set is empty.
     * @return the first element, or null if this set is empty.
     */

    public T pollFirst() {
        if (!isEmpty()) {
            T t = array.get(0);
            array.remove(array.get(0));
            return t;
        }
        return null;
    }

    /**
     * Retrieves and removes the last (highest) element, or returns null if this set is empty.
     *
     * @return the last element, or null if this set is empty.
     */

    public T pollLast() {
        if (!isEmpty()) {
            T t = array.get(array.size() - 1);
            array.remove(array.get(array.size() - 1));
            return t;
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    public Iterator<T> iterator() {
        return array.iterator();
    }

    /**
     * Returns a reverse order view of the elements contained in this set.
     *
     * @return reverse order of elements.
     */
    public NavigableSet<T> descendingSet() {
        NavigableSet<T> inverted = new AddSet<T>(comparator);
        for (int i = array.size() - 1; i > -1; i--) {
            inverted.add(array.get(i));
        }
        return inverted;
    }

    /**
     * Returns an iterator over the elements in this set, in descending order of type {@code T}.
     *
     * @return an reverse Iterator.
     */

    public Iterator<T> descendingIterator () {
        Iterator<T> iterator = new Iterator<T>() {
            int cursor = array.size() - 1;

            public boolean hasNext() {
                return cursor > -1;
            }

            public T next() {
                try {
                    if (array.get(cursor - 1) == null) {
                        while (array.get(cursor - 1) == null) {
                            cursor = cursor - 1;
                        }
                        cursor = cursor - 1;
                        return array.get(cursor - 1);
                    } else {
                        cursor = cursor - 1;
                        return array.get(cursor - 1);
                    }
                } catch (ArrayIndexOutOfBoundsException b) {
                    throw new NoSuchElementException();
                }
            }
        };
        return iterator;
    }

    /**
     * Returns a view of the portion of this set whose elements range from fromElement to toElement.
     * If fromInclusive or toInclusive equal true, then fromElement or toElement are included in the set,
     * else these elements don't included in the set.
     *
     * @return a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
     */

    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        NavigableSet<T> returned = new AddSet<T>(comparator);
        if (comparator.compare(fromElement, toElement) > 0) throw new IllegalArgumentException();

        if (fromInclusive && toInclusive) {
            for (T getEL : array) {
                if (comparator.compare(getEL, fromElement) >= 0 && comparator.compare(getEL, toElement) <= 0) {
                    returned.add(getEL);
                }
            }
        }
        else {
            for (T getEL : array) {
                if (comparator.compare(getEL, fromElement) > 0 && comparator.compare(getEL, toElement) < 0) {
                    returned.add(getEL);
                }
            }
        }
        if (fromInclusive && !toInclusive){
            for (T getEL : array) {
                if (comparator.compare(getEL, fromElement) >= 0 && comparator.compare(getEL, toElement) < 0) {
                    returned.add(getEL);
                }
            }
        }
        else{
            for (T getEL : array) {
                if (comparator.compare(getEL, fromElement) > 0 && comparator.compare(getEL, toElement) <= 0) {
                    returned.add(getEL);
                }
            }
        }


        return returned;
    }

    /**
     * Returns a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement.
     *
     * @return a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement.
     */

    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        NavigableSet<T> returned = new AddSet<T>(comparator);

        if (inclusive) {
            for (T getEl : array) {
                if (comparator.compare(getEl, toElement) <= 0) returned.add(getEl);
                else break;
            }
        } else {
            for (T getEl : array) {
                if (comparator.compare(getEl, toElement) < 0) returned.add(getEl);
                else break;
            }
        }
        return returned;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than (or equal to, if inclusive is true) fromElement.
     *
     * @return a view of the portion of this set whose elements are greater than or equal to fromElement.
     */

    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        NavigableSet<T> returned = new AddSet<T>(comparator);
        if (inclusive) {
            for (T getEl : array) {
                if (comparator.compare(getEl, fromElement) >= 0) returned.add(getEl);
            }

        } else {
            for (T getEl : array) {
                if (comparator.compare(getEl, fromElement) > 0) returned.add(getEl);
            }
        }
        return returned;
    }

    /**
     * Returns the comparator used to order the elements in this set, or null if this set uses the natural ordering of its elements.
     *
     * @return the comparator used to order the elements in this set, or null if this set uses the natural ordering of its elements.
     */

    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * Returns the part of this set whose elements are located from fromElement to toElement of type  {@code T}.
     * @return a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
     */

    public SortedSet<T> subSet(T fromElement, T toElement) {
        SortedSet<T> returned = new AddSet<T>(comparator);
        if (comparator.compare(fromElement, toElement) > 0) throw new IllegalArgumentException();

        for (T getEL : array) {
            if (comparator.compare(getEL, fromElement) >= 0 && comparator.compare(getEL, toElement) < 0) {
                returned.add(getEL);
            }
        }

        return returned;
    }

    /**
     * Returns a view of the portion of this set whose elements are strictly less than toElement.
     *
     * @return a view of the portion of this set whose elements are strictly less than toElement.
     */

    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> returned = new AddSet<T>(comparator);

        for (T getEl : array) {
            if (comparator.compare(getEl, toElement) < 0) returned.add(getEl);
            else break;
        }

        return returned;
    }

    /**
     * Returns a view of the portion of this set whose elements are greater than or equal to fromElement.
     *
     * @return a view of the portion of this set whose elements are greater than or equal to fromElement.
     */

    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> returned = new AddSet<T>(comparator);

        for (T getEl : array) {
            if (comparator.compare(getEl, fromElement) >= 0) returned.add(getEl);
        }

        return returned;
    }

    /**
     * Returns the first (lowest) element currently in this set.
     * @return the first (lowest) element currently in this set.
     */

    public T first() {
        return array.get(0);
    }

    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set.
     */

    public T last() {
        return array.get(array.size() - 1);
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list.
     */

    public int size() {
        return array.size();
    }

}
