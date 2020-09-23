package dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.BoardBean;

public class DaoText {

	//①DBアクセスに必要な情報の定数を定義

	//接続先DBのURL(jdbc:mysql://[ホスト名orIPアドレス]:[ポート番号]/[データベース名]?serverTimezone=JST)
	private static final String url = "jdbc:mysql://localhost:3306/java1?serverTimezone=JST";
	//ユーザ
	private static final String user = "root";
	//パスワード
	private static final String pw = "morijyobi";


	//INSERT文を実行するメソッドのサンプル
	//引数は登録したい情報が格納されたBean
	public static void inserttext(BoardBean s){
		//②アクセスに必要な変数の初期化
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			//③JDBCドライバをロードする
			Class.forName("com.mysql.cj.jdbc.Driver");

			//④データベースと接続する(コネクションを取ってくる)
			//第1引数→接続先URL
			//第2引数→ユーザ名
			//第3引数→パスワード
			con = DriverManager.getConnection(url, user, pw);

			//⑤SQL文の元を作成する
			//?をプレースホルダと言います。
			//後の手順で?に値を設定します。

			String sql = null;

			if(s.getFile() == null){
				sql = "INSERT INTO bulletinboard(name,mail,text) VALUES(?,?,?)";

			}else if(s.getFile() != null){
				sql = "INSERT INTO bulletinboard(name,mail,text,file) VALUES(?,?,?,?)";

			}

			//⑥SQLを実行するための準備(構文解析)
			pstmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

			//⑦プレースホルダに値を設定
			//第1引数→何番目の?に設定するか(1から数える)
			//第2引数→?に設定する値
			pstmt.setString(1, s.getName());
			pstmt.setString(2, s.getMail());
			pstmt.setString(3, s.getText());
			pstmt.setBlob(4, s.getFile());

			//⑧SQLを実行し、DBから結果を受領する
			int result = pstmt.executeUpdate();

			System.out.println(result + "件投稿されました。");


		}  catch (SQLException e){
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} finally {

			//⑨DBとの切断処理
			try {
				//nullかチェックしないとNullPointerExceptionが
				//発生してしまうためチェックしている。
				if( pstmt != null){
					pstmt.close();
				}
			} catch(SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}

			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
	}



