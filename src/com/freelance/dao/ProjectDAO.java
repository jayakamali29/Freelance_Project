package com.freelance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.freelance.bean.Project;
import com.freelance.util.DBUtil;

public class ProjectDAO {

    public Project findProject(int projectID) {
    	Project project = null;
        try {
        	Connection con=DBUtil.getDBConnection();
     
        String sql = "SELECT *FROM PROJECT_TBL WHERE projectID = ?";
        PreparedStatement ps=con.prepareStatement(sql);
    ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    project = new Project();
                    project.setProjectID(rs.getInt("projectID"));
                    project.setClientUserID(rs.getString("clientUserID"));
                    project.setProjectTitle(rs.getString("projectTitle"));
                    project.setProjectDescription(rs.getString("projectDescription"));
                    project.setBudgetMin(rs.getBigDecimal("budgetMin"));
                    project.setBudgetMax(rs.getBigDecimal("budgetMax"));
                    project.setPostedDate(rs.getDate("postedDate"));
                    project.setStatus(rs.getString("status"));
                    project.setAwardedBidID(rs.getInt("awardedBidID"));
                  
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }
    public java.util.List<Project>viewAllProjects(){
    	List<Project>projects=new ArrayList<>();
    	Project project = null;
    	try {
    		Connection con=DBUtil.getDBConnection();
            
            
            String sql = "SELECT projectID, clientUserID, projectTitle, " +
                         "projectDescription, budgetMin, budgetMax, " +
                         "postedDate, status, awardedBidID " +
                         "FROM PROJECT_TBL";
            PreparedStatement ps=con.prepareStatement(sql);
              ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                    	project = new Project();	
                        project.setProjectID(rs.getInt("projectID"));
                        project.setClientUserID(rs.getString("clientUserID"));
                        project.setProjectTitle(rs.getString("projectTitle"));
                        project.setProjectDescription(rs.getString("projectDescription"));
                        project.setBudgetMin(rs.getBigDecimal("budgetMin"));
                        project.setBudgetMax(rs.getBigDecimal("budgetMax"));
                        project.setPostedDate(rs.getDate("postedDate"));
                        project.setStatus(rs.getString("status"));
                        int bidId = rs.getInt("awardedBidID");
                        project.setAwardedBidID(rs.wasNull() ? null : bidId);
                        projects.add(project);
                    }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return projects;
        }
    public java.util.List<Project> viewOpenProjects() 
    {
    	List<Project>projects=new ArrayList<>();
    	Project project = null;
    	try {
    		Connection con=DBUtil.getDBConnection();
            
            
            String sql = "SELECT projectID, clientUserID, projectTitle, " +
                         "projectDescription, budgetMin, budgetMax, " +
                         "postedDate, status, awardedBidID " +
                         "FROM PROJECT_TBL where status='open'";
            PreparedStatement ps=con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                    	project = new Project();
                    	
                        project.setProjectID(rs.getInt("projectID"));
                        project.setClientUserID(rs.getString("clientUserID"));
                        project.setProjectTitle(rs.getString("projectTitle"));
                        project.setProjectDescription(rs.getString("projectDescription"));
                        project.setBudgetMin(rs.getBigDecimal("budgetMin"));
                        project.setBudgetMax(rs.getBigDecimal("budgetMax"));
                        project.setPostedDate(rs.getDate("postedDate"));
                        project.setStatus(rs.getString("status"));
                        int bidId = rs.getInt("awardedBidID");
                        project.setAwardedBidID(rs.wasNull() ? null : bidId);
                        projects.add(project);
                    }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return projects;
    	
    }
    public int generateProjectID() {
        int newID = 1; 

        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT MAX(projectID) FROM PROJECT_TBL";
            PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int maxID = rs.getInt(1);
                newID = maxID + 1;        
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newID;
    }
    public boolean insertProject(Project project)
    {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "insert into PROJECT_TBL values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, project.getProjectID());
            ps.setString(2, project.getClientUserID());
            ps.setString(3, project.getProjectTitle());
            ps.setString(4, project.getProjectDescription());
            ps.setBigDecimal(5, project.getBudgetMin());
            ps.setBigDecimal(6, project.getBudgetMax());
            ps.setDate(7, project.getPostedDate());
            ps.setString(8, project.getStatus());
            ps.setObject(9, project.getAwardedBidID()); // handles NULL
           int row = ps.executeUpdate();

            if (row > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateProjectStatus(int projectID, String status)
    {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "update PROJECT_TBL set status=? where projectID=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, projectID);
           int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateAwardedBid(int projectID, int bidID, String status)
    {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "UPDATE PROJECT_TBL SET awardedBidID=?, status=? WHERE projectID=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bidID);
            ps.setString(2, status);
            ps.setInt(3, projectID);
            int row = ps.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public java.util.List<Project> findActiveProjectsByClient(String clientUserID)
    {
        java.util.List<Project> projects = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT projectID, clientUserID, projectTitle, " +
                         "projectDescription, budgetMin, budgetMax, " +
                         "postedDate, status, awardedBidID " +
                         "FROM PROJECT_TBL " +
                         "WHERE clientUserID=? AND (status='OPEN' OR status='AWARDED')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, clientUserID);
           ResultSet rs = ps.executeQuery();
  while (rs.next()) {
                Project project = new Project();
                project.setProjectID(rs.getInt("projectID"));
                project.setClientUserID(rs.getString("clientUserID"));
                project.setProjectTitle(rs.getString("projectTitle"));
                project.setProjectDescription(rs.getString("projectDescription"));
                project.setBudgetMin(rs.getBigDecimal("budgetMin"));
                project.setBudgetMax(rs.getBigDecimal("budgetMax"));
                project.setPostedDate(rs.getDate("postedDate"));
                project.setStatus(rs.getString("status"));

                int bidId = rs.getInt("awardedBidID");
                project.setAwardedBidID(rs.wasNull() ? null : bidId);

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }   	
}