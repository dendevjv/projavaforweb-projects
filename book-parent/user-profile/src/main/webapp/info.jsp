<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%-- for Eclipse --%>
<%  
	application.setAttribute("appAttribute", "Stored in application");
	pageContext.setAttribute("pageAttribute", "Stored in page");
	session.setAttribute("sessionAttribute", "Stored in session");
	request.setAttribute("requestAttribute", "Stored in request");
%>
<!DOCTYPE html>
<html>
<head>
<title>Information</title>
</head>
<body>
    Remote Address: ${pageContext.request.remoteAddr}<br />
    Request URL: ${pageContext.request.requestURL}<br />
    Session ID: ${pageContext.request.session.id}<br />
    Application Scope: ${applicationScope["appAttribute"] }<br />
    Page Scope: ${pageScope["pageAttribute"] }<br />
    Session Scope: ${sessionScope["sessionAttribute"] }<br />
    Request Scope: ${requestScope["requestAttribute"] }<br />
    User Parameter: ${param["user"]}<br />
    Color Multi-Param: ${fn:join(paramValues["colors"], ', ') }<br />
    Accept Header: ${header["Accept"]}<br />
    Session ID Cookie Value: ${cookie["JSESSIONID"].value}<br />
</body>
</html>