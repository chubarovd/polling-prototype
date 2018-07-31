<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Task List Page</title>
    </head>
    <body>
        <form action="/logout" method="POST">
            <input type="submit" value="Sign Out"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>

        <div>
            <script type="text/javascript">
                var votesList = [];
                function onPlusClick(i) {
                    document.getElementById("vote" + i).innerHTML = ' [ ' + (++votesList[i]) + ' ] ';
                };

                function onMinusClick(i) {
                    if (votesList[i] > 0)
                        document.getElementById("vote" + i).innerHTML = ' [ ' + (--votesList[i]) + ' ] ';
                };

                function onSaveClick() {
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", '/polling/save_votes', true)
                    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    xhr.setRequestHeader("X-CSRF-Token", "${_csrf.token}");
                    var json = {};
                    json.list = votesList;
                    xhr.send(JSON.stringify(json));
                }
            </script>

            <table id="table"><h2>~ Task list ~</h2>
                <%int i = 0;%>
                <c:forEach items="${items}" var="item">
                    <tr>
                        <th># <%=i%> </th>
                        <th id="item<%=i%>">${item.content}</th>
                        <th id="vote<%=i%>"> [ 0 ] </th>
                        <th><button type="button" onclick="onPlusClick(<%=i%>)">+</button>
                        <button type="button" onclick="onMinusClick(<%=i++%>)">-</button></th>
                    </tr>
                    <script>votesList.push(0);</script>
                </c:forEach>
            </table>

            <script type="text/javascript">

            </script>

            <button type="button" onClick="onSaveClick()">Save votes</button>
        </div>

        <form action="/debug/add" method="POST">
            <input type="text" name="content"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Add item"/>
        </form>
        <form action="/debug/print_summary" method="POST">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Print summary"/>
        </form>

        <script type="text/javascript">
            function onPrintClick() {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", '/debug/print_votes', true);
                xhr.setRequestHeader("X-CSRF-Token", "${_csrf.token}");
                xhr.send();
            }
        </script>
        <button type="button" onClick="onPrintClick()">Print votes</button>
    </body>
</html>