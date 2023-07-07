<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
	.row {
		margin: 0px auto;
		width: 860px;
	}
</style>
</head>
<body>
<div class="wrapper row3">
  	<main class="container clear">
		<div class="row">
			<table class="table">
				<tr>
					<td width=40% class="text-center" rowspan=7 style="vertical-align: middle">
						<img src="${vo.goods_poster }" style="width: 100%">
					</td>
					<td width=60%><h3>${vo.goods_name }</h3></td>
				</tr>
				<tr>
					<td width=60% class="text-center"><span style="color:gray">${vo.goods_sub }</span></td>
				</tr>
				<tr>
					<td class="inline"><b><span style="color:red">${vo.goods_discount }%</span></b>
						&nbsp;<h3>${vo.goods_price }</h3>
					</td>
				</tr>
				<tr>
					<td class="inline"><span style="color:green;font-size:8px">첫구매 할인가</span>&nbsp;<h3><span style="color:green">${vo.goods_first_price }</span></h3></td>
				</tr>
				<tr>
					<td width=60%>배송&nbsp;&nbsp;<span>${vo.goods_delivery }</span></td>
				</tr>
				<tr>
					<td class="inline">
						수량 : <select name="account" class="input-sm" style="width: 350px;">
							<c:forEach var="i" begin="1" end="${vo.account }">
								<option value="${i }">${i }개</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="inline text-center">
						<input type=button class="btn btn-lg btn-success" value="장바구니" style="width: 200px;">
						<input type=button class="btn btn-lg btn-default" value="바로구매" style="width: 200px;">
					</td>
				</tr>
			</table>
		</div>
  	</main>
</div>
</body>
</html>