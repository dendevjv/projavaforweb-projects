<%-- @elvariable id="ticketId" type="java.lang.String" --%>
<%-- @elvariable id="ticket" type="projava4webbook.customer_support_v4.Ticket" --%>
<%@ page import="projava4webbook.customer_support_v4.Ticket, projava4webbook.customer_support_v4.Attachment" %> <%-- this line if for Eclipse --%>
<%
    Ticket ticket = (Ticket)request.getAttribute("ticket");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <p><a href="<c:url value="/login?logout" />">Logout</a></p>
        
        <h2>Ticket # ${ticketId}: ${ticket.subject}</h2>
        <i>Customer Name: ${ticket.customerName}</i><br /><br />
        ${ticket.body}<br /><br />
        <%
            if(ticket.getNumberOfAttachments() > 0) {
                %>Attachments: <%
                int i = 0;
                for(Attachment a : ticket.getAttachments()) {
                    if(i++ > 0)
                        out.print(", ");
                    %><a href="<c:url value="/tickets">
                        <c:param name="action" value="download" />
                        <c:param name="ticketId" value="${ticketId}" />
                        <c:param name="attachment" value="<%= a.getName() %>" />
                    </c:url>"><%= a.getName() %></a><%
                }
                %><br /><br /><%
            }
        %>
        <a href="<c:url value="/tickets" />">Return to list tickets</a>
    </body>
</html>
