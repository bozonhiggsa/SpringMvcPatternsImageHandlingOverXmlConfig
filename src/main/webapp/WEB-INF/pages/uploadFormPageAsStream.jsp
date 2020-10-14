<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload a file using Streaming API</title>
</head>
<body>
<h1>Please upload a file</h1>
<form method="post" action="/uploadAsStream" enctype="multipart/form-data">
    <input type="text" name="name"/>
    <input type="file" name="file"/>
    <input type="submit"/>
</form>
</body>
</html>