package com.attendence;

import com.attendence.Model.Attendence;
import com.attendence.Model.Student;
import com.attendence.Model.User;
import com.attendence.Service.AuthService;
import com.attendence.Service.AttendanceService;
import com.attendence.Service.StudentService;
import com.attendence.ui.ConsoleUI;
import com.attendence.utill.ReportExporter;

import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
public class App{
private static AuthService auth;
private static StudentService studentService;
private static AttendanceService attendanceService;
private static final Scanner sc = new Scanner(System.in);

    public static void main( String[] args ) {
    new ConsoleUI().start();
    
    
    	 auth = new AuthService();
         studentService = new StudentService();
         attendanceService = new AttendanceService();

         System.out.println("=== Student Attendance System (CLI) ===");
         while (true) {
             System.out.println("\nMain Menu:");
             System.out.println("1) Register");
             System.out.println("2) Verify OTP");
             System.out.println("3) Login");
             System.out.println("4) Exit");
             System.out.print("Choose: ");
             String c = sc.nextLine().trim();
             if (c.equals("1")) registerFlow();
             else if (c.equals("2")) verifyFlow();
             else if (c.equals("3")) loginFlow();
             else if (c.equals("4")) break;
             else System.out.println("Invalid choice.");
         }
         System.out.println("Bye");
         sc.close();
     }

