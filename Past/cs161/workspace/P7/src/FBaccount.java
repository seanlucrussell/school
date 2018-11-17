// P7 Assignment
// Author: Sean Russell
// Date:   May 4, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class FBaccount {
	
	protected String name;
	protected String username;
	protected FBlinkedList friends;
	
	public FBaccount(String username, String name){
		this.name = name;
		this.username = username;
		friends = new FBlinkedList();
	}
	
	public void addFriend(FBaccount friend){
		if (friend.equals(this) || friends.elementOf(friend))
			return;
		friends.add(friend);
		friend.getFriends().add(this);
	}
	
	public boolean unfriend(String username){
		if (friends.indexOf(username)==-1)
			return false;
		friends.getFriend(friends.indexOf(username)).getFriends().remove(friends.getFriend(friends.indexOf(username)).getFriends().indexOf(this.getUsername()));
		friends.remove(friends.indexOf(username));
		return true;
	}
	
	public FBlinkedList friendsInCommon(FBaccount other){
		return friends.compareLists(this.friends,other.getFriends());
	}
	
	public String getName(){
		return name;
	}
	
	public String getUsername(){
		return username;
	}
	
	public FBlinkedList getFriends(){
		return friends;
	}
	
	public boolean equals(Object other){
		if (other == this){
			return true;
		}
		
		if (!(other instanceof FBaccount)){
			return false;
		}
		
		FBaccount fbother = (FBaccount) other;
		if (fbother.getUsername().equals(username)){
			return true;
		}
		return false;
		
	}

	public int numFriends(){
		return friends.size();
	}
	
	public String toString(){
		return username;
	}
	
	public void unfriendAll(){
		while (friends.size() > 0){
			unfriend(friends.getFriend(0).getUsername());
		}
	}
}
