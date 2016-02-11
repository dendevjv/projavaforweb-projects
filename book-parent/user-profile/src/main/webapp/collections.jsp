<%-- @elvariable id="user" type="projava4webbook.userprofile.User" --%>
<%@ page import="projava4webbook.userprofile.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- for Eclipse --%>
<%
	ArrayList<User> users = new ArrayList<>();
    users.add(new User(19384L, "Doder317", "John", "Smith"));
    users.add(new User(19383L, "geek217", "Joe", "Smith"));
    users.add(new User(19382L, "jack317", "Jack", "Smithson"));
    users.add(new User(19381L, "farmer-dude", "Adam", "Fisher"));
    request.setAttribute("users", users);
%>
<!DOCTYPE html>
<html>
<head>
<title>Collections and Streams</title>
</head>
<body>
    ${users.stream()
        .filter(u -> fn:contains(u.username, '1'))
        .sorted((u1, u2) -> (x = u1.lastName.compareTo(u2.lastName);
            x == 0 ? u1.firstName.compareTo(u2.firstName) : x))
        .map(u -> {'username' : u.username, 'first' : u.firstName, 'last' : u.lastName})
        .toList()}
</body>
</html>