     private static void registerFlow() {
         try {
             System.out.print("Email: ");
             String email = sc.nextLine().trim();
             System.out.print("Password: ");
             String pwd = sc.nextLine().trim();
             System.out.print("Name: ");
             String name = sc.nextLine().trim();
             System.out.print("Role (ADMIN/TEACHER/STUDENT): ");
             String role = sc.nextLine().trim().toUpperCase();
             if (!role.equals("ADMIN") && !role.equals("TEACHER") && !role.equals("STUDENT")) {
                 System.out.println("Invalid role — using STUDENT by default.");
                 role = "STUDENT";
             }
             boolean ok = auth.register(email, pwd, name, role);
             System.out.println(ok ? "Registered. Check email for OTP." : "Registration failed (maybe email exists).");
         } catch (Exception e) {
             System.out.println("Error during registration: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void verifyFlow() {
         System.out.print("Email: ");
         String email = sc.nextLine().trim();
         System.out.print("OTP: ");
         String otp = sc.nextLine().trim();
         boolean ok = auth.verifyOtp(email, otp);
         System.out.println(ok ? "OTP Verified" : "Invalid OTP or expired");
     }

     private static void loginFlow() {
         System.out.print("Email: ");
         String email = sc.nextLine().trim();
         System.out.print("Password: ");
         String pwd = sc.nextLine().trim();
         User u = auth.login(email, pwd);
         if (u == null) {
             System.out.println("Login failed (wrong credentials).");
             return;
         }
         System.out.println("Hello, " + u.getUsername() + " (" + u.getRole() + ")");
         switch (u.getRole()) {
             case "ADMIN": adminMenu(u); break;
             case "TEACHER": teacherMenu(u); break;
             default: studentMenu(u); break;
         }
     }

     /* -------------------- Admin Menu -------------------- */
     private static void adminMenu(User u) {
         while (true) {
             System.out.println("\n--- Admin Menu ---");
             System.out.println("1) Add Student");
             System.out.println("2) Update Student");
             System.out.println("3) Delete Student");
             System.out.println("4) List Students");
             System.out.println("5) Export Attendance Report (Class & Date range)");
             System.out.println("6) Logout");
             System.out.print("Choose: ");
             String c = sc.nextLine().trim();
             if (c.equals("1")) addStudent();
             else if (c.equals("2")) updateStudent();
             else if (c.equals("3")) deleteStudent();
             else if (c.equals("4")) listStudents();
             else if (c.equals("5")) exportReportAdmin();
             else if (c.equals("6")) break;
             else System.out.println("Invalid choice.");
         }
     }

     private static void addStudent() {
         try {
             System.out.print("Roll No: ");
             String roll = sc.nextLine().trim();
             System.out.print("Name: ");
             String name = sc.nextLine().trim();
             System.out.print("Class (e.g., 10A): ");
             String cls = sc.nextLine().trim();
             System.out.print("Email: ");
             String email = sc.nextLine().trim();
             Student s = new Student();
             s.setRollNo(roll);
             s.setName(name);
             s.setStudentClass(cls);
             s.setEmail(email);
             boolean ok = studentService.addStudent(s);
             
         
             System.out.println(ok ? "Student added, id=" + s.getId() : "Failed to add student");
         } catch (Exception e) {
             System.out.println("Error adding student: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void updateStudent() {
         try {
             System.out.print("Student ID to update: ");
             int id = Integer.parseInt(sc.nextLine().trim());
             Student s = studentService.getStudentById(id);
             if (s == null) { System.out.println("Student not found."); return; }
             System.out.println("Leave blank to keep existing value.");
             System.out.print("Roll No (" + s.getRollNo() + "): ");
             String roll = sc.nextLine().trim();
             if (!roll.isEmpty()) s.setRollNo(roll);
             System.out.print("Name (" + s.getName() + "): ");
             String name = sc.nextLine().trim();
             if (!name.isEmpty()) s.setName(name);
             System.out.print("Class (" + s.getStudentClass() + "): ");
             String cls = sc.nextLine().trim();
             if (!cls.isEmpty()) s.setStudentClass(cls);
             System.out.print("Email (" + s.getEmail() + "): ");
             String email = sc.nextLine().trim();
             if (!email.isEmpty()) s.setEmail(email);
             boolean ok = studentService.updateStudent(s);
             System.out.println(ok ? "Student updated" : "Update failed");
         } catch (NumberFormatException nfe) {
             System.out.println("Invalid id format.");
         } catch (Exception e) {
             System.out.println("Error updating student: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void deleteStudent() {
         try {
             System.out.print("Student ID to delete: ");
             int id = Integer.parseInt(sc.nextLine().trim());
             boolean ok = studentService.deleteStudent(id);
             System.out.println(ok ? "Student deleted" : "Delete failed (maybe not found)");
         } catch (NumberFormatException nfe) {
             System.out.println("Invalid id format.");
         } catch (Exception e) {
             System.out.println("Error deleting student: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void listStudents() {
         try {
             List<Student> list = studentService.findAll();
             System.out.println("\nStudents:");
             System.out.printf("%-5s %-10s %-20s %-10s %-25s%n", "ID", "Roll", "Name", "Class", "Email");
             for (Student s : list) {
                 System.out.printf("%-5d %-10s %-20s %-10s %-25s%n",
                         s.getId(), s.getRollNo(), s.getName(), s.getStudentClass(), s.getEmail());
             }
             if (list.isEmpty()) System.out.println("No students found.");
         } catch (Exception e) {
             System.out.println("Error listing students: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void exportReportAdmin() {
         try {
             System.out.print("Enter class (e.g., 10A): ");
             String cls = sc.nextLine().trim();
             System.out.print("From date (yyyy-MM-dd): ");
             LocalDate from = LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ISO_DATE);
             System.out.print("To date (yyyy-MM-dd): ");
             LocalDate to = LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ISO_DATE);

             // gather attendance for each date in range for the class
             List<Attendence> all = new java.util.ArrayList<>();
             LocalDate d = from;
             while (!d.isAfter(to)) {
                 List<Attendence> day = attendanceService.getAttendanceForClassOnDate(d,cls);
                 all.addAll(day);
                 d = d.plusDays(1);
             }
             if (all.isEmpty()) {
                 System.out.println("No attendance records for the given range/class.");
                 return;
             }
             System.out.print("Export format (1=CSV,2=XLSX): ");
             String fmt = sc.nextLine().trim();
             System.out.print("Output path (e.g., /tmp/report.xlsx or report.csv): ");
             String path = sc.nextLine().trim();
             if (fmt.equals("1")) ReportExporter.exportToCsv(all, path);
             else ReportExporter.exportToExcel(all, path);
             System.out.println("Exported " + all.size() + " rows to " + path);
         } catch (Exception e) {
             System.out.println("Error exporting report: " + e.getMessage());
             e.printStackTrace();
         }
     }

     /* -------------------- Teacher Menu -------------------- */
     private static void teacherMenu(User u) {
         while (true) {
             System.out.println("\n--- Teacher Menu ---");
             System.out.println("1) Mark Attendance");
             System.out.println("2) View Class Attendance for Date");
             System.out.println("3) Export Class Report (Date)");
             System.out.println("4) Logout");
             System.out.print("Choose: ");
             String c = sc.nextLine().trim();
             if (c.equals("1")) markAttendance(u);
             else if (c.equals("2")) viewClassAttendance();
             else if (c.equals("3")) exportReportTeacher();
             else if (c.equals("4")) break;
             else System.out.println("Invalid choice.");
         }
     }
     private static void markAttendance(User teacher) {
         try {
             System.out.print("Class (e.g., 10A): ");
             String cls = sc.nextLine().trim();
             List<Student> students = studentService.findAll();
             // filter students by class
             List<Student> classStudents = new java.util.ArrayList<>();
             for (Student s : students) if (cls.equals(s.getStudentClass())) classStudents.add(s);

             if (classStudents.isEmpty()) {
                 System.out.println("No students found for class " + cls);
                 return;
             }

             LocalDate localDate = LocalDate.now();
             System.out.println("Marking attendance for date: " + localDate);
             for (Student s : classStudents) {
                 System.out.printf("Roll: %s, Name: %s  -> (P/A/L): ", s.getRollNo(), s.getName());
                 String st = sc.nextLine().trim().toUpperCase();
                 if (!st.equals("P") && !st.equals("A") && !st.equals("L")) {
                     System.out.println("Invalid input, defaulting to A (Absent).");
                     st = "A";
                 }
                 Attendence a = new Attendence();
                 a.setStudentId(s.getId());
                 a.setTeacherId(teacher.getId());
                 a.setDate(Date.valueOf(localDate));
                 a.setStatus(st.equals("P") ? "PRESENT" : st.equals("L") ? "LATE" : "ABSENT");
                 a.setRemarks(null);
                 boolean ok = attendanceService.markAttendance(a);
                 if (!ok) System.out.println("Failed to save for student id " + s.getId());
             }
             System.out.println("Attendance marking completed for class " + cls);
         } catch (Exception e) {
             System.out.println("Error marking attendance: " + e.getMessage());
             e.printStackTrace();
         }
     }




         private static void viewClassAttendance() {
         try {
             System.out.print("Class (e.g., 10A): ");
             String cls = sc.nextLine().trim();
             System.out.print("Date (yyyy-MM-dd): ");
             LocalDate date = LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ISO_DATE);
             List<Attendence> list = attendanceService.getAttendanceForClassOnDate(date, cls);
             if (list.isEmpty()){
                 System.out.println("No records found for class " + cls + " on " + date);
                 return;
             }
             System.out.printf("%-5s %-10s %-10s %-10s %-20s%n", "ID", "StudID", "Teacher", "Date", "Status");
             for (Attendence a : list) {
                 System.out.printf("%-5d %-10d %-10d %-10s %-20s%n", a.getId(), a.getStudentId(), a.getTeacherId(), a.getDate(), a.getStatus());
             }
         } catch (Exception e) {
             System.out.println("Error viewing class attendance: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void exportReportTeacher() {
         try {
             System.out.print("Class (e.g., 10A): ");
             String cls = sc.nextLine().trim();
             System.out.print("Date (yyyy-MM-dd): ");
             LocalDate date = LocalDate.parse(sc.nextLine().trim(), DateTimeFormatter.ISO_DATE);
             List<Attendence> list = attendanceService.getAttendanceForClassOnDate(date, cls);
             if (list.isEmpty()) {
                 System.out.println("No records to export.");
                 return;
             }
             System.out.print("Export format (1=CSV,2=XLSX): ");
             String fmt = sc.nextLine().trim();
             System.out.print("Output path: ");
             String path = sc.nextLine().trim();
             if (fmt.equals("1")) ReportExporter.exportToCsv(list, path);
             else ReportExporter.exportToExcel(list, path);
             System.out.println("Exported " + list.size() + " rows to " + path);
         } catch (Exception e) {
             System.out.println("Error exporting: " + e.getMessage());
             e.printStackTrace();
         }
     }


     /* -------------------- Student Menu -------------------- */
     private static void studentMenu(User u) {
         while (true) {
             System.out.println("\n--- Student Menu ---");
             System.out.println("1) View My Attendance");
             System.out.println("2) Export My Attendance");
             System.out.println("3) Logout");
             System.out.print("Choose: ");
             String c = sc.nextLine().trim();
             if (c.equals("1")) viewMyAttendance(u);
             else if (c.equals("2")) exportMyAttendance(u);
             else if (c.equals("3")) break;
             else System.out.println("Invalid choice.");
         }
     }

     private static void viewMyAttendance(User u) {
         try {
             // find student by matching user email with student email (simple approach)
             // If your system links users and students separately, update this lookup accordingly.
             List<Student> students = studentService.findAll();
             Student me = null;
             for (Student s : students) {
                 if (s.getEmail() != null && s.getEmail().equalsIgnoreCase(u.getEmail())) {
                     me = s;
                     break;
                 }
             }
             if (me == null) {
                 System.out.println("No student profile linked to this user email.");
                 return;
             }
             List<Attendence> list = attendanceService.getAttendanceByStudent(me.getId());
             if (list.isEmpty()) {
                 System.out.println("No attendance records found.");
                 return;
             }
             System.out.printf("%-5s %-12s %-10s %-10s%n", "ID", "Date", "Status", "Remarks");
             for (Attendence a : list) {
                 System.out.printf("%-5d %-12s %-10s %-10s%n", a.getId(), a.getDate(), a.getStatus(), a.getRemarks() == null ? "" : a.getRemarks());
             }
         } catch (Exception e) {
             System.out.println("Error viewing attendance: " + e.getMessage());
             e.printStackTrace();
         }
     }

     private static void exportMyAttendance(User u) {
         try {
             List<Student> students = studentService.findAll();
             Student me = null;
             for (Student s : students) {
                 if (s.getEmail() != null && s.getEmail().equalsIgnoreCase(u.getEmail())) {
                     me = s;
                     break;
                 }
             }
             if (me == null) {
                 System.out.println("No student profile linked to this user email.");
                 return;
             }
             List<Attendence> list = attendanceService.getAttendanceByStudent(me.getId());
             if (list.isEmpty()) {
                 System.out.println("No records to export.");
                 return;
             }
             System.out.print("Export format (1=CSV,2=XLSX): ");
             String fmt = sc.nextLine().trim();
             System.out.print("Output path: ");
             String path = sc.nextLine().trim();
             if (fmt.equals("1")) ReportExporter.exportToCsv(list, path);
             else ReportExporter.exportToExcel(list, path);
             System.out.println("Exported " + list.size() + " rows to " + path);
         } catch (Exception e) {
             System.out.println("Error exporting attendance: " + e.getMessage());
             e.printStackTrace();
         }
     }
 }
