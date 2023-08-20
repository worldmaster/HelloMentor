<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>

</head>
<body>
<div class="my_right_title">내 정보 수정</div>
<div class="my_editinfo_content1">

    <div class="my_editinfo_box_1">
        <div class="my_editinfo_profile_img">
            <img src="${pageContext.request.contextPath}/resources/img/profile-img.jpeg"/>
        </div>
        <span>프로필 사진 변경하기</span>
    </div>

    <div class="my_editinfo_box_2">
        <div class="my_editinfo_inputbox">
            <span>계정 ID</span>
            <input type="text" id="edit_info_userId" value="가나다라마바사">
        </div>
    </div>

    <div class="my_editinfo_box_2">
        <div class="my_editinfo_inputbox">
            <span>이름</span>
            <input type="text" id="edit_info_userName" value="임재린">
        </div>
    </div>

    <div class="my_editinfo_box_2">
        <div class="my_editinfo_inputbox">
            <span>현재 비밀번호</span>
            <input type="password" id="edit_info_userPwd" value="임재린">
        </div>
    </div>

    <div class="my_editinfo_box_3">
        <div class="my_inputbox_row">
            <span>변경할 비밀번호</span>
            <input type="password" id="edit_info_useroriginPwd" value="임재린">
        </div>
        <div class="my_inputbox_row" style="margin-left: 10%">
            <span>비밀번호 확인</span>
            <input type="password" id="edit_info_usernewPwd" value="임재린">
        </div>
    </div>

    <div class="my_editinfo_box_2">
        <div class="my_editinfo_inputbox">
            <span>가입 상태</span>
            <input type="text" id="edit_info_status" value="멘토">
        </div>
    </div>

    <div class="my_editinfo_box_2">
        <div class="my_editinfo_inputbox">
            <span>한 줄 소개</span>
            <input type="text" id="edit_info_intro" value="멘토">
        </div>
    </div>

    <div class="my_editinfo_content2">
        <div class="my_editinfo_btnbox1">
            <button id="my_editinfo_editBtn">수정</button>
            <button id="my_editinfo_cancelBtn">취소</button>
        </div>

        <div id="withdrawModal" class="withdrawmodal">
            <div class="width_draw_modal_content">
                <p>정말 탈퇴하시겠습니까?</p>
                <button id="withdrawyesButton">예</button>
                <button id="withdrawnoButton">아니오</button>
            </div>
        </div>

        <div class="my_editinfo_btnbox2">
            <button id="my_editinfo_withdrawBtn">탈퇴하기</button>
        </div>
    </div>










</div>



<script>
    document.getElementById('my_editinfo_withdrawBtn').addEventListener('click', function() {
        document.getElementById('withdrawModal').style.display = 'block';
    });

    document.getElementById('withdrawyesButton').addEventListener('click', function() {
        // 탈퇴 처리 코드
        document.getElementById('withdrawModal').style.display = 'none';
    });

    document.getElementById('withdrawnoButton').addEventListener('click', function() {
        document.getElementById('withdrawModal').style.display = 'none';
    });

</script>
</body>
</html>