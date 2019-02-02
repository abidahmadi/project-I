package dao;


import model.User;

public interface UserDaoInter {
	
	public int addUser(User m);
	
	public User getUser(String username, String password);
	
	public int getUserID(String username);
	
	//
	
	public String[] getUserById(int num);
	
	
	public boolean checkDuplUsername(String m);
	
	public boolean checkDuplEmail(String m);
	
	public User[] getUserByID(int[] id);
}
