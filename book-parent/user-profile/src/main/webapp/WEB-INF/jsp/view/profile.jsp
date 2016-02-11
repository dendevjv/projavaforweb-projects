<%-- @elvariable id="user" type="projava4webbook.userprofile.User" --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- for Eclipse --%>
<!DOCTYPE html>
<html>
<head>
<title>User Profile</title>
</head>
<body>
    User ID: ${user.userId}<br />
    Username: ${user.username} (${user.username.length()} characters )<br />
    Full Name: ${fn:escapeXml(user.lastName) += ', ' += fn:escapeXml(user.firstName)}<br />
    <br />
    <br />
    <b>Permissions ${fn:length(user.permissions)}</b><br />
    User: ${user.permissions["user"]}<br />
    Moderator: ${user.permissions["moderator"]}<br />
    Administrator: ${user.permissions["admin"]}<br />
</body>
</html>