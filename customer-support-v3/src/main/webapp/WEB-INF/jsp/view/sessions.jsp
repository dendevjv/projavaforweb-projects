<%@page import="java.util.List"%>
<%!
	private static String toString(long timeInterval) {
	    if(timeInterval < 1_000)
	        return "less than one second";
	    if(timeInterval < 60_000)
	        return (timeInterval / 1_000) + " seconds";
	    return "about " + (timeInterval / 60_000) + " minutes";
	}
%>
<%
	int numberOfSessions = (Integer) request.getAttribute("numberOfSessions");
	@SuppressWarnings("unchecked")
	List<HttpSession> sessionList = (List<HttpSession>) request.getAttribute("sessionList");
%>
<!DOCTYPE html>
<html>
<head>
<title>Customer Support</title>
</head>
<body>
    <p><a href="<c:url value="/login?logout" />">Logout</a></p>
    
    <h2>Sessions</h2>
    <p>There are a total of <%= numberOfSessions %> active sessions in this application.</p>
    <%
    if (numberOfSessions > 0) {
        long timestamp = System.currentTimeMillis();
        out.println("<ul>");
	    for (HttpSession aSession : sessionList) {
	        %><li><%= aSession.getId() %> - 
	        <%= aSession.getAttribute("username") %><%= aSession.getId().equals(session.getId()) ? " (you)" : "" %> - 
	        last active <%= toString(timestamp - aSession.getLastAccessedTime()) %> ago</li><%
	    }
	    out.println("</ul>");
    }
    %>
</body>
</html>