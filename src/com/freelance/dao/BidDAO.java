package com.freelance.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.freelance.bean.Bid;
import com.freelance.util.DBUtil;
public class BidDAO {
    public int generateBidID() {
        int newID = 1; 
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT MAX(bidID) FROM BID_TBL";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt(1);
                newID = maxID + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newID;
    }
    public boolean insertBid(Bid bid) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "INSERT INTO BID_TBL VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bid.getBidID());
            ps.setInt(2, bid.getProjectID());
            ps.setString(3, bid.getFreelancerUserID());
            ps.setBigDecimal(4, bid.getBidAmount());
            ps.setInt(5, bid.getDeliveryDays());
            ps.setString(6, bid.getCoverLetter());
            ps.setDate(7, bid.getBidDate());
            ps.setString(8, bid.getBidStatus());

            int row = ps.executeUpdate();
            return row > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Bid findBid(int bidID) {
        Bid bid = null;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM BID_TBL WHERE bidID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bidID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bid = new Bid();
                bid.setBidID(rs.getInt("bidID"));
                bid.setProjectID(rs.getInt("projectID"));
                bid.setFreelancerUserID(rs.getString("freelancerUserID"));
                bid.setBidAmount(rs.getBigDecimal("bidAmount"));
                bid.setDeliveryDays(rs.getInt("deliveryDays"));
                bid.setCoverLetter(rs.getString("coverLetter"));
                bid.setBidDate(rs.getDate("bidDate"));
                bid.setBidStatus(rs.getString("bidStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid; 
    }
    public List<Bid> findBidsByProject(int projectID) {
        List<Bid> bids = new ArrayList<>();
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM BID_TBL WHERE projectID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Bid bid = new Bid();
                bid.setBidID(rs.getInt("bidID"));
                bid.setProjectID(rs.getInt("projectID"));
                bid.setFreelancerUserID(rs.getString("freelancerUserID"));
                bid.setBidAmount(rs.getBigDecimal("bidAmount"));
                bid.setDeliveryDays(rs.getInt("deliveryDays"));
                bid.setCoverLetter(rs.getString("coverLetter"));
                bid.setBidDate(rs.getDate("bidDate"));
                bid.setBidStatus(rs.getString("bidStatus"));
                bids.add(bid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bids;
    }
    public List<Bid> findBidsByFreelancer(String freelancerUserID) {
        List<Bid> bids = new ArrayList<>();
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM BID_TBL WHERE freelancerUserID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, freelancerUserID);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Bid bid = new Bid();
                bid.setBidID(rs.getInt("bidID"));
                bid.setProjectID(rs.getInt("projectID"));
                bid.setFreelancerUserID(rs.getString("freelancerUserID"));
                bid.setBidAmount(rs.getBigDecimal("bidAmount"));
                bid.setDeliveryDays(rs.getInt("deliveryDays"));
                bid.setCoverLetter(rs.getString("coverLetter"));
                bid.setBidDate(rs.getDate("bidDate"));
                bid.setBidStatus(rs.getString("bidStatus"));
                bids.add(bid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bids;
    }
    public Bid findActiveBidForProjectAndFreelancer(int projectID, String freelancerUserID) {
        Bid bid = null;
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "SELECT * FROM BID_TBL WHERE projectID=? AND freelancerUserID=? AND bidStatus='PENDING'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectID);
            ps.setString(2, freelancerUserID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {  
                bid = new Bid();
                bid.setBidID(rs.getInt("bidID"));
                bid.setProjectID(rs.getInt("projectID"));
                bid.setFreelancerUserID(rs.getString("freelancerUserID"));
                bid.setBidAmount(rs.getBigDecimal("bidAmount"));
                bid.setDeliveryDays(rs.getInt("deliveryDays"));
                bid.setCoverLetter(rs.getString("coverLetter"));
                bid.setBidDate(rs.getDate("bidDate"));
                bid.setBidStatus(rs.getString("bidStatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid;
    }
    public boolean updateBidStatus(int bidID, String status) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "UPDATE BID_TBL SET bidStatus=? WHERE bidID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, bidID);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean bulkRejectOtherBids(int projectID, int acceptedBidID) {
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "UPDATE BID_TBL SET bidStatus='REJECTED' WHERE projectID=? AND bidID<>?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectID);
            ps.setInt(2, acceptedBidID);
            int row = ps.executeUpdate();
            return row > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Bid> findActiveBidsForFreelancer(String freelancerUserID) {
        List<Bid> bids = new ArrayList<>();
        try {
            Connection con = DBUtil.getDBConnection();
            String sql = "select b.* from  BID_TBL b join PROJECT_TBL p WHERE b.projectID=p.projectID where b.freelencerUserID=? and p.status IN ('OPEN', 'AWARDED') AND b.bidStatus NOT IN ('REJECTED', 'WITHDRAWN')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, freelancerUserID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bid bid = new Bid();
                bid.setBidID(rs.getInt("bidID"));
                bid.setProjectID(rs.getInt("projectID"));
                bid.setFreelancerUserID(rs.getString("freelancerUserID"));
                bid.setBidAmount(rs.getBigDecimal("bidAmount"));
                bid.setDeliveryDays(rs.getInt("deliveryDays"));
                bid.setCoverLetter(rs.getString("coverLetter"));
                bid.setBidDate(rs.getDate("bidDate"));
                bid.setBidStatus(rs.getString("bidStatus"));
                bids.add(bid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bids;
    }
}