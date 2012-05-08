package testgameost;
/**
 * A singly linked list.
 * 
 * @author 
 * @version
 */

public class LinkedList<T> {

	private ListElement<T> first;   // First element in list.
	private ListElement<T> last;    // Last element in list.
	private int size;               // Number of elements in list.

	/**
	 * A list element.
	 */
	private static class ListElement<T> {
		public T data;
		public ListElement<T> next;

		public ListElement(T data) {
			this.data = data;
			this.next = null;
		}
	}

	/**
	 * This test method returns true iff the following invariants hold:
	 * <ul>
	 *   <li> size equals the number of list elements, </li>
	 *   <li> if size == 0, first == null and last == null, </li>
	 *   <li> if size > 0, first != null and last != null, </li>
	 *   <li> if size == 1, first == last, </li>
	 *   <li> last.next == null. </li>
	 * </ul>
	 * 
	 * Värstafallstid: t(n) = n;
	 */
	public boolean invariants() {
		if (numberOfElements()!=size){
			return false;
		}
		if (size == 0 && (first != null || last != null)){
			return false;
		}
		if (size > 0 && (first == null || last == null)){
			return false;
		}
		if (size == 1 && first != last){
			return false;
		}
		if (size > 0 && last.next != null){
			return false;
		}
		return true;
	}

	/**
	 * Creates an empty list.
	 */
	public LinkedList() {
	}

	/**
	 * Inserts the given element at the beginning of this list.
	 * 
	 * Värstafallstid: konstant;
	 */
	public void addFirst(T element) {
		ListElement<T> newElement = new ListElement<T>(element);
		newElement.next = first;
		first = newElement;
		if(last == null){
			last = newElement;
		}
		size++;
	}

	/**
	 * Inserts the given element at the end of this list.
	 * 
	 * Värstafallstid: konstant;
	 */
	public void addLast(T element) {
		ListElement<T> newElement = new ListElement<T>(element);
		
		if(last != null){
			last.next = newElement;
		}
		
		if(first == null){
			first = newElement;
		}
		
		last = newElement;
		size++;
	}

	/**
	 * Returns the first element of this list.
	 * Returns <code>null</code> if the list is empty.
	 * 
	 * Värstafallstid: konstant;
	 */
	public T getFirst() {
		if(size!=0){
			return first.data;
		}
		return null;
	}

	/**
	 * Returns the last element of this list.
	 * Returns <code>null</code> if the list is empty.
	 * 
	 * Värstafallstid: konstant;
	 */
	public T getLast() {
		if(size!=0){
			return last.data;
		}
		return null;
	}

	/**
	 * Returns the element at the specified position in this list.
	 * Returns <code>null</code> if <code>index</code> is out of bounds.
	 * 
	 * Värstafallstid: t(n) = n;
	 */
	public T get(int index) {
		if(index>=size){
			return null;
		}
		ListElement<T> current = first;
		for(int i=0; i<index;i++){
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Removes and returns the first element from this list.
	 * Returns <code>null</code> if the list is empty.
	 * 
	 * Värstafallstid: konstant;
	 */
	public T removeFirst() {
		if(size!=0){
			T data = first.data;
			if(size > 1){
				first = first.next;
			}
			else{
				first = null;
				last = null;
			}
			size--;
			return data;
		}
		return null;
	}

	/**
	 * Removes all of the elements from this list.
	 *
	 * Värstafallstid: konstant;
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in this list.
	 * 
	 * Värstafallstid: konstant;
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns <code>true</code> if this list contains no elements.
	 * 
	 * Värstafallstid: konstant;
	 */
	public boolean isEmpty() {
		if(size==0){
			return true;
		}
		return false;
	}

	/**
	 * Returns a string representation of this list. The string
	 * representation consists of a list of the elements enclosed in
	 * square brackets ("[]"). Adjacent elements are separated by the
	 * characters ", " (comma and space). Elements are converted to
	 * strings by the method toString() inherited from Object.
	 * 
	 * Värstafallstid: t(n) = n;
	 */
	public String toString() {
		if(size==0){
			return null;
		}
		ListElement<T> current = first;
		String returnString = "[" + current.data.toString();
		for(int i=1; i<size;i++){
			current = current.next;
			returnString += ", " + current.data.toString();
		}
		returnString += "]";
		return returnString;
	}
	
	private int numberOfElements(){
		int numberOfElements = 0;
		ListElement<T> current = first;
		
		while (current != null){
			numberOfElements++;
			current = current.next;
		}
		return numberOfElements;
	}
}
