<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="bean.BoardBean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新</title>

<link rel="stylesheet" href="./css/style.css">

</head>
<body>
<f1>
	<button><a href="/BulletinBoard/Main">TOP画面へ</a></button><br>
	<button><a href="/BulletinBoard/Mgmt">管理画面へ</a></button><br>


<%

	String no = null;

	ArrayList<BoardBean> List = (ArrayList<BoardBean>)request.getAttribute("list");

	if(List != null){

		for(int i = 0 ; i < List.size() ; i++){
			BoardBean text = List.get(i);

		no = text.getNo();

		}
	}
%>
	<form  action="/BulletinBoard/UpdateResult" method="post" id="post" enctype="multipart/form-data">
		<input type="hidden" name="no" value="<%=no %>">
        投稿者<br>
        <input type="text" style="width:200px;" name="name"><br>
        メールアドレス<br>
        <input type="text" style="width:200px;" name="mal"><br>
        内容<br>
        <textarea type="text" style="width:300px;height:100px;" name="text" wrap="hard"></textarea><br>

     	ファイル<br>
     	<input type="radio" id = "Cfile" name="Cfile" value="選択"
			onclick="document.getElementById('xyz').style.display = 'inline-block';"
			>ファイル選択

		<input type="radio" id = "Cfile" name="Cfile" value="未選択" checked="checked"
			onclick="document.getElementById('xyz').style.display = 'inline-block';"
			>ファイル未選択

        <br>
        <input type="text" style="width:200px;" name="fname">
        <input type="FILE" style="width:250px;" name="file"><br>

        <r><input type="submit" value="投稿"></r>
    </form>
    <br><br>


	<hr style="border-bottom:double">

    <p>投稿内容をここに表示</p>

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

<%			if(text.getFile() != null){
%>
			<tr>
				<td class="text" colspan="100"><%=text.getFile() %></td>
			</tr>
			<tr>
				<td class="text" colspan="100">
					<img src="./upload/<%=text.getFile() %>"
					alt="ファイル">
				</td>
			</tr>
<%			}
%>

			<tr class="under">
				<td class="num"><%=text.getNo() %></td>
				<th>投稿者</th> <td><%=text.getName() %></td>
				<th>メールアドレス</th> <td><%=text.getMail() %></td>
				<th>投稿時間</th> <td><%=text.getInsert_time() %></td>
				<th>編集時間</th> <td><%=text.getUpdated_time() %></td>
			</tr>

			<tr><td class="Unull" colspan="100"></td></tr>

			<tr><td class="null" colspan="100"></td></tr>

<%
		}
	}
%>

	</table>

</f1>
</body>
</html>