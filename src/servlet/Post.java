package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.BoardBean;
import dao.DaoText;

/**
 * Servlet implementation class Post
 */
@WebServlet("/Post")
@MultipartConfig(maxFileSize=1048576)
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String mal = request.getParameter("mal");
		String text = request.getParameter("text");

		//ストリームの取得
        InputStream file= null;

        Part filePart = request.getPart("file");
        if (filePart != null) {
            file = filePart.getInputStream();
        }


		BoardBean fr = null;

		if(name != null && mal != null && text != null && file == null){
			fr = new BoardBean(name, mal, text);
		}

		if(name != null && mal != null && text != null && file != null){
			fr = new BoardBean(name, mal, text, file);
		}


		if(fr == null){

			//今までの掲示板情報を取得
			ArrayList<BoardBean> list = DaoText.selectAlltext();

			//リクエストスコープへ保存
			request.setAttribute("list",list);

			//投稿画面へフォワードする。
			String view = "/WEB-INF/view/post.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);

		}else{

			//入力内容をインサート
			DaoText.inserttext(fr);

			//投稿画面へフォワードする。
			String view = "/WEB-INF/view/result.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}


	public String getFileName(Part part) {
        String pname = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                pname = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                pname = pname.substring(pname.lastIndexOf("\\") + 1);
                break;
            }
        }
        return pname;
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
