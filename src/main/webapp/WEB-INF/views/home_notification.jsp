<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my_left_menu.js"></script>
</head>
<body>

<div class="my_right_title">알림</div>
<br>

<div class="my_notification">

    <div class="my_notifi_content1">
        <div class="my_notifi_content1-2">
            <select name="my_notifi_category" id="my_notifi_category">
                <option value="" selected>분류</option>
                <option value="가">가</option>
                <option value="나">나</option>
                <option value="다">다</option>
                <option value="라">라</option>
                <option value="마">마</option>
            </select>
            <span> 읽지 않은 알림 <span class="my_notifi_number">5</span>개</span>

        </div>
        <button type="button" id="my_notifi_button">모두 읽음 처리</button>
    </div>


    <div class="my_notifi_content2">
        <c:forEach var="i" begin="1" end="17">
            <div class="my_notifi_box">
                다른 사용자가 멘토링 신청을 했습니다.
            </div>
        </c:forEach>


    </div>


    <div class="my_notifi_content3">
        <div class="my_notifi_paging">
            <span id="previous-page" onclick="movePage(-1)">이전</span>
            <span id="page-numbers"></span>
            <span id="next-page" onclick="movePage(1)">다음</span>
        </div>
    </div>


</div>

</div>
</body>
</html>