package com.attendence.Model;



	import java.sql.Date;

	public class Attendence {
	    private int id;
	    private int studentId;
	    private Date date;
	    private String status;
	    // Present / Absent / Leave
	    private int teacherId;   // new
	     private String remarks;

	    public Attendence() {}

	    public Attendence(int id, int studentId, Date date, String status) {
	        this.id = id;
	        this.studentId = studentId;
	        this.date = date;
	        this.status = status;
	    }

	    // Getters & Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public int getStudentId() { return studentId; }
	    public void setStudentId(int studentId) { this.studentId = studentId; }
	    public int getTeacherId() { return teacherId; }
	    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
	    public Date getDate() { return date; }
	    public void setDate(java.sql.Date date) { this.date = date; }
	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	    public String getRemarks() { return remarks; }
	    public void setRemarks(String remarks) { this.remarks = remarks; }
	}



