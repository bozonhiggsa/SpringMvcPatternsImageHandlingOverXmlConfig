<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload a file please</title>
    <link href=<c:url value="/static/common.css"/> rel="stylesheet">
</head>
<body>
<h1>Please upload a file</h1>
<form method="post" action="/upload" enctype="multipart/form-data">
    <input type="text" name="name"/>
    <input type="file" name="file"/>
    <input type="submit"/>
</form>

<script type="text/javascript" src="<c:url value="/static/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/foo.js" />"/>
</body>
</html>