<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
        var today = new Date();
        var selectedCell = null;

        function buildCalendar() {

            var dateElement = document.createElement("input");
            dateElement.setAttribute("type", "text");
            dateElement.setAttribute("id", "date");
            // document.body.appendChild(dateElement);

            var row = null
            var row1 = null
            var cnt = 0;
            var calendarTable = document.getElementById("calendar");
            var calendarTableTitle = document.getElementById("calendarTitle");
            calendarTableTitle.innerHTML = today.getFullYear() + "년" + (today.getMonth() + 1) + "월";

            var firstDate = new Date(today.getFullYear(), today.getMonth(), 1);
            var lastDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);
            while (calendarTable.rows.length > 2) {
                calendarTable.deleteRow(calendarTable.rows.length - 1);
            }

            row = calendarTable.insertRow();
            for (i = 0; i < firstDate.getDay(); i++) {
                cell = row.insertCell();
                cnt += 1;
            }

            row1 = calendarTable.insertRow();

            for (i = 1; i <= lastDate.getDate(); i++) {
                cell = row.insertCell();
                cnt += 1;

                cell.classList.add("product-calendar-cell");
                cell.setAttribute('id', i);
                cell.innerHTML = i;
                cell.align = "center";

                // cell.onclick----------------------------------------------------------------------------------------------------------
                cell.onclick = function () {
                    if (selectedCell) {
                        selectedCell.classList.remove("product-calendar-selected");
                    }
                    selectedCell = this;
                    selectedCell.classList.add("product-calendar-selected");
                    this.classList.remove("product-calendar-hover");

                    clickedYear = today.getFullYear();
                    clickedMonth = (1 + today.getMonth());
                    clickedDate = this.getAttribute('id');

                    clickedDate = clickedDate >= 10 ? clickedDate : '0' + clickedDate;
                    clickedMonth = clickedMonth >= 10 ? clickedMonth : '0' + clickedMonth;
                    selectedDate = clickedYear.toString().substring(2) + "/" + clickedMonth + "/" + clickedDate;

                    document.getElementById("selectedDate").innerText = selectedDate;


                    // document.getElementById("date").value = selectedDate;
                    showMemoModal();
                }


                if (cnt % 7 == 1) {
                    cell.innerHTML = "<font color=#FF0000>" + i + "</font>";
                }

                if (cnt % 7 == 0) {
                    cell.innerHTML = "<font color=#0055FF>" + i + "</font>";
                    row = calendar.insertRow();
                }
            }

            if (cnt % 7 != 0) {
                for (i = 0; i < 7 - (cnt % 7); i++) {
                    cell = row.insertCell();
                }
            }


        }

        function prevCalendar() {
            today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
            buildCalendar();
        }

        function nextCalendar() {
            today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
            buildCalendar();
        }
    </script>
</head>
<body>
<div class="my_right_title">캘린더</div>
<br>
<!-- 일정선택 박스 -->

    <!-- 달력 -->
    <div class="product-calendar-box">
        <table id="calendar" align="center">
            <tr>
                <td align="center" class="product-calendar"><label onclick="prevCalendar()"> ◀
                </label></td>
                <td colspan="5" align="center" id="calendarTitle">yyyy년 m월</td>
                <td align="center" class="product-calendar"><label onclick="nextCalendar()"> ▶
                </label></td>
            </tr>

            <tr style="border-bottom: 1px dashed black">
                <td align="center" class="product-calendar">
                    <font color="#FF0000">일 </font>
                </td>
                <td align="center" class="product-calendar">월</td>
                <td align="center" class="product-calendar">화</td>
                <td align="center" class="product-calendar">수</td>
                <td align="center" class="product-calendar">목</td>
                <td align="center" class="product-calendar">금</td>
                <td align="center" class="product-calendar">
                    <font color="#0055FF">토 </font>
                </td>
            </tr>
            <script type="text/javascript">buildCalendar();</script>
        </table>
    </div>


<div id="memoModal" class="withdrawmodal">
    <div class="width_draw_modal_content">
        <div id="selectedDate">YY.MM.DD</div>
        <label for="memo">메모:</label>
        <input type="text" id="memo" name="memo">
        <button onclick="saveMemo()">저장</button>
        <button onclick="cancelMemo()">취소</button>
    </div>
</div>



<script>
    function showMemoModal() {
        document.getElementById("memoModal").style.display = "block";
    }

    function saveMemo() {
        // 메모를 저장하는 로직을 추가할 수 있습니다.
        document.getElementById("memoModal").style.display = "none";
    }

    function cancelMemo() {
        // 취소 로직을 추가할 수 있습니다.
        document.getElementById("memoModal").style.display = "none";
    }
</script>

</body>
</html>