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
 * Servlet implementation class Update
 */
@WebServlet("/UpdateResult")
@MultipartConfig(maxFileSize=1048576)  // 最大1M

public class UpdateResult extends HttpServlet {
	final File uploadDir =
			new File("C:\\pleiades\\workspace\\BulletinBoard\\WebContent\\upload");  // ファイル保存先


	public void init() throws ServletException {
		uploadDir.mkdir();
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String mal = request.getParameter("mal");
		String text = request.getParameter("text");

		String Cfile = request.getParameter("Cfile");


		int no2 = Integer.parseInt(no);

		ArrayList<BoardBean> Nfile = DaoText.selecttext(no2);

		BoardBean Nfile2 = Nfile.get(0);

		String Nfile3 = "C:/pleiades/workspace/BulletinBoard/WebContent/upload/"+Nfile2.getFile();
        File Ufile = new File(Nfile3);

        //deleteメソッドを使用してファイルを削除する
        Ufile.delete();

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

		BoardBean fr = null;


//		if(name != null && mal != null && text != null && Cfile.equals("未選択")){
//			fr = new BoardBean(no, name, mal, text, Cfile);
//		}
//
//		if(name != null && mal != null && text != null && file != null){
			fr = new BoardBean(no, name, mal, text, file, Cfile);
//		}



		DaoText.updatetext(fr);





		//フォワードする。
		String view = "/WEB-INF/view/result.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
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


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
