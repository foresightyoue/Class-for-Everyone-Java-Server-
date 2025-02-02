package com.wanli.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilsUser {

	public static Connection getConnection() {
		//声明Connection对象
		Connection conn;
		//驱动程序名称
		String driverClass = "com.mysql.jdbc.Driver";
		//URL指向要访问的数据库名graduation_design
		String url = "jdbc:mysql:///graduation_user";
		//Mysql配置时的用户名
		String user = "root";
		//Mysql配置时的密码
		String password = "root";
		
		try {
			//加载驱动程序
			Class.forName(driverClass);
			//获取数据库连接
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "select * from userbean where name = ? and password = ?";
		Connection connection = DbUtilsScoreTab.getConnection();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "1");
			statement.setString(2, "1");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("有值");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
