<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <title>Delete group</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    </head>
</head>
<body>
<form class="navbar-form navbar-left" role="deleteGroup" action="/group/delete" method="post">
    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Group Name</b></td>
        </tr>
        </thead>
        <c:forEach items="${groups}" var="gr">
            <tr>
                <td><input type="checkbox" name="toDeleteGroup[]" value="${gr.id}" id="checkbox_${gr.id}"/></td>
                <td>${gr.name}</td>>
            </tr>
        </c:forEach>

    </table>
    <li><button type="submit" id="delete_group" class="btn btn-default">Delete</button></li>

    <li><button>
</form>>




<script>
    $('#delete_group').click(function(){
        var data = { 'toDeleteGroup[]' : []};
        $(":checked").each(function() {
            data['toDeleteGroup[]'].push($(this).val());
        });
        $.post("/group/delete", data, function(data, status) {
            window.location.reload();
        });
    });
</script>

</body>
</html>
