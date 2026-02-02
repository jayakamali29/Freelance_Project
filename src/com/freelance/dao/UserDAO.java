package com.freelance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.freelance.bean.User;
import com.freelance.util.DBUtil;

public class UserDAO {
	public User findUser(String userID) {
	    try { 
	    	Connection con = DBUtil.getDBConnection();
	    	String sql = "SELECT userID,fullName,email,mobile,userRole, primarySkillOrCompany,status from USERS_TBL where userID=?";
	         PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, userID);
	        ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                User user = new User();
	                user.setUserID(rs.getString("userID"));
	                user.setFullName(rs.getString("fullName"));
	                user.setEmail(rs.getString("email"));
	                user.setMobile(rs.getString("mobile"));
	                user.setUserRole(rs.getString("userRole"));
	                user.setPrimarySkillOrCompany(
	                        rs.getString("primarySkillOrCompany"));
	                user.setStatus(rs.getString("status"));
	                return user;
	            }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null; 
	}
	public java.util.List<User>viewAllUsers(){
		List<User>user=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			String sql="SELECT userID,fullName,email,mobile,userRole, primarySkillOrCompany,status from USERS_TBL";
		    PreparedStatement ps=con.prepareStatement(sql);
		    ResultSet rs=ps.executeQuery();
		    	 if (rs.next()) {
		                User users = new User();
		                users.setUserID(rs.getString("userID"));
		                users.setFullName(rs.getString("fullName"));
		                users.setEmail(rs.getString("email"));
		                users.setMobile(rs.getString("mobile"));
		                users.setUserRole(rs.getString("UserRole"));
		                users.setPrimarySkillOrCompany(
		                        rs.getString("primarySkillOrompany"));
		                users.setStatus(rs.getString("status"));
		                user.add(users);
		               
		            }
		    
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		 return user;
	}
	public boolean insertUser(User user)
	{
		try {
			Connection con=DBUtil.getDBConnection();
			String sql="insert into USERS_TBL values(?,?,?,?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, user.getUserID());
			ps.setString(2, user.getFullName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getMobile());
			ps.setString(5, user.getUserRole());
			ps.setString(6, user.getPrimarySkillOrCompany());
			ps.setString(7, user.getStatus());
			int row=ps.executeUpdate();
			if(row>0)
			{
				return true;
				
			}
			else {
				return false;
			}}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		return false;
		}
	public boolean updateUserStatus(String userID,String status)
	{
		try {
			Connection con=DBUtil.getDBConnection();
			String sql="update USERS_TBL set status=? where userID=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1,status);
			ps.setString(2, userID);
			int row=ps.executeUpdate();
			if(row>0)
			{
				return true;
				
			}
			else {
				return false;
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		return false;
	}
	public boolean deleteUser(String userID) 
	{
		try {
			Connection con=DBUtil.getDBConnection();
			String sql="delete from USERS_TBL where userID=?";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, userID);
			int row=ps.executeUpdate();
			if(row>0)
			{
				return true;
				
			}
			else {
				return false;
			}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		return false;
	}
}