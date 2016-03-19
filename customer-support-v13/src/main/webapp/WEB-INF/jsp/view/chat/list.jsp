<%--@elvariable id="sessions" type="java.util.List<projava4webbook.customer_support_v13.chat.ChatSession>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat Requests">
    <c:choose>
        <c:when test="${fn:length(sessions) == 0}">
            <i><spring:message code="message.chatList.none" /></i>
        </c:when>
        <c:otherwise>
            <spring:message code="message.chatList.instruction" />:<br /><br />
            <c:forEach items="${sessions}" var="s">
                <a href="javascript:void 0;"
                   onclick="join(${s.sessionId});">${s.customerUsername}</a><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript">
        var join = function(id) {
            postInvisibleForm('<c:url value="/chat/join/{id}" />'.replace('{id}', id), { });
        };
    </script>
</template:basic>
