<%-- @elvariable id="numberOfSessions" type="java.lang.Integer" --%>
<%-- @elvariable id="timestamp" type="java.lang.Long" --%>
<%-- @elvariable id="sessionList" type="java.util.List<java.servlet.http.HttpSession>" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:basic htmlTitle="Active Sessions" bodyTitle="Active Sessions">
    
    <p>There are a total of ${numberOfSessions} active sessions in this application.</p>
    
    <c:if test="${numberOfSessions > 0}">
        <ul>
            <c:forEach items="${sessionList}" var="aSession" >
                <li>
                    <c:out value="${aSession.id} - ${aSession.getAttribute('username')}" />
                    
                    <c:if test="${aSession.id == pageContext.session.id}">&nbsp;(you) :</c:if>
                    &nbsp;- last active ${projava4web:timeIntervalToString(timestamp - aSession.lastAccessedTime)} ago
                </li>
            </c:forEach>
        </ul>
    </c:if>
</template:basic>

<%--
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
    <p>There are a total of ${numberOfSessions} active sessions in this application.</p>
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
 --%>
 