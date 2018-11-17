
public class FBnode {
    private FBaccount item;
    private FBnode next;

    public FBnode(FBaccount item) {
        this.item = item;
        this.next = null;
    }
    public FBnode(FBaccount item, FBnode next) {
        this.item = item;
        this.next = next;
    }
    public void setNext(FBnode nextNode) {
		next = nextNode;
    }
    public FBnode getNext() {
    		return next;
    }
    public FBaccount getItem() {
    		return item;
    }
    public void setItem(FBaccount item){
    		this.item = item;
    }
    public String toString(){
    		return item.toString();
    }
    
    public static void main(String[] args) {
 
    }
}
