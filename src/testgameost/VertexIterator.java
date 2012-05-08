package testgameost;
import java.util.NoSuchElementException;

/**
 * An iterator over vertices in an UndirectedGraph object.
 * 
 * @author Stefan Nilsson
 * @version 2011-02-18
 */

public interface VertexIterator {
    /**
     * Returns true iff the iteration has more elements.
     *
     * @return true iff the iteration has more elements
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no
     *         more elements
     */
    int next() throws NoSuchElementException;
}
