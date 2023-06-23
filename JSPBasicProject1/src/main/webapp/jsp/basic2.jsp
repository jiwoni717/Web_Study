<%--
		지시자
		1. page : JSP파일의 정보
		  1) info : 정보 → 코드 작성자 / 수정일...
		  2) contentType : 변환 → 브라우저에 실행 결과를 보여줌
		  3) import : 자바 라이브러리 읽기(사용자 정의 라이브러리도 가능)
		              java.lang.*, javax.servlet → 자동으로 추가되어있음
		  4) buffer : HTML을 출력할 메모리 공간
		  5) errorPage : 에러발생시에 이동하는 파일 지정
		  6) isErrorPage : 종류별 에러 처리
		2. taglib : 
		3. include :
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%!
	public int add(int a, int b)
	{
		return a+b;
	}

	int sum=0;
	int even=0;
	int odd=0;
%>
<%
	int a = 10;
	int b = 20;
	int c = add(a, b);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
		for(int i=0;i<=10;i++)
		{
			if(i%2==0)
			{
				even+=i;
			}
			else
				odd+=i;
			
			sum+=i;
		}
	%>
	<h3>1에서 100까지의 총 합 : <%=sum%></h3>
	<h3>짝수의 합 : <%=even%></h3>
	<h3>홀수의 합 : <%=odd%></h3>
</body>
</html>