// 좌측 메뉴바 클릭 이벤트 구현
document.addEventListener("DOMContentLoaded", function() {
    var menuItems = document.querySelectorAll(".my_left_container [class$='-2'], .my_left_container [class$='-3'], .my_left_container [class$='-4'], .my_left_container [class$='-5']");

    menuItems.forEach(function(item) {
        item.addEventListener("click", function() {
            menuItems.forEach(function(innerItem) {
                innerItem.classList.remove("active");
            });

            this.classList.add("active");
        });
    });
});



// 알람 탭 페이징처리 구현

var currentPage = 1;
var itemsPerPage = 15;
var numberOfPages = Math.ceil(document.querySelectorAll('.my_notifi_box').length / itemsPerPage);

function updatePage() {
    var start = (currentPage - 1) * itemsPerPage;
    var end = start + itemsPerPage;

    document.querySelectorAll('.my_notifi_box').forEach((item, index) => {
        item.style.display = index >= start && index < end ? 'block' : 'none';
    });

    var pageNumberContainer = document.getElementById('page-numbers');
    pageNumberContainer.innerHTML = ''; // 이전 페이지 번호 제거

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

    // "이전" 버튼을 첫 페이지에서 숨기기
    document.getElementById('previous-page').style.display = currentPage === 1 ? 'none' : 'inline';

    // "다음" 버튼을 마지막 페이지에서 숨기기
    document.getElementById('next-page').style.display = currentPage === numberOfPages ? 'none' : 'inline';
}

function movePage(direction) {
    currentPage += direction;

    if (currentPage < 1) currentPage = 1;
    if (currentPage > numberOfPages) currentPage = numberOfPages;

    updatePage();
}
updatePage();


