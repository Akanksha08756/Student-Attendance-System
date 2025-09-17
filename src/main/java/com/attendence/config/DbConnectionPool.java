package com.attendence.config;
    import org.apache.commons.dbcp2.BasicDataSource;
	import javax.sql.DataSource;

	public class DbConnectionPool {
	    private static BasicDataSource dataSource;

	    static {
	        dataSource = new BasicDataSource();
	        dataSource.setUrl("jdbc:mysql://localhost:3306/attendance_db");
	        dataSource.setUsername("Developer");
	        dataSource.setPassword("Admin@9696");
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setInitialSize(5);
	        dataSource.setMaxTotal(20);
	    }

	    public static DataSource getDataSource() {
	        return dataSource;
	    }
	}



