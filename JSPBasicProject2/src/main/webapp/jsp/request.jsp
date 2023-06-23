<%--
		내장객체
		  - request** → 각 JSP마다 따로 가지고 있다, 화면 변경되면 초기화됨
		  		1) 서버 정보와 클라이언트 브라우저 정보
		  		  getServerInfo()
		  		  getPort()
		  		  getMethod()
		  		  getProtocol()
		  		  getRequestURL()*
		  		  getRequestURI()*
		  		  getContextPath()*
		  		2) 사용자 요청 정보
		  		  단일 데이터 : getParameter()
		  		  다중 데이터 : getParameterValues() → 체크박스, 셀렉트 멀티라인...
		  		  한글 변환(디코딩) : setCharacterEncoding → UTF-8
		  		  키 읽기 : getParameterNames()
		  		3) 추가정보
		  		  setAttribute() → request 데이터 추가 전송
		  		  getAttribute() → 전송된 데이터 읽기
		  		4) 브라우저 정보
		  		  getRemoteAddr() : 접속자의 IP
		  		  getRequestURL(), getRequestURI(), getContextPath()
		  - response** : 헤더정보 / 응답정보
		  		1) 헤더정보
		  		  setHeader()
		  		2) 응답정보
		  		  HTML 전송 : text/html		┐
		  		  Cookie 전송 : addCookie()	┘둘이 동시에 못함
		  		3) 화면 이동
		  		  sendRedirect()**
		  - session** → HttpSession
		  - out*
		  - application* → ServletContext
		  - pageContext**
		  - page → object
		  - exception
		  - config → ServletConfig
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	.container {
		margin-top: 50px;
	}
	.row {
		margin: 0px auto;
		width: 1080px;
	}
	h2 {
		text-align: center
	}
</style>
</head>
<body>
	<div class="container">
	<h2>개인 정보</h2>
	
		<div class="row">
		<form method=post action="request_ok.jsp">
			<table>
				<tr>
					<th class="text-center" width=20%>이름</th>
					<td width=80%>
						<input type=text name=name size=15 class="input-sm">
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>성별</th>
					<td width=80%>
						<input type=radio name=sex value="남자" checked>남자
						<input type=radio name=sex value="여자" >여자
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>전화번호</th>
					<td width=80%>
						<select name=tel class="input-sm">
							<option>010</option>
						</select>-
						<input type=text name=tel2 size=6 class="input-sm">-
						<input type=text name=tel3 size=6 class="input-sm">
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>소개</th>
					<td width=80%>
						<textarea rows="8" cols="50" name=content></textarea>
					</td>
				</tr>
				<tr>
					<th class="text-center" width=20%>취미</th>
					<td width=80%>
						<input type=checkbox name=hobby value="운동">운동&nbsp;
						<input type=checkbox name=hobby value="등산">등산&nbsp;
						<input type=checkbox name=hobby value="낚시">낚시&nbsp;
						<input type=checkbox name=hobby value="게임">게임&nbsp;
						<input type=checkbox name=hobby value="영화감상">영화감상&nbsp;
						<input type=checkbox name=hobby value="여행">여행
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<button class="btn btn-sm btn-danger">전송</button>
					</td>
				</tr>
			</table>
		</form>
		</div>
		
	</div>
</body>
</html>