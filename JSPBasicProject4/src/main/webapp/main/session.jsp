<%--
		session
		  String getId() : 세션 저장 구분자
		  setMaxinactiveInterval() : 저장 기간 설정(디폴트 값은 1800초 = 30분) → 0이나 -1을 주면 유효시간이 무한이 됨, invalidate() 메소드를 통해 명시적으로 종료해주지 않으면 계속 유지됨
		  isNew() : ID가 할당이 된 것인지 여부 확인 → 장바구니
		  invalidate() : 세션에 저장된 모든 내용을 지운다
		  setAttribute() : 세션에 정보 저장
		  getAttribute() : 저장된 정보 읽어옴
		  removeAttribute() : 저장된 데이터 일부를 지울 때 사용
		  
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>session</h2>
</body>
</html>