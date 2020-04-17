<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DownloadToXML</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<form class="navbar-form navbar-left" role="xmlGroup" action="/group/toXML" method="post">
    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Group Name</b></td>
        </tr>
        </thead>
        <c:forEach items="${groups}" var="gr">
            <tr>
                <td><input type="checkbox" name="toXMLGroup[]" value="${gr.id}" id="checkbox_${gr.id}"/></td>
                <td>${gr.name}</td>>
            </tr>
        </c:forEach>

    </table>
    <li><button type="submit" id="toXML_group" class="btn btn-default">Convert</button></li>

    <li><button>
</form>>


<script>
    $('#toXML_group').click(function(){
        var data = { 'toXMLGroup[]' : []};
        $(":checked").each(function() {
            data['toXMLGroup[]'].push($(this).val());
        });
        $.post("/group/toXML", data, function(data, status) {
            window.location.reload();
        });
    });
</script>

</body>
</html>
