package daoImpl;

import java.sql.*;

import dao.UserDaoInter;
import model.User;
import serviceUtility.Utilities;

public class UserDaoImpl implements UserDaoInter{
	
@Override//add User
public int addUser(User m) {
		int i =0;
		Connection con = Utilities.connect();
		
		try {
			String sql = "insert into User(Full_Name, Email_Address, Phone, Username, Password)values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, m.getName());
			ps.setString(2, m.getUserEmail());
			ps.setString(3, m.getUserPhone());
			ps.setString(4, m.getUserName());
			ps.setString(5, m.getPassword());
		
			i=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
@Override //get User
public User getUser(String username, String password) {
		User c = new User();
		Connection con = Utilities.connect();
		try{
				String sql = "SELECT * FROM User WHERE Username=? and Password=? ";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
				
		while(rs.next()){
			c.setName(rs.getString(2));
			c.setUserEmail(rs.getString(3));
			c.setUserPhone(rs.getString(4));
			c.setUserName(rs.getString(5));
			c.setPassword(rs.getString(6));
			c.setId(rs.getInt(1));
			c.setStatus(rs.getInt(8));
		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return c;
	}
@Override
public int getUserID(String username) {
		// get user id only. 
			int userID = 0;
			String userName;
			
				Connection con = Utilities.connect();
			try{
				String sql = "Select * From User WHERE Username=?";
				PreparedStatement pss = con.prepareStatement(sql);
				pss.setString(1, username);
				ResultSet rs = pss.executeQuery();
				
			if (rs.next()){
				userID = rs.getInt("User_ID");
				userName = rs.getString("Full_Name");
				
			}
			}catch(SQLException e){ e.printStackTrace();}	
		return userID;
	}
@Override
public String[] getUserById(int num) {
		
		String[] userInfo = new String[3];
		Connection con = Utilities.connect();
		try{
			String qry = "Select * from User WHERE User_ID='"+num+"'";
			PreparedStatement ps = con.prepareStatement(qry);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userInfo[0] = rs.getString("Full_Name");
				userInfo[1] = rs.getString("Phone");
				userInfo[2] = rs.getString("Email_Address");
			}
			}catch(SQLException g){System.out.println("error is here on UserDaoImpl method getUserById");}
		return userInfo;
	}
@Override
public boolean checkDuplUsername(String m) {
		boolean duplicate =false;
		Connection con = Utilities.connect();
		try{
			String qry = "Select * FROM User WHERE Username='"+m+"'";
			PreparedStatement ps = con.prepareStatement(qry);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				duplicate = true;
			}
		}catch(SQLException e){e.printStackTrace();}
		return duplicate;
	}
@Override
public boolean checkDuplEmail(String m) {
		boolean duplicate =false;
		Connection con = Utilities.connect();
		try{
			String qry = "Select * FROM User WHERE Email_Address='"+m+"'";
			PreparedStatement ps = con.prepareStatement(qry);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				duplicate = true;
			}
		}catch(SQLException e){e.printStackTrace();}
		return duplicate;
	}
@Override
public User[] getUserByID(int[] id) {
		User[] a = null;
		int arraySize = id.length;
		a = new User[arraySize];
		for(int i =0; i < id.length; i++){	
		Connection con = Utilities.connect();
		int b = id[i];
		String qry = "Select * FROM User WHERE User_ID='"+b+"'";
		
	try{
		PreparedStatement ps = con.prepareStatement(qry);
		ResultSet rs = ps.executeQuery();
			if(rs.next()){
				a[i] = new User();
				a[i].setName(rs.getString("Full_Name"));
				a[i].setUserEmail(rs.getString("Email_Address"));
				a[i].setUserPhone(rs.getString("Phone"));
			}
		
		}catch(SQLException e){e.printStackTrace();}
	
		}
		return a;	
  }

}