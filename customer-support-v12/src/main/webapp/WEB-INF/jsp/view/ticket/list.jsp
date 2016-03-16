<%-- @elvariable id="tickets" type="java.util.List<projava4webbook.customer_support_v12.site.Ticket>" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:basic htmlTitle="Tickets" bodyTitle="Tickets">

    <c:choose>
        <c:when test="${tickets.size() == 0}">
            <p><i><spring:message code="message.ticketList.none" /></i></p>
        </c:when>
        <c:otherwise>
            <c:forEach items="${tickets}" var="ticket">
                <p>
	                <spring:message code="message.ticketList.ticket" />&nbsp;#${ticket.id}:
	                <a href="<c:url value="/ticket/view/${ticket.id}" />">
	                    <c:out value="${projava4web:abbreviateString(ticket.subject, 60)}" />
	                </a><br />
	                <c:out value="${ticket.customerName}" />&nbsp;
                    <spring:message code="message.ticketList.created" />&nbsp;
	                <projava4web:formatDate value="${ticket.dateCreated}" type="both" 
	                        timeStyle="short" dateStyle="medium"  />
	            </p>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</template:basic>
