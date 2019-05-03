<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title></title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

//검색과 page 둘 다 form  전송!!
function fncGetProductList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					<!--manage인 경우 상품관리 search인경우 상품목록조회 -->
					<c:if test="${param.menu=='manage'}" >
						상품관리
					</c:if>
					<c:if test="${param.menu=='search'}" >
						상품 목록조회
					</c:if>
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" 
						value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
								class="ct_input_g" style="width:200px; height:19px" >
			</td>

				<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
			


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	
	<c:set var = "i" value = "0"/>
	<c:forEach var ="product" items ="${list }">
		<c:set var="i"  value = "${i+1 }"/>
		<tr class="ct_list_pop">
			<td align="center">${i }</td>
			<td></td>
			<td align="left">
				<a href="/product/getProduct?prodNo=${product.prodNo}&menu=${param.menu}">${product.prodName }</a>
			</td>
			<td></td>
			<td align="left">${product.price }</td>
			<td></td>
			<td align="left">${product.regDate }</td>		
			<td></td>
			<td align="left">

				<c:if test="${empty product.proTranCode }">판매중</c:if>
				<c:if test="${! empty product.proTranCode && empty user }">재고없음</c:if>
				
				<c:if test="${! empty product.proTranCode && product.proTranCode=='1  '}">
					<c:if test="${user.role=='admin' && param.menu=='manage'}">
					구매완료<a href="/purchase/updateTranCodeByProd?menu=manage&prodNo=${product.prodNo}&tranCode=2"> 배송하기</a></c:if>
					<c:if test="${param.menu=='search' && ! empty user.role}">재고없음</c:if>
				</c:if>
				
				<c:if test="${! empty product.proTranCode && product.proTranCode=='2  '}">
					<c:if test="${user.role=='admin' && param.menu=='manage'}">배송중</c:if>
					<c:if test="${param.menu=='search' && ! empty user.role}">재고없음</c:if>
				</c:if>
				
				<c:if test="${! empty product.proTranCode && product.proTranCode=='3  '}">
					<c:if test="${user.role=='admin' && param.menu=='manage'}">배송완료</c:if>
					<c:if test="${param.menu=='search' && ! empty user.role}">재고없음</c:if>
				</c:if>

				
			</td>		
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<!--  페이지 Navigator 시작 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>
		 
		 	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
					◁ 이전
			</c:if>
			<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetProductList('${ resultPage.currentPage-1}')">◁ 이전</a>
			</c:if>
	
			<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
			<a href="javascript:fncGetProductList('${ i }');">${ i }</a>
			</c:forEach>
	
			<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▷
			</c:if>
			<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">이후 ▷</a>
			</c:if>


    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>
</div>
</body>
</html>