	//全件検索するSELECT文を実行するメソッド
	public static ArrayList<BoardBean> selectAlltext(){
		//アクセスに必要な変数の初期化
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		byte[] file = null;

		try{
			//JDBCドライバをロードする
			Class.forName("com.mysql.cj.jdbc.Driver");

			//データベースと接続する(コネクションを取ってくる)
			//第1引数→接続先URL
			//第2引数→ユーザ名
			//第3引数→パスワード
			con = DriverManager.getConnection(url, user, pw);

			//SQL文の元を作成する
			String sql = "SELECT * FROM bulletinboard";

			//SQLを実行するための準備(構文解析)
			pstmt = con.prepareStatement(sql);

			//SQLを実行し、DBから結果を受領する
			rs = pstmt.executeQuery();

			//return用のArrayList生成
			ArrayList<BoardBean> list = new ArrayList<BoardBean>();

			//next()の戻り値がfalseになるまでResultSetから
			//データを取得してArrayListに追加していく
			while( rs.next() ){
				String no = rs.getString("no");
				String name = rs.getString("name");
				String mail = rs.getString("mail");
				String text = rs.getString("text");

				try {
					InputStream is = rs.getBinaryStream("file");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] bs = new byte[1024];
					int size = 0;


					while( ( size = is.read( bs ) ) != -1 ){
						baos.write( bs, 0, size );
					}

					file = baos.toByteArray();  //. byte[] 型に変換してデータを取得

				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					System.out.println("error");
				}


				String insert_time = rs.getString("insert_time");
				String updated_time = rs.getString("updated_time");

				BoardBean result = new BoardBean(no, name, mail, text, file, insert_time, updated_time);

				list.add(result);
			}

			//中身の詰まったArrayListを返却する
			return list;

		}  catch (SQLException e){
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} finally {
			//⑫DBとの切断処理
			try {
				//nullかチェックしないとNullPointerExceptionが
				//発生してしまうためチェックしている。
				if( pstmt != null){
					pstmt.close();
				}
			} catch(SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}

			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}
		}

		//途中でExceptionが発生した時はnullを返す。
			return null;
		}



	//条件に当てはまるもののみ検索するSELECT文を実行するメソッド
	//引数は検索する投稿のナンバー
	public static ArrayList<BoardBean> selecttext(int key){
		//②アクセスに必要な変数の初期化
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			//③JDBCドライバをロードする
			Class.forName("com.mysql.cj.jdbc.Driver");

			//④データベースと接続する(コネクションを取ってくる)
			//第1引数→接続先URL
			//第2引数→ユーザ名
			//第3引数→パスワード
			con = DriverManager.getConnection(url, user, pw);

			//⑤SQL文の元を作成する
			String sql = "SELECT * FROM bulletinboard WHERE no = ?";


			//⑥SQLを実行するための準備(構文解析)
			pstmt = con.prepareStatement(sql);

			//⑦プレースホルダに値を設定
			pstmt.setInt(1, key);

			//⑧SQLを実行し、DBから結果を受領する
			rs = pstmt.executeQuery();

			//return用のArrayList生成
			ArrayList<BoardBean> list = new ArrayList<BoardBean>();

			//next()の戻り値がfalseになるまでResultSetから
			//データを取得してArrayListに追加していく
			while( rs.next() ){
				String no = rs.getString("no");
				String name = rs.getString("name");
				String mail = rs.getString("mail");
				String text = rs.getString("text");
				InputStream file = (InputStream) rs.getBlob("file");
				String insert_time = rs.getString("insert_time");
				String updated_time = rs.getString("updated_time");

				BoardBean result = new BoardBean(no, name, mail, text, file, insert_time, updated_time);

				list.add(result);
			}

			//中身の詰まったArrayListを返却する
			return list;

		}  catch (SQLException e){
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} finally {
			//⑫DBとの切断処理
			try {
				//nullかチェックしないとNullPointerExceptionが
				//発生してしまうためチェックしている。
				if( pstmt != null){
					pstmt.close();
				}
			} catch(SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}

			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}
		}

		//途中でExceptionが発生した時はnullを返す。
		return null;
	}



	//DELETE文を実行するメソッド
	//引数は削除する投稿のID
	public static void deletetext(int no){
		//②アクセスに必要な変数の初期化
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			//③JDBCドライバをロードする
			Class.forName("com.mysql.cj.jdbc.Driver");

			//④データベースと接続する(コネクションを取ってくる)
			//第1引数→接続先URL
			//第2引数→ユーザ名
			//第3引数→パスワード
			con = DriverManager.getConnection(url, user, pw);

			//⑤SQL文の元を作成する
			//?をプレースホルダと言います。
			//後の手順で?に値を設定します。
			String sql = "DELETE FROM bulletinboard WHERE no = ?";

			//⑥SQLを実行するための準備(構文解析)
			pstmt = con.prepareStatement(sql);

			//⑦プレースホルダに値を設定
			//第1引数→何番目の?に設定するか(1から数える)
			//第2引数→?に設定する値
			pstmt.setInt(1, no);

			//⑧SQLを実行し、DBから結果を受領する
			int result = pstmt.executeUpdate();
			System.out.println(result + "件削除されました。");

		}  catch (SQLException e){
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} finally {
			//⑨DBとの切断処理
			try {
				//nullかチェックしないとNullPointerExceptionが
				//発生してしまうためチェックしている。
				if( pstmt != null){
					pstmt.close();
				}
			} catch(SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}

			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}
		}

	//auto_incrementの連番をリセットするsql
	try{
		//③JDBCドライバをロードする
		Class.forName("com.mysql.cj.jdbc.Driver");

		//④データベースと接続する(コネクションを取ってくる)
		//第1引数→接続先URL
		//第2引数→ユーザ名
		//第3引数→パスワード
		con = DriverManager.getConnection(url, user, pw);

		//⑤SQL文の元を作成する
		//?をプレースホルダと言います。
		//後の手順で?に値を設定します。
		String sql = "ALTER TABLE bulletinboard auto_increment = 1;";

		//⑥SQLを実行するための準備(構文解析)
		pstmt = con.prepareStatement(sql);

		//⑧SQLを実行し、DBから結果を受領する
		int result = pstmt.executeUpdate();

	}  catch (SQLException e){
		System.out.println("DBアクセスに失敗しました。");
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		System.out.println("DBアクセスに失敗しました。");
		e.printStackTrace();
	} finally {
		//⑨DBとの切断処理
		try {
			//nullかチェックしないとNullPointerExceptionが
			//発生してしまうためチェックしている。
			if( pstmt != null){
				pstmt.close();
			}
		} catch(SQLException e){
			System.out.println("DB切断時にエラーが発生しました。");
			e.printStackTrace();
		}

		try {
			if( con != null){
				con.close();
			}
		} catch (SQLException e){
			System.out.println("DB切断時にエラーが発生しました。");
			e.printStackTrace();
		}
	}
}



	//UPDATE文を実行するメソッド
	//引数は変更する投稿のID
	public static void updatetext(BoardBean n){
		//②アクセスに必要な変数の初期化
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			//③JDBCドライバをロードする
			Class.forName("com.mysql.cj.jdbc.Driver");

			//④データベースと接続する(コネクションを取ってくる)
			//第1引数→接続先URL
			//第2引数→ユーザ名
			//第3引数→パスワード
			con = DriverManager.getConnection(url, user, pw);

			//⑤SQL文の元を作成する
			//?をプレースホルダと言います。
			//後の手順で?に値を設定します。
			String sql = "UPDATE bulletinboard SET name = ?,mail = ?,text = ? WHERE no = ?";

			//⑥SQLを実行するための準備(構文解析)
			pstmt = con.prepareStatement(sql);

			//⑦プレースホルダに値を設定
			//第1引数→何番目の?に設定するか(1から数える)
			//第2引数→?に設定する値
			pstmt.setString(1, n.getName());
			pstmt.setString(2, n.getMail());
			pstmt.setString(3, n.getText());
			pstmt.setString(4, n.getNo());


			//⑧SQLを実行し、DBから結果を受領する
			int result = pstmt.executeUpdate();
			System.out.println(result + "件更新されました。");

		}  catch (SQLException e){
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("DBアクセスに失敗しました。");
			e.printStackTrace();
		} finally {
			//⑨DBとの切断処理
			try {
				//nullかチェックしないとNullPointerExceptionが
				//発生してしまうためチェックしている。
				if( pstmt != null){
					pstmt.close();
				}
			} catch(SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}

			try {
				if( con != null){
					con.close();
				}
			} catch (SQLException e){
				System.out.println("DB切断時にエラーが発生しました。");
				e.printStackTrace();
			}
		}
	}

}



