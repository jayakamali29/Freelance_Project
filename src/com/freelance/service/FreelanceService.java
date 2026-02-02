package com.freelance.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.freelance.bean.Bid;
import com.freelance.bean.Project;
import com.freelance.bean.User;
import com.freelance.dao.BidDAO;
import com.freelance.dao.ProjectDAO;
import com.freelance.dao.UserDAO;
import com.freelance.util.ActiveEngagementsExistException;
import com.freelance.util.DBUtil;
import com.freelance.util.ProjectAwardingException;
import com.freelance.util.ValidationException;

public class FreelanceService {

    public User viewUserDetails(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            return null;
        }

        UserDAO user=new UserDAO();
        return user.findUser(userID);
    }
    public java.util.List<User>viewAllUsers(){
    	UserDAO user=new UserDAO();
    	return user.viewAllUsers();
    	
    }
    public boolean registerNewUser(User user) {
        if (user == null ||
            user.getUserID() == null || user.getUserID().trim().isEmpty() ||
            user.getFullName() == null || user.getFullName().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty() ||
            user.getUserRole() == null || user.getUserRole().trim().isEmpty()) {

            throw new ValidationException("Required fields must not be empty");
        }
        String role = user.getUserRole();
        if (!role.equals("CLIENT") && !role.equals("FREELANCER")) {
            throw new ValidationException("Role must be CLIENT or FREELANCER");
        }
        UserDAO userDAO = new UserDAO();
        if (userDAO.findUser(user.getUserID()) != null) {
            return false;
        }
        user.setStatus("ACTIVE");
        return userDAO.insertUser(user);
    }
    public boolean postNewProject(Project project) {
        if (project == null ||
            project.getClientUserID() == null || project.getClientUserID().trim().isEmpty() ||
            project.getProjectTitle() == null || project.getProjectTitle().trim().isEmpty() ||
            project.getProjectDescription() == null || project.getProjectDescription().trim().isEmpty() ||
            project.getBudgetMin() == null ||
            project.getBudgetMax() == null ||
            project.getBudgetMin().compareTo(BigDecimal.ZERO) <= 0 ||
            project.getBudgetMax().compareTo(BigDecimal.ZERO) <= 0 ||
            project.getBudgetMax().compareTo(project.getBudgetMin()) < 0) {

            throw new ValidationException("Invalid project data");
        }
        UserDAO userDAO = new UserDAO();
        User client = userDAO.findUser(project.getClientUserID());

        if (client == null ||
            !"CLIENT".equalsIgnoreCase(client.getUserRole()) ||
            !"ACTIVE".equalsIgnoreCase(client.getStatus())) {
            return false;
        }
        Connection con = null;
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);
            project.setProjectID(projectDAO.generateProjectID());
            project.setPostedDate(new java.sql.Date(System.currentTimeMillis()));
            project.setStatus("OPEN");
            boolean inserted = projectDAO.insertProject(project);
            if (!inserted) {
                con.rollback();
                return false;
            }
            con.commit();
            return true;
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

    public boolean placeBid(int projectID,String freelancerUserID,java.math.BigDecimal bidAmount,int deliveryDays,String coverLetter) 
    {
    	if(projectID<=0 || freelancerUserID==null || freelancerUserID.trim().isEmpty()|| bidAmount==null ||bidAmount.compareTo(BigDecimal.ZERO)<=0||deliveryDays<=0) {
    		throw new ValidationException("Invalid bid data");
    	}
    	ProjectDAO projectDAO=new ProjectDAO();
        Project project=projectDAO.findProject(projectID);
        if(project==null || !"OPEN".equalsIgnoreCase(project.getStatus()))
        {
        	return false;
        }
        UserDAO userDAO = new UserDAO();
        User freelancer=userDAO.findUser(freelancerUserID);
        if(freelancer==null || !"FREELANCER".equalsIgnoreCase(freelancer.getUserRole())|| !"ACTIVE".equalsIgnoreCase(freelancer.getStatus()))
        {
        	return false;
        }
        if (bidAmount.compareTo(project.getBudgetMin()) < 0 ||
                bidAmount.compareTo(project.getBudgetMax()) > 0) {
                return false; 
            }
        BidDAO bidDAO = new BidDAO();
        Bid existingBid =
            bidDAO.findActiveBidForProjectAndFreelancer(projectID, freelancerUserID);

        if (existingBid != null) {
            throw new ValidationException("Duplicate active bid not allowed");
        }
        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            int bidID = bidDAO.generateBidID();

            Bid bid = new Bid();
            bid.setBidID(bidID);
            bid.setProjectID(projectID);
            bid.setFreelancerUserID(freelancerUserID);
            bid.setBidAmount(bidAmount);
            bid.setDeliveryDays(deliveryDays);
            bid.setCoverLetter(coverLetter);
            bid.setBidStatus("PENDING");
            bid.setBidDate(new java.sql.Date(System.currentTimeMillis()));
            boolean inserted = bidDAO.insertBid(bid);

            if (!inserted) {
                con.rollback();
                return false;
            }

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
            return false;
    }
    public boolean awardProject(int projectID, int bidID) {

        ProjectDAO projectDAO = new ProjectDAO();
        BidDAO bidDAO = new BidDAO();
        Connection con = null;

        try {
            if (projectID <= 0 || bidID <= 0) {
                throw new ProjectAwardingException("projectID and bidID must be positive");
            }
            Project project = projectDAO.findProject(projectID);
            if (project == null || !"OPEN".equalsIgnoreCase(project.getStatus())) {
                throw new ProjectAwardingException("Project not found or not OPEN. ProjectID=" + projectID);
            }
            Bid bid = bidDAO.findBid(bidID);
            if (bid == null || bid.getProjectID() != projectID || !"PENDING".equalsIgnoreCase(bid.getBidStatus())) {
                throw new ProjectAwardingException("Invalid bid for awarding. ProjectID=" + projectID + ", BidID=" + bidID);
            }
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            if (!bidDAO.updateBidStatus(bidID, "ACCEPTED")) {
                throw new ProjectAwardingException("Failed to accept bid. ProjectID=" + projectID + ", BidID=" + bidID);
            }

            if (!bidDAO.bulkRejectOtherBids(projectID, bidID)) {
                throw new ProjectAwardingException("Failed to reject other bids. ProjectID=" + projectID);
            }

            if (!projectDAO.updateAwardedBid(projectID, bidID, "AWARDED")) {
                throw new ProjectAwardingException("Failed to update project status. ProjectID=" + projectID + ", BidID=" + bidID);
            }

            con.commit();
            return true;

        } catch (ProjectAwardingException e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.err.println(e.toString());
            return false;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }


    public boolean markProjectCompleted(int projectID) {
        if (projectID <= 0) {
            throw new ValidationException("ProjectID must be positive");
        }

        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.findProject(projectID);
        if (project == null || !"AWARDED".equalsIgnoreCase(project.getStatus())) {
            return false;
        }

        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean updated =
                projectDAO.updateProjectStatus(projectID, "COMPLETED");

            if (!updated) {
                con.rollback();
                return false;
            }
            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    public List<Bid> listBidsByProject(int projectID) {
        BidDAO bidDAO = new BidDAO();
        return bidDAO.findBidsByProject(projectID);
    }
    public List<Bid> listBidsByFreelancer(String freelancerUserID) {
        BidDAO bidDAO = new BidDAO();
        return bidDAO.findBidsByFreelancer(freelancerUserID);
    }
    public boolean removeUser(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            throw new ValidationException("UserID cannot be blank");
        }
        UserDAO userDAO = new UserDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        BidDAO bidDAO = new BidDAO();
        User user = userDAO.findUser(userID);
        if (user == null) {
            return false; 
        }

        try {
            String role = user.getUserRole();
            if ("CLIENT".equalsIgnoreCase(role)) {
                List<Project> activeProjects = projectDAO.findActiveProjectsByClient(userID);
                if (activeProjects != null && !activeProjects.isEmpty()) {
                    throw new ActiveEngagementsExistException(
                        "Client has " + activeProjects.size() + " active project(s). Cannot remove user."
                    );
                }
            } else if ("FREELANCER".equalsIgnoreCase(role)) {
                List<Bid> activeBids = bidDAO.findActiveBidsForFreelancer(userID);
                if (activeBids != null && !activeBids.isEmpty()) {
                    throw new ActiveEngagementsExistException(
                        "Freelancer has " + activeBids.size() + " active bid(s). Cannot remove user."
                    );
                }
            }

            return userDAO.deleteUser(userID);

        } catch (ActiveEngagementsExistException e) {
            System.err.println(e.toString());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}