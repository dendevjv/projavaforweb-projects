<!--@elvariable id="userList" type="java.util.Collection<projava4webbook.spring_forms.site.User>" -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	   <title>User list</title>
	</head>
	<body>
	   <h2>Users</h2>
	   [<a href="<c:url value="/user/add" />">new user</a>]<br />
	   <br />
	   <c:forEach items="${userList}" var="user">
	       ${user.name} (${user.username})
	       [<a href="<c:url value="/user/edit/${user.userId}" />">edit</a>]<br />
	   </c:forEach>
	</body>
</html>