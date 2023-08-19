<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<script>
    function loadPage(pageName) {
        $.get(pageName, function(data) {
            $('.my_right_container2').html(data);
        });
    }
</script>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <!-- bootstrap js: jquery load 이후에 작성할것.-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <!-- bootstrap css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage.css"/>
    <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/variable/pretendardvariable.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/my_left_menu.js"></script>
</head>
<body>


<div class="my_left_container">
    <div class="my_left_content1">
        <div class="my_left_content1-1">홈</div>
        <div class="my_left_content1-2" onclick="loadPage('${contextPath}/home_notification')">알림</div>
        <div class="my_left_content1-3" onclick="loadPage('${contextPath}/home_following_list')">팔로잉 목록</div>
    </div>

    <div class="my_left_content2">
        <div class="my_left_content2-1">프로필</div>
        <div class="my_left_content2-2" onclick="loadPage('${contextPath}/profile_edit_info')">내정보수정</div>
        <div class="my_left_content2-3" onclick="loadPage('${contextPath}/profile_my_post')">내가 쓴 글</div>
        <div class="my_left_content2-4" onclick="loadPage('${contextPath}/profile_my_reply')">내가 쓴 댓글</div>
    </div>

    <div class="my_left_content3">
        <div class="my_left_content3-1">개발도우미</div>
        <%--                <div class="my_left_content3-2">todoList</div>--%>
        <div class="my_left_content3-3" onclick="loadPage('${contextPath}/devhelper_calendar')">캘린더</div>
        <div class="my_left_content3-4" onclick="loadPage('${contextPath}/devhelper_codelab')">코드 실험실</div>
    </div>

    <div class="my_left_content4">
        <div class="my_left_content4-1">멘토링</div>
        <div class="my_left_content4-2" onclick="loadPage('${contextPath}/mentoring_mentor_applications')">멘토 / 멘티 신청목록</div>
        <div class="my_left_content4-3" onclick="loadPage('${contextPath}/mentoring_mentor_registdetail')">멘토 / 멘티 정보 등록 내역</div>
    </div>

    <div class="my_left_content5">
        <div class="my_left_content5-1">결제</div>
        <div class="my_left_content5-2" onclick="loadPage('${contextPath}/payment_payment_history')">결제 내역</div>
        <div class="my_left_content5-3" onclick="loadPage('${contextPath}/payment_exchange_history')">환전 내역</div>
    </div>

    <div class="my_left_content6">
        <div class="my_left_content6-1">설정</div>
        <div class="my_left_content6-2" onclick="loadPage('${contextPath}/settings_notification_preferences')">알림 설정</div>
    </div>
</div>


</body>
</html>


</html>