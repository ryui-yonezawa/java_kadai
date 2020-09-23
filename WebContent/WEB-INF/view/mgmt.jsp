<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="bean.BoardBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理画面</title>

<link rel="stylesheet" href="./css/style.css">

</head>
<body>
<f1>


	<button><a href="/BulletinBoard/Main">TOP画面へ</a></button><br>

	<p>投稿内容をここに表示</p>

	<form method="post" id="post">
	<table border="1" align="center">


<%
	ArrayList<BoardBean> textList = (ArrayList<BoardBean>)request.getAttribute("list");

	if(textList != null){

		for(int i = 0 ; i < textList.size() ; i++){
			BoardBean text = textList.get(i);
%>


			<tr>
				<td class="text" colspan="100"><%=text.getText() %></td>
			</tr>

			<tr class="under">
				<td class="num"><%=text.getNo() %></td>
				<th>投稿者</th> <td><%=text.getName() %></td>
				<th>メールアドレス</th> <td><%=text.getMail() %></td>
				<th>投稿時間</th> <td><%=text.getInsert_time() %></td>
				<th>編集時間</th> <td><%=text.getUpdated_time() %></td>

				<td class="tbutton">
					<button type="submit" name="no" formaction="/BulletinBoard/Update" value="<%=text.getNo()%>">
						編集
					</button>

					<button type="submit" name="no" formaction="/BulletinBoard/Delete" value="<%=text.getNo()%>">
						削除
					</button>
				</td>

			</tr>

			<tr><td class="Unull" colspan="100"></td></tr>

			<tr><td class="null" colspan="100"></td></tr>

<%
		}
	}
%>

	</table>
	</form>

</f1>
</body>
</html>