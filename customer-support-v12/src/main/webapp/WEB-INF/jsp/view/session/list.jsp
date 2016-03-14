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

 