package my;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class My
 */
public class My extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public My() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/my.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);

		String email = (String) session.getAttribute("email");
		
		System.out.println(email);
		

		/*String url = "jdbc:mysql://localhost/wechat_v1";
		String user = "root";
		String passwordConnect = "123456";*/

		String msg = "";

		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");

			//Connection conn = DriverManager.getConnection(url, user, passwordConnect);
			Connection conn = DBUtil.getConnection();

			String sql = "select `email`,`password` from `user_v1` "
					+ "where email='" + email + "'";

			Statement createStatement = conn.createStatement();

			ResultSet resultSet = createStatement.executeQuery(sql);			
			
			String email1="";
			
			String password1="";

			while (resultSet.next()) {

				email1=resultSet.getString("email");
				
				password1=resultSet.getString("password");

			}
			
			if(email1==null) {
				
				msg="このメールアドレスが登録されない。";
				
			}
			
			response.getWriter().append(email1+"/"+password1);
			

			conn.close();
			

		} catch (Exception e) {

			e.printStackTrace();
			msg = "データベースのアクセスできない";

		}

		response.getWriter().append(msg);

	}

}
