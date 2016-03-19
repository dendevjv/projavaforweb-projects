<%--@elvariable id="ticketForm" type="projava4webbook.customer_support_v13.site.TicketController.TicketForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<template:basic bodyTitle="Create a Ticket" htmlTitle="Create a Ticket">

    <c:if test="${validationErrors != null}"><div class="errors">
        <ul><c:forEach items="${validationErrors}" var="error">
            <li><c:out value="${error.message}" /></li>
        </c:forEach></ul>
    </div></c:if>

	<form:form method="POST" enctype="multipart/form-data"
	       modelAttribute="ticketForm">
        <form:label path="subject"><spring:message code="field.ticket.subject" /></form:label><br />
        <form:input path="subject"/><br /> 
        <form:errors path="subject" cssClass="errors" /><br /><br />
		
		<form:label path="body"><spring:message code="field.ticket.body" /></form:label><br />
		<form:textarea path="body" rows="5" cols="30"/><br />
		<form:errors path="body" cssClass="errors" /><br /><br />
		
		<b><spring:message code="field.ticket.attachments" /></b><br />
		<input type="file" name="attachments" multiple="multiple" /><br />
		<form:errors path="attachments" cssClass="errors" /><br /><br />
		
		<input type="submit" value="<spring:message code="button.ticket.submit" />" />
	</form:form>
	
</template:basic>

