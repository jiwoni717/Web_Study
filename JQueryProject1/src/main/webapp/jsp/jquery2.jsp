<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
/*
		문서 객체(태그) 조작
		text() : ┌getter : $(태그).text()
				 └setter : $(태그).text(값)
		val()
		attr() : 태그 속성에 값 첨부
		html() : innerHTML → set
		append() : 여러개 동시에 처리
		trim() : 공백 문자 제거
 */
 $(function(){
	$('img').attr("src","cgv1.jpg")
	$('input[type="text"]').val("admin")
	$('img').css({"width":"240px", "height":"320px"})
 })
</script>
</head>
<body>
	<img src="" width=240 height=320><br>
	<input type=text id="id" size="20">
</body>
</html>