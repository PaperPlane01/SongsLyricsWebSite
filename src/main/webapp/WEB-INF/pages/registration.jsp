<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="register" /> <br>
    <input type="text" name="username"/> <br>
    <input type="password" name="password"/> <br>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
