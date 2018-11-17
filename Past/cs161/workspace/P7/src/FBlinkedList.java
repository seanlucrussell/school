public class FBlinkedList {

	private FBnode head;
	private int size;

	public FBlinkedList() {
		head = null;
		size = 0;
	}

	public void clear() {
		head = null;
	}

	public void add(FBaccount item) {
		add(size, item);
	}

	public void add(int index, FBaccount item) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException(
					"List index out of bounds on add");
		if (index == 0) {
			head = new FBnode(item, head);
		} else { // find predecessor
			FBnode curr = head;
			for (int i = 0; i < index - 1; i++) {
				curr = curr.getNext();
			}
			// append after predecessor
			curr.setNext(new FBnode(item, curr.getNext()));
		}
		size++;
	}
	
	public boolean elementOf(FBaccount item){
		FBnode curr = head;
		for (int i=0;i<size;i++){
			if (curr.getItem().equals(item)){
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	}
	
	public int indexOf(String username){
		FBnode curr = head;
		for (int i=0;i<size;i++){
			if (curr.getItem().getUsername().equals(username)){
				return i;
			}
			curr = curr.getNext();
		}
		return -1;
		
	}
	
	public boolean addBefore(FBaccount item1, Object item2) {

		FBnode curr = head;
		while (curr.getNext()!=null){
			if (curr.getNext().getItem().equals(item2)){
				System.out.println("adding");
				curr.setNext(new FBnode(item1, curr.getNext()));
				size++;
				return true;
			}
			curr=curr.getNext();
		}
		return false;
	}

	public FBaccount getFriend(int index){
		FBnode curr = head;
		if (index==0){
			return curr.getItem();
		}
		for (int i=0;i<index;i++)
			curr = curr.getNext();
		return curr.getItem();
	}
	
	public String friendliest(){
		String friendliest = "";
		int friendliestFriends = 0;
		FBnode curr = head;
		for(int i=0;i<size;i++){
			if (curr.getItem().getFriends().size() > friendliestFriends){
				friendliestFriends = curr.getItem().getFriends().size();
				friendliest = curr.getItem().getUsername();
			}
			curr = curr.getNext();
		}
		
		return friendliest;
	}
	
	public void remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException(
					"List index out of bounds on remove");
		if (index == 0) {
			head = head.getNext();
		} else { // locate predecessor of node to be removed
			FBnode curr = head;
			for (int i = 0; i < index - 1; i++)
				curr = curr.getNext();
			curr.setNext(curr.getNext().getNext());
		}
		size--;
	}

	public boolean remove(FBaccount item) {

		if (size == 0)
			return false;
		if (item.equals(head.getItem())) {
			head = head.getNext();
			size--;
			return true;
		} else {
			// locate predecessor of node to be removed
			FBnode curr = head;
			while (curr.getNext() != null
					&& !item.equals(curr.getNext().getItem())) {
				curr = curr.getNext();
			}
			// if node is not in the list
			if (curr.getNext() == null)
				return false;
			else {
				curr.setNext(curr.getNext().getNext());
				size--;
				return true;
			}
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public String toString() {
		String res = "[" + size + ": ";
		for (FBnode current = head; current != null; current = current.getNext())
			res += current.getItem().toString() + " ";
		return res + "]";
	}
	
	public FBlinkedList compareLists(FBlinkedList list1, FBlinkedList list2){
		FBlinkedList retList = new FBlinkedList();
		for (int i=0;i<list1.size();i++){
			outerloop:
			for (int j=0;j<list2.size();j++){
				if (list1.getFriend(i).equals(list2.getFriend(j))){
					for (int k=0;k<retList.size();k++){
						if (retList.getFriend(k).equals(list1.getFriend(i))){
							break outerloop;
						}
					}
					retList.add(list1.getFriend(i));
				}
			}
		}
		
		return retList;
	}
	
}
