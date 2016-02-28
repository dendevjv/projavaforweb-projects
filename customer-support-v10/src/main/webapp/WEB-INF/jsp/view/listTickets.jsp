<%-- @elvariable id="ticketDatabase" type="java.util.Map" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:basic htmlTitle="Tickets" bodyTitle="Tickets">

    <c:choose>
        <c:when test="${ticketDatabase.size() == 0}">
            <p><i>There are no tickets in the system.</i></p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${ticketDatabase}" var="entry">
                Ticket #${entry.key}:
                <a href="<c:url value="/tickets">
		                    <c:param name="action" value="view" />
		                    <c:param name="ticketId" value="${entry.key}" />
		                </c:url>">
                    <c:out value="${projava4web:abbreviateString(entry.value.subject, 60)}" />
                </a><br />
                <c:out value="${entry.value.customerName}" /> created ticket 
                <projava4web:formatDate value="${entry.value.dateCreated}" type="both" 
                        timeStyle="short" dateStyle="medium"  /><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>

</template:basic>
