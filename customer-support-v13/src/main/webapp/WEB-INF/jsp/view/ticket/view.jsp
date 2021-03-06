<%-- @elvariable id="ticketId" type="java.lang.String" --%>
<%-- @elvariable id="ticket" type="projava4webbook.customer_support_v13.site.Ticket" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:basic htmlTitle="${ticket.subject}" bodyTitle="Ticket # ${ticketId} : ${ticket.subject}">

    <i>
        <spring:message code="message.ticketView.customerName" />: <c:out value="${ticket.customerName}" /><br /><br />
        <spring:message code="message.ticketView.created" />&nbsp;<projava4web:formatDate value="${ticket.dateCreated}" type="both" 
                                        timeStyle="long" dateStyle="full" /><br />
    </i>
    
    <c:out value="${ticket.body}" /><br /><br />
    
    <c:if test="${ticket.numberOfAttachments > 0}">
        <spring:message code="message.ticketView.attachments" />:
        <c:forEach items="${ticket.attachments}" var="attachment" varStatus="status">
            <c:if test="${!status.first}">, </c:if>
            <a href="<c:url value="/ticket/${ticketId}/attachment/${attachment.name}" />">
                <c:out value="${attachment.name}" />
            </a>
        </c:forEach><br /><br />
    </c:if>
    
</template:basic>
