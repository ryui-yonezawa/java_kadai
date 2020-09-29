package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
@MultipartConfig(maxFileSize=1048576)  // 最大1M

public class Post extends HttpServlet {
	final File uploadDir =
			new File("C:\\pleiades\\workspace\\BulletinBoard\\WebContent\\upload");  // ファイル保存先


	public void init() throws ServletException {
		uploadDir.mkdir();
	}

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String mal = request.getParameter("mal");
		String text = request.getParameter("text");

		String Cfile = request.getParameter("Cfile");

		String file = null;
		if (Cfile.equals("選択")){
		    // ファイルの保存 ->
		    Part fPart = request.getPart("file");
		    String fname = request.getParameter("fname");

		    file = (new StringBuilder(fname)
		    	      .append("_").append(System.currentTimeMillis())
		    	      .append("_").append(fPart.getSubmittedFileName()
		    	    ).toString());

		    save(fPart, new File(uploadDir, file));
		}

//		//ストリームの取得
//        InputStream file= null;
//
//        Part filePart = request.getPart("file");
//        if (filePart != null) {
//            file = filePart.getInputStream();
//        }


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

			ArrayList<BoardBean> flist = list;

			//リクエストスコープへ保存
			request.setAttribute("list",flist);

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


	  public void save(Part in, File out) throws IOException {
		    BufferedInputStream br
		      = new BufferedInputStream(in.getInputStream());
		    try (BufferedOutputStream bw =
		      new BufferedOutputStream(new FileOutputStream(out))
		    ) {
		      int len = 0;
		      byte[] buff = new byte[1024];
		      while ((len = br.read(buff)) != -1) {
		        bw.write(buff, 0, len);
		      }
		    }
		  }


//	public String getFileName(Part part) {
//        String pname = null;
//        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
//            if (dispotion.trim().startsWith("filename")) {
//                pname = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
//                pname = pname.substring(pname.lastIndexOf("\\") + 1);
//                break;
//            }
//        }
//        return pname;
//    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
