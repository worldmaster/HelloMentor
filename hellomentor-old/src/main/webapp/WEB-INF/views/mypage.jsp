<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
<%--    <!-- bootstrap js: jquery load 이후에 작성할것.-->--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"--%>
<%--            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"--%>
<%--            crossorigin="anonymous"></script>--%>
<%--    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"--%>
<%--            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"--%>
<%--            crossorigin="anonymous"></script>--%>
<%--    <!-- bootstrap css -->--%>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"--%>
<%--          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">--%>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="content">
    <div class="my_content">
        <jsp:include page="mypage_left_menu.jsp"/>

        <div class="my_right_container">
            <div class="my_right_container2">
            가나다라마바사
            </div>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>