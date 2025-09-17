package com.attendence.Model;

public class Student {
	
	    private int id;
	    private String name;
	    private String rollNo;
	    private String studentClass; // class field
	    private String email;

	    public Student() {}

	    public Student(int id, String name, String rollNo, String studentClass, String email) {
	        this.id = id;
	        this.name = name;
	        this.rollNo = rollNo;
	        this.studentClass = studentClass;
	        this.email = email;
	    }

	    // Getters & Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    public String getRollNo() { return rollNo; }
	    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
	    
	    public String getStudentClass() {
	        return studentClass;
	    }
	    public void setStudentClass(String studentClass) {
	        this.studentClass = studentClass;
	    }

	    public String getEmail() {
	        return email;
	    }
	    public void setEmail(String email) {
	        this.email = email;
	    }
	}

	



