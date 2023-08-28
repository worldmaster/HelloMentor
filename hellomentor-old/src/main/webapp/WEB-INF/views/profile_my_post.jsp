<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<div class="my_right_title">내가 쓴 글</div>
<br>

<div class="my_notification">

    <div class="my_post_content1">
        <div class="my_post_content1-2">
            <select name="my_post_category" id="my_post_category">
                <option value="" selected>분류</option>
                <option value="가">가</option>
                <option value="나">나</option>
                <option value="다">다</option>
                <option value="라">라</option>
                <option value="마">마</option>
            </select>

        </div>
        <div>
            <span class="my_post_newfilter">최신순</span> ㅣ <span class="my_post_oldfilter">과거순</span>
        </div>

    </div>

    <div class="my_post_content2">
        <div>
            <span>게시판</span>
        </div>
        <div>
            <span>제목</span>
        </div>
        <div>
            <span>작성일자</span>
        </div>
    </div>


    <div class="my_post_content3">

        <c:forEach var="i" begin="1" end="17">
            <div class="my_post_box">
                <div>
                    <span>자유게시판</span>
                </div>
                <div>
                    <span>가나다라마바사아자차카</span>
                </div>
                <div>
                    <span>23.05.27</span>
                </div>
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

<script>
    // 알람 탭 페이징처리 구현

    // var currentPage = 1;
    // var itemsPerPage = 15;
    // var numberOfPages = Math.ceil(document.querySelectorAll('.my_notifi_box').length / itemsPerPage);
    //
    // function updatePage() {
    //     var start = (currentPage - 1) * itemsPerPage;
    //     var end = start + itemsPerPage;
    //
    //     document.querySelectorAll('.my_notifi_box').forEach((item, index) => {
    //         item.style.display = index >= start && index < end ? 'block' : 'none';
    //     });

    var currentPage = 1;
    var itemsPerPage = 15;
    var numberOfPages = Math.ceil(document.querySelectorAll('.my_post_box').length / itemsPerPage);

    function updatePage() {
        var start = (currentPage - 1) * itemsPerPage;
        var end = start + itemsPerPage;

        document.querySelectorAll('.my_post_box').forEach((item, index) => {
            item.style.display = index >= start && index < end ? 'flex' : 'none';
        });


        var pageNumberContainer = document.getElementById('page-numbers');
        pageNumberContainer.innerHTML = '';

        var startPage = currentPage - 5;
        var endPage = currentPage + 4;

        if (startPage < 1) {
            startPage = 1;
            endPage = Math.min(10, numberOfPages);
        }

        if (endPage > numberOfPages) {
            endPage = numberOfPages;
            startPage = Math.max(1, numberOfPages - 9);
        }

        for (var i = startPage; i <= endPage; i++) {
            var pageSpan = document.createElement('span');
            pageSpan.textContent = i;
            if (i === currentPage) {
                pageSpan.style.fontWeight = 'bold';
            } else {
                pageSpan.style.cursor = 'pointer';
                pageSpan.onclick = function () {
                    currentPage = parseInt(this.textContent);
                    updatePage();
                };
            }
            pageNumberContainer.appendChild(pageSpan);
        }

        document.getElementById('previous-page').style.display = currentPage === 1 ? 'none' : 'inline';

        document.getElementById('next-page').style.display = currentPage === numberOfPages ? 'none' : 'inline';
    }

    function movePage(direction) {
        currentPage += direction;

        if (currentPage < 1) currentPage = 1;
        if (currentPage > numberOfPages) currentPage = numberOfPages;

        updatePage();
    }

    updatePage();
</script>
</body>
</html>