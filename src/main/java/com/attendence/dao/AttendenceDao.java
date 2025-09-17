package com.attendence.dao;
//import java.time.LocalDate;
import java.sql.Date;
import com.attendence.config.DbConnectionPool;
import com.attendence.Model.Attendence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
//import java.time.LocalDate;

	public class AttendenceDao {
		public boolean markAttendance(Attendence a) {
	        try {
	         Connection conn = DbConnectionPool.getDataSource().getConnection();
	            String sql = "INSERT INTO attendance (student_id, teacher_id, date, status, remarks) VALUES (?,?,?,?,?)";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, a.getStudentId());
	            ps.setInt(2, a.getTeacherId());
	            ps.setDate(3, new java.sql.Date(a.getDate().getTime())); // Date convert
	            ps.setString(4, a.getStatus());
	            ps.setString(5, a.getRemarks());
	          
	            ps.executeUpdate();  
	            System.out.println("mark attendence successfull");
	            return true;

	        } catch (SQLIntegrityConstraintViolationException e) {
	            
	            System.out.println("⚠ Attendance already marked for student ID " + a.getStudentId() + " on " + a.getDate());
	            return false;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	

	    public List<Attendence> getByStudentId(int studentId) {
	        List<Attendence> list = new ArrayList<>();
	        String sql = "SELECT id,student_id,date,status FROM attendance WHERE student_id = ? ORDER BY date DESC";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, studentId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Attendence a = new Attendence();
	                    a.setId(rs.getInt("id"));
	                    a.setStudentId(rs.getInt("student_id"));
	                    a.setDate(rs.getDate("date"));
	                    a.setStatus(rs.getString("status"));
	                    list.add(a);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    public List<Attendence> getByDate(Date date) {
	        List<Attendence> list = new ArrayList<>();
	        String sql = "SELECT id,student_id,date,status FROM attendance WHERE date = ? ORDER BY student_id";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setDate(1, date);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Attendence a = new Attendence();
	                    a.setId(rs.getInt("id"));
	                    a.setStudentId(rs.getInt("student_id"));
	                    a.setDate(rs.getDate("date"));
	                    a.setStatus(rs.getString("status"));
	                    list.add(a);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    /**
	     * Get attendance for students of a specific class on a date.
	     * Assumes students table has roll_no or class column if needed — adjust JOIN/WHERE accordingly.
	     * Example SQL commented out for reference.
	     */
	    public List<Attendence> getByDateAndClass(Date date, String studentClass) {
	        List<Attendence> list = new ArrayList<>();
	        String sql = "SELECT a.id, a.student_id, a.date, a.status " +
	                     "FROM attendance a JOIN students s ON a.student_id = s.id " +
	                     "WHERE a.date = ? AND s.student_class = ? ORDER BY s.roll_no";

	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setDate(1, date); // ✅ java.sql.Date
	            ps.setString(2, studentClass);

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Attendence a = new Attendence();
	                    a.setId(rs.getInt("id"));
	                    a.setStudentId(rs.getInt("student_id"));
	                    a.setDate(rs.getDate("date")); // java.sql.Date
	                    a.setStatus(rs.getString("status"));
	                    list.add(a);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	}
	        