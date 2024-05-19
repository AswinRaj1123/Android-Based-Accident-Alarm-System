package accident;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class LoginServlet extends HttpServlet {
	Connection con;
	public void init(ServletConfig config) throws ServletException {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			System.out.println(con);
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String s1 = req.getParameter("name");
			String s2 = req.getParameter("pword");
			System.out.println(con);
			PreparedStatement pstmt = con.prepareStatement("select * from userinfo where name=? and password=?");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(rs);
			PrintWriter pw = res.getWriter();
			System.out.println(rs.isAfterLast());
			System.out.println(rs.isBeforeFirst());
			System.out.println(rs.isFirst());
			if (rs.next()) {
				RequestDispatcher rd=req.getRequestDispatcher("Map.html");
				rd.forward(req, res);
			} else {
				RequestDispatcher rd=req.getRequestDispatcher("Login.html");
				rd.include(req, res);
				pw.println("<h3 align='right' style=color:red;margin-right:100px;>*Invalid username or password.</h3>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
