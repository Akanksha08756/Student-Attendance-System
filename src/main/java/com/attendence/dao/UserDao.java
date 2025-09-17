package com.attendence.dao;



	import com.attendence.config.DbConnectionPool;
	import com.attendence.Model.User;

	import java.sql.*;

	/**
	 * Simple User DAO.
	 * Columns assumed in users table: id (AUTO_INCREMENT), username (or email), password, role, email
	 */
	public class UserDao {

	    public boolean createUser(User user) {
	        String sql = "INSERT INTO users(username,password,role,email) VALUES(?,?,?,?)";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            ps.setString(1, user.getUsername());
	            ps.setString(2, user.getPassword());
	            ps.setString(3, user.getRole());
	            ps.setString(4, user.getEmail());

	            int affected = ps.executeUpdate();
	            if (affected > 0) {
	                try (ResultSet rs = ps.getGeneratedKeys()) {
	                    if (rs.next()) user.setId(rs.getInt(1));
	                }
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public User findByUsername(String username) {
	        String sql = "SELECT id,username,password,role,email FROM users WHERE username = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, username);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    User u = new User();
	                    u.setId(rs.getInt("id"));
	                    u.setUsername(rs.getString("username"));
	                    u.setPassword(rs.getString("password"));
	                    u.setRole(rs.getString("role"));
	                    u.setEmail(rs.getString("email"));
	                    return u;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public User findById(int id) {
	        String sql = "SELECT id,username,password,role,email FROM users WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    User u = new User();
	                    u.setId(rs.getInt("id"));
	                    u.setUsername(rs.getString("username"));
	                    u.setPassword(rs.getString("password"));
	                    u.setRole(rs.getString("role"));
	                    u.setEmail(rs.getString("email"));
	                    return u;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
	     * Simple authentication: matches stored password with provided.
	     * In production, use hashed passwords.
	     */
	    public User authenticate(String username, String password) {
	        User u = findByUsername(username);
	        if (u == null) return null;
	        if (u.getPassword() != null && u.getPassword().equals(password)) return u;
	        return null;
	    }

	    public boolean updateUser(User user) {
	        String sql = "UPDATE users SET username = ?, password = ?, role = ?, email = ? WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, user.getUsername());
	            ps.setString(2, user.getPassword());
	            ps.setString(3, user.getRole());
	            ps.setString(4, user.getEmail());
	            ps.setInt(5, user.getId());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    public boolean deleteUser(int id) {
	        String sql = "DELETE FROM users WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}



