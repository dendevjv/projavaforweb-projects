<%-- @elvariable id="ticketId" type="java.lang.String" --%>
<%-- @elvariable id="ticket" type="projava4webbook.customer_support_v5.Ticket" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <p><a href="<c:url value="/login?logout" />">Logout</a></p>
        
        <h2>Ticket # ${ticketId}: <c:out value="${ticket.subject}" /></h2>
        <i>Customer Name: <c:out value="${ticket.customerName}" /></i><br /><br />
        <c:out value="${ticket.body}" /><br /><br />
        
        <c:if test="${ticket.numberOfAttachments > 0}">
            Attachments:
            <c:forEach items="${ticket.attachments}" var="attachment" varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <a href="<c:url value="/tickets">
	                        <c:param name="action" value="download" />
	                        <c:param name="ticketId" value="${ticketId}" />
	                        <c:param name="attachment" value="${attachment.name}" />
	                    </c:url>"><c:out value="${attachment.name}" />
                 </a>
            </c:forEach><br /><br />
        </c:if>
        
        <a href="<c:url value="/tickets" />">Return to list tickets</a>
    </body>
</html>
