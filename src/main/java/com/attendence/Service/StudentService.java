package com.attendence.Service;
//import java.util.ArrayList;
import java.util.List;
import com.attendence.dao.StudentDao;
import com.attendence.Model.Student;


	public class StudentService {
	    private StudentDao studentDao = new StudentDao();
//	    private List<Student> studentList = new ArrayList<>();

	    public boolean addStudent(String name, String rollNo) {
	        Student s = new Student();
	        s.setName(name);
	        s.setRollNo(rollNo);
	        return studentDao.save(s);
	    }
	    public boolean addStudent(Student s) {
	        return studentDao.addStudent(s);
	    }
	    	
	        
	    public Student getStudentById(int id) {
	        return studentDao.getStudentById(id);
	    }

	    public boolean updateStudent(int id, String name, String rollNo) {
	        Student s = studentDao.findById(id);
	        if (s == null) return false;
	        s.setName(name);
	        s.setRollNo(rollNo);
	        return studentDao.update(s);
	    }

	    public boolean updateStudent(Student s) {
	        return studentDao.update(s);
	    }
	    public boolean deleteStudent(int id) {
	        return studentDao.delete(id);
	        }
	    

	    public List<Student> findAll() {
	        return studentDao.findAll();
	    }
	}



