package login;

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
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/login.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		//パラメータを取得
		String email = request.getParameter("email");
		
		String password = request.getParameter("password");

		//System.out.println(email + "|" + password);

		/*String url = "jdbc:mysql://localhost/wechat_v1";
		String user = "root";
		String passwordConnect = "123456";*/
		
		String msg="";

		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");

			//Connection conn = DriverManager.getConnection(url, user, passwordConnect);
			Connection conn = DBUtil.getConnection();

			String sql = "select `email`,`password` from `user_v1` "
					    + "where email='"+email+"' and password='"+password+"'";

			Statement createStatement = conn.createStatement();

			ResultSet resultSet = createStatement.executeQuery(sql);
			
			int count=0;
			
			while(resultSet.next()) {
				
				 count = count+1;
				
			}
			
			conn.close();
			
			if(count==0) {
				
				//
				msg = "メールかパスワードが正しくない。";
				
			}else {
				
				msg = "ログインに成功しました。" + "<a href='my.html'>マイページへ戻る</a>";
				
				HttpSession session = request.getSession(true);
				
				session.setAttribute("email", email);
				
			}			
			

		} catch (Exception e) {

			e.printStackTrace();
			msg = "データベースのアクセスできない";

		}
		
		response.getWriter().append(msg);
	}
	

}
