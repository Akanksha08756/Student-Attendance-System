package com.attendence.dao;



	import com.attendence.config.DbConnectionPool;
	import com.attendence.Model.Student;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;

	/**
	 * Student DAO assumes table students:
	 * id (AUTO_INCREMENT), name, roll_no
	 */
	public class StudentDao {

	    public boolean save(Student s) {
	        String sql = "INSERT INTO students(name, roll_no) VALUES(?,?)";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            ps.setString(1, s.getName());
	            ps.setString(2, s.getRollNo());

	            int affected = ps.executeUpdate();
	            if (affected > 0) {
	                try (ResultSet rs = ps.getGeneratedKeys()) {
	                    if (rs.next()) s.setId(rs.getInt(1));
	                }
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    public Student getStudentById(int id) {
	        String sql = "SELECT * FROM students WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                Student s = new Student();
	                s.setId(rs.getInt("id"));
	                s.setName(rs.getString("name"));
	                s.setRollNo(rs.getString("roll_no"));
	                s.setStudentClass(rs.getString("student_class"));
	                s.setEmail(rs.getString("email"));
	                return s;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    
	

	    public Student findById(int id) {
	        String sql = "SELECT id,name,roll_no FROM students WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Student s = new Student();
	                    s.setId(rs.getInt("id"));
	                    s.setName(rs.getString("name"));
	                    s.setRollNo(rs.getString("roll_no"));
	                    return s;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public Student findByRollNo(String rollNo) {
	        String sql = "SELECT id,name,roll_no FROM students WHERE roll_no = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, rollNo);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Student s = new Student();
	                    s.setId(rs.getInt("id"));
	                    s.setName(rs.getString("name"));
	                    s.setRollNo(rs.getString("roll_no"));
	                    return s;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public List<Student> findAll() {
	        List<Student> list = new ArrayList<>();
	        String sql = "SELECT id,name,roll_no FROM students ORDER BY name";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                Student s = new Student();
	                s.setId(rs.getInt("id"));
	                s.setName(rs.getString("name"));
	                s.setRollNo(rs.getString("roll_no"));
	                list.add(s);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    public boolean update(Student s) {
	        String sql = "UPDATE students SET name = ?, roll_no = ? WHERE id = ?";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setString(1, s.getName());
	            ps.setString(2, s.getRollNo());
	            ps.setInt(3, s.getId());
	            return ps.executeUpdate() > 0;
	           
        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
	    public boolean addStudent(Student s) {
	        String sql = "INSERT INTO students(name, roll_no, student_class, email) VALUES(?,?,?,?)";
	        try (Connection conn = DbConnectionPool.getDataSource().getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, s.getName());
	            ps.setString(2, s.getRollNo());
	            ps.setString(3, s.getStudentClass());
	            ps.setString(4, s.getEmail());
            ps.executeUpdate();
	            System.out.println("✅ Student added successfully!");
	            return true;

	        } catch (SQLIntegrityConstraintViolationException e) {
	            System.out.println("⚠ Student with Roll No " + s.getRollNo() + " already exists!");
	            return false;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        return false;
//	    }
	

	    public boolean delete(int id) {
	        String sql = "DELETE FROM students WHERE id = ?";
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


