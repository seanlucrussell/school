public class LinkedBookList {

	private BookNode head;
	private int size;
	
	public LinkedBookList(){
		head = null;
		size = 0;
	}
	
	public int size(){
		return size;
	}
	
	public void add(Book b){
		if (head == null){
			head = new BookNode(b);
		}
		BookNode current = head;
		while (current.getNext() != null){
			current = current.getNext();
		}
		
		current.setNext(new BookNode(b));
		size ++;
	}
	
	public void add(Book b, int index){
		BookNode current = head;
		for(int i=0;i<index;i++)
			current = current.getNext();
		
		BookNode next = current.getNext();
		
		current.setNext(new BookNode(b,next));
		
		if (index >= size)
			add(b);
		size ++;
	}
	
	public Book remove(Book b){
		BookNode current = head;
		BookNode prev = head;
		
		while (current.getNext() != null){
			prev = current;
			current = current.getNext();
			if (current.getBook().equals(b)){
				Book temp = current.getBook();
				prev.setNext(current.getNext());
				size --;
				return temp;
			}
			
		}
		
		return null;
	}

	public Book remove(int index){
		if(index >= size){
			return null;
		}
		
		BookNode current = head;
		BookNode prev = head;
		
		for (int i=0;i<=index;i++){
			prev = current;
			current = current.getNext();
		}
		
		Book b = current.getBook();
		
		prev.setNext(current.getNext());
		size --;
		return b;
	}
	
	public int totalPages(){
		int pages = 0;
		BookNode current = head;
		while (current.getNext() != null){
			current = current.getNext();
			pages += current.getNumPages();
		}
		return pages;
	}
	
	public String toString(){
		String res = "";
		BookNode current = head;
		while (current.getNext() != null){
			current = current.getNext();
			res += current.toString() + "\n";
		}
		//res = res.substring(0, res.length());
		return res;
	}
	
}




