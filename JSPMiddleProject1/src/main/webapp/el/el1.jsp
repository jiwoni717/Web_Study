<%--
		EL(Expression Language) : 표현식
		${출력물} = <%= %>
		 ------
		 request에 있는 값, session, application, param...
		
		연산자
		  단항연산자는 존재하지 않음
		  이항연산자
		    1. 산술연산자 : +, -, *, /(div), %(mod) → +는 산술만 처리한다(문자열 결합은 +=)
		    2. 비교연산자 : ==(eq), !=(ne), <(lt), >(gt), <=(le), >=(ge) → 문자열이나 날짜 비교시에 동일하게 사용한다(오라클처럼)
		    3. 논리연산자 : &&(and), ||(or), !(not)
		    4. 삼항연산자 : 조건?값:값
		    5. 기타연산자 : Empty → null값이거나 공백인 경우 처리(사용빈도 낮음)
	
		EL에서 지원하는 내장객체
		  1. requestScope* → request.setAttribute()
		  2. sessionScope* → session.setAttribute()
		  3. param → request.getParameter()
		  4. paramValues → request.getParameterValues()
		  session보다 request가 우선순위가 높음
		  
		
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
<%--	<h2>산술연산자</h2>
	&#36;{10+10}=${10+10}<br>
	&#36;{"10"+10}=${"10"+10}<br>
	&#36;{"10"+10}=${"10"+=10}<br>
	&#36;{10/3}=${10/3 }<br>
	&#36;{10 div 3}=${10 div 3 }<br>
	&#36;{10%3}=${10%3 }<br>
	${"홍길동"=="심청이" }<br>
--%>
</body>
</html>