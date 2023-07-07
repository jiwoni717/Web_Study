<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="wrapper row4">
  <footer id="footer" class="clear"> 
    <!-- ################################################################################################ -->
    <div class="one_third first">
      <h6 class="title">인기 맛집 Top 7</h6>
	      <ul class="nospace linklist">
	        <c:forEach var="fvo" items="${flist }">
	        	<li><a href="#">${fvo.name }&nbsp;(조회수 : ${fvo.hit })</a></li>
	        </c:forEach>
	      </ul>
    </div>
    <div class="one_third">
      <h6 class="title">인기 레시피 Top 7</h6>
	      <ul class="nospace linklist">
	        <li><a href="#">Home Page</a></li>
	        <li><a href="#">Blog</a></li>
	        <li><a href="#">Gallery</a></li>
	        <li><a href="#">Portfolio</a></li>
	        <li><a href="#">Contact Us</a></li>
	      </ul>
    </div>
    <div class="one_third">
      <h6 class="title">오늘의 뉴스 Top 7</h6>
	      <ul class="nospace linklist">
	        <li><a href="#">Home Page</a></li>
	        <li><a href="#">Blog</a></li>
	        <li><a href="#">Gallery</a></li>
	        <li><a href="#">Portfolio</a></li>
	        <li><a href="#">Contact Us</a></li>
	      </ul>
    </div>
    <!-- ################################################################################################ --> 
  </footer>
</div>

<div class="wrapper row5">
  <div id="copyright" class="clear"> 
    <!-- ################################################################################################ -->
    <p class="fl_left">Copyright &copy; 2023 - 쌍용강북교육센터 - <a href="#">D강의장</a></p>
    <p class="fl_right">Product by <a target="_blank" href="https://www.os-templates.com/" title="Free Website Templates">임지원</a></p>
    <!-- ################################################################################################ --> 
  </div>
</div>
</body>
</html>