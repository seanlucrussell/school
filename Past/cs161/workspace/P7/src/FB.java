// P7 Assignment
// Author: Sean Russell
// Date:   May 4, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.util.ArrayList;

public class FB {
	
	protected ArrayList<FBaccount> accounts;
	
	public FB(){
		accounts = new ArrayList<FBaccount>();
	}
	
	public boolean newAccount(String username, String name){
		for (FBaccount a: accounts)
			if (a.equals(new FBaccount(username,name)))
				return false;
		accounts.add(new FBaccount(username,name));
		return true;
	}
	
	public boolean closeAccount(String username){
		for (FBaccount a: accounts)
			if (a.equals(new FBaccount(username,""))){
				a.unfriendAll();
				accounts.remove(a);
				return true;
			}
		return false;
	}
	
	public boolean makeFriends(String username1, String username2){
		for (FBaccount a: accounts)
			if (a.getUsername().equals(username1))
				for (FBaccount b: accounts)
					if (b.getUsername().equals(username2)){
						a.addFriend(b);
						return true;
					}
				
		return false;
	}
	
	public String friendRecommend(String username){
		FBaccount user = null;
		for (FBaccount a: accounts)
			if (a.equals(new FBaccount(username,"")))
				user = a;
		return user.getFriends().friendliest();
	}
	
}
