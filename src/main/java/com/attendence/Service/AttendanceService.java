package com.attendence.Service;
import java.time.LocalDate;
import java.sql.Date;
import com.attendence.dao.AttendenceDao;
import com.attendence.Model.Attendence;
//import com.attendence.Model.Student;

import java.util.List;

	public class AttendanceService {
	    private AttendenceDao attendanceDao = new AttendenceDao();

	    public boolean markAttendance(Attendence a) {
	        return attendanceDao.markAttendance(a); 
    }
    public boolean markAttendance(int sid, Date date, String status) {
        Attendence attendance = new Attendence();
	        attendance.setStudentId(sid);
	        attendance.setDate(date);
	        attendance.setStatus(status);
	        
	        return  markAttendance(attendance); // existing method call
	    }
//

	    public List<Attendence> getAttendanceByStudent(int studentId) {
	        return attendanceDao.getByStudentId(studentId);
	    }

	    public List<Attendence> getAttendanceByDate(Date date) {
	        return attendanceDao.getByDate(date);
	    }
	    
	    
	    public List<Attendence> getAttendanceForClassOnDate(LocalDate localDate, String studentClass) {
	        Date sqlDate = Date.valueOf(localDate); // convert LocalDate → java.sql.Date
	        return attendanceDao.getByDateAndClass(sqlDate, studentClass);
	    }
	    
	    	
	    	
	    }
	
  
	    
	
	



