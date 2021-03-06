<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Customer Support</title>
</head>
<body>
	<h2>Login</h2>
	You must log in to access the customer support site.<br />
	
	<c:if test="${loginFailed}">
	   <p><b>The username or password you entered are not correct. Please try again.</b></p>
	</c:if>
	
	<form method="POST" action="<c:url value="/login" />">
	   Username: <br />
	   <input type="text" name="username" /><br /><br />
	   Password: <br />
	   <input type="password" name="password" /><br /><br />
	   <input type="submit" value="Log In" />
	</form>
</body>
</html>