package com.attendence.ui;
//    import com.attendence.Model.Attendence;
import com.attendence.Model.Student;
	import com.attendence.Model.User;
	import com.attendence.Service.AuthService;
	import com.attendence.Service.AttendanceService;
	import com.attendence.Service.StudentService;

	import java.sql.Date;
	import java.util.List;
	import java.util.Scanner;

	public class ConsoleUI {

	    private AuthService authService = new AuthService();
	    private StudentService studentService = new StudentService();
	    private AttendanceService attendanceService = new AttendanceService();
	    private  Scanner scanner = new Scanner(System.in);
	     public void start() {
	        while (true) {
	            System.out.println("\n1. Login\n2. Register\n3. Exit");
	            System.out.print("Choose: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consume newline

	            switch (choice) {
	                case 1:
	                    loginFlow();
	                    break;
	                case 2:
	                    registerFlow();
	                    break;
	                case 3:
	                    System.exit(0);
	            }
	        }
	    }

	    private void loginFlow() {
	        System.out.print("Username: ");
	        String username = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();

	        User user = authService.login(username, password);
	        if (user == null) {
	            System.out.println("Invalid credentials!");
	            return;
	        }

	        System.out.println("Welcome " + user.getUsername() + " (" + user.getRole() + ")");
	        if (user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("TEACHER")) {
	            teacherMenu();
	        } else {
	            studentMenu(user);
	        }
	    }

	    private void registerFlow() {
	        System.out.print("Username: ");
	        String username = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
	        System.out.print("Role (STUDENT/TEACHER/ADMIN): ");
	        String role = scanner.nextLine();

	        boolean success = authService.register(username, password, role, email);
	        if (success) System.out.println("User registered successfully!");
	        else System.out.println("Registration failed!");
	    }

	    private void teacherMenu() {
	        while (true) {
	            System.out.println("\n--- Teacher Menu ---");
	            System.out.println("1. Add Student");
	            System.out.println("2. Update Student");
	            System.out.println("3. Delete Student");
	            System.out.println("4. View All Students");
	            System.out.println("5. Mark Attendance");
	            System.out.println("6. View Attendance By Date");
	            System.out.println("7. Logout");
	            System.out.print("Choose: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();

	            switch (choice) {
	                case 1:
	                    System.out.print("Student Name: ");
	                    String name = scanner.nextLine();
	                    System.out.print("Roll No: ");
	                    String rollNo = scanner.nextLine();
	                    studentService.addStudent(name, rollNo);
	                    break;
	                case 2:
	                    System.out.print("Student ID: ");
	                    int updateId = scanner.nextInt(); scanner.nextLine();
	                    System.out.print("New Name: ");
	                    String newName = scanner.nextLine();
	                    System.out.print("New Roll No: ");
	                    String newRoll = scanner.nextLine();
	                    studentService.updateStudent(updateId, newName, newRoll);
	                    break;
	                case 3:
	                    System.out.print("Student ID: ");
	                    int deleteId = scanner.nextInt(); scanner.nextLine();
	                    studentService.deleteStudent(deleteId);
	                    break;
	                case 4:
	                    List<Student> students = studentService.findAll();
	                    for (Student s : students) {
	                        System.out.println(s.getId() + " | " + s.getName() + " | " + s.getRollNo());
	                    }
	                    break;
	                case 5:
	                    System.out.print("Student ID: ");
	                    int sid = scanner.nextInt(); scanner.nextLine();
	                    System.out.print("Status (Present/Absent): ");
	                    String status = scanner.nextLine();
	                    Date date = new Date(System.currentTimeMillis());
	                    attendanceService.markAttendance(sid, date, status);
	                    break;
	                case 6:
	                    Date viewDate = new Date(System.currentTimeMillis());
	                    List<com.attendence.Model.Attendence> atts = attendanceService.getAttendanceByDate(viewDate);
	                    for (com.attendence.Model.Attendence a : atts) {
	                        System.out.println(a.getStudentId() + " | " + a.getDate() + " | " + a.getStatus());
	                    }
	                    break;
	                case 7:
	                    return;
	            }
	        
	        }
	    }
	
	    

private   void studentMenu(User user) {
	System.out.println("student menu is not listed here ");
}
	}
	        



