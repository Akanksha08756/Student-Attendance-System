package com.attendence.Service;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import com.attendence.dao.UserDao;
import com.attendence.Model.User;

	public class AuthService {
	    private UserDao userDao = new UserDao();
	    private Map<String, String> otpMap = new HashMap<>();

	    // Login
	    public User login(String username, String password) {
	        return userDao.authenticate(username, password);
	    }

	    // Register
	    public boolean register(String username, String password, String role, String email) {
	        if (userDao.findByUsername(username) != null) {
	            System.out.println("Username already exists!");
	            return false;
	        }
	        User user = new User();
	        user.setUsername(username);
	        user.setPassword(password); // Production: hash the password
	        user.setRole(role);
	        user.setEmail(email);
//	        return userDao.createUser(user);
	        
	        boolean created = userDao.createUser(user);
	        if (created) {
	            sendOtp(email); // generate + store OTP
	        }
	        return created;
	    }
	        
	        
	    

	    

	    // Generate and store OTP
	    private void sendOtp(String email) {
	        Random rnd = new Random();
	        String otp = String.format("%04d", rnd.nextInt(10000));
	        otpMap.put(email, otp);
	        System.out.println("OTP for " + email + " is: " + otp + " (For demo, print in console)");
	        // TODO: Send email via EmailService
	    }

	    // Verify OTP
	    public boolean verifyOtp(String email, String otp) {
	        if (otpMap.containsKey(email) && otpMap.get(email).equals(otp)) {
	            otpMap.remove(email); // OTP used
	            return true;
	        }
	        return false;
	    }
	
	    }
	



