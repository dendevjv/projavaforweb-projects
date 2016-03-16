<%-- @elvariable id="numberOfSessions" type="java.lang.Integer" --%>
<%-- @elvariable id="timestamp" type="java.lang.Long" --%>
<%-- @elvariable id="sessionList" type="java.util.List<java.servlet.http.HttpSession>" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:basic htmlTitle="Active Sessions" bodyTitle="Active Sessions">
    
    <p><spring:message code="message.sessionList.instruction">
        <spring:argument value="${numberOfSessions}" />
    </spring:message></p>
    
    <c:if test="${numberOfSessions > 0}">
        <ul>
            <c:forEach items="${sessionList}" var="aSession" >
                <li>
                    <c:out value="${aSession.id} - ${aSession.getAttribute('projava4webbook.customer_support.user.principal')}" />
                    
                    <c:if test="${aSession.id == pageContext.session.id}">
                        &nbsp;(<spring:message code="message.sessionList.you" />) :
                    </c:if>
                    &nbsp;- 
                    <spring:message code="message.sessionList.lastActive">
                         <spring:argument>${projava4web:timeIntervalToString(timestamp - aSession.lastAccessedTime)}</spring:argument>
                    </spring:message> 
                </li>
            </c:forEach>
        </ul>
    </c:if>
</template:basic>

 