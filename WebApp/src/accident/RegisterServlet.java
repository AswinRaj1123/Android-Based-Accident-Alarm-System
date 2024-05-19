package accident;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class RegisterServlet extends HttpServlet {
	Connection con;
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			long phone = Long.parseLong(req.getParameter("phone"));
			String pwd = req.getParameter("password");
			String dob = req.getParameter("dob");
			String bg = req.getParameter("bldgrp");
			PreparedStatement pstmt = con.prepareStatement("insert into userinfo values(?,?,?,?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setLong(3, phone);
			pstmt.setString(4, pwd);
			pstmt.setString(5, dob);
			pstmt.setString(6, bg);
			pstmt.executeQuery();
			res.sendRedirect("Login.html");
			System.out.println(pstmt);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
}
