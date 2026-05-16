package add_Friend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import com.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Add_Friend
 */
public class Add_Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Add_Friend() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/sh.html").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String to = request.getParameter("to");

		HttpSession session = request.getSession(true);
		String from = (String) session.getAttribute("email");

		response.setContentType("text/html;charset=UTF-8");

		String msg = "";

		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DBUtil.getConnection();

			Statement statement = conn.createStatement();

			String sql = "INSERT INTO `friend_v1` (`addfrom`, `accepto`, `status`, `updateTime`) "
					+ "SELECT '" + from + "', '" + to + "',1,CURRENT_TIMESTAMP()"
					+ " WHERE NOT EXISTS(SELECT 1 FROM `friend_v1` "
					+ "WHERE (`addfrom`='"+from+"' AND `accepto`='"+to+"')"
					+ " OR (`addfrom`='"+to+"' AND `accepto`='"+from+"'))";
			
			System.out.println(sql);

			//ResultSet resultSet = statement.executeQuery(s);
			int row=statement.executeUpdate(sql);
			
			if(row>0) {
				msg = "友達が正常に追加されました。" + "<a href=\"friends.html\">友達一覧を見る</a>";
			}else {
				msg="二人はすでに友達です。";
			}
			
			statement.close();
			conn.close();

			

		} catch (Exception e) {
			System.out.println(e);
			msg = "JDBCのロードに失敗した";
		}

		response.getWriter().append(msg);
	}

}
