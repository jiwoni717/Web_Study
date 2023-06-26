<!-- 
		JQuery : 자바스크립트 라이브러리
		  자바스크립트는 시작과 동시에 처리 → window.onload=()=>{}
		  JQuery는 $(function(){})
		  1. 라이브러리 로드
		  	<script type="text/javascript" src="http://code.jquery.com/jquery.js"/>
		  	→ main.jsp에 자바스크립트 모아둬야함(충돌시 작동하지 않는다)
		  2. 문서 객체 선택 → selector
		  	 $('태그명') : 태그에 값이 여러개일 경우 배열로 들어온다
		  	 			 모든 태그에 이벤트 가능
		  	 			 선택된 태그 읽기 → $(this)
		  	 $('#아이디명')
		  	 $('.클래스명')
		  	 $('CSS style')
		  	 $('p + h1') / $('p ~ h1') / $('p > h1')~...도 가능
		  	 
		  	 
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$('div > h2+span').css("color","red")
	})
</script>
</head>
<body>
	<div>
		<h2>Hello Jquery</h2>
		<span>Hello Selector</span>
	</div>
</body>
</html>