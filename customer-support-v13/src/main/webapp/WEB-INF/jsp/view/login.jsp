<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="loginForm" type="projava4webbook.customer_support_v13.site.AuthenticationController.LoginForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<template:loggedOut htmlTitle="Log in" bodyTitle="Log In">

    <p><spring:message code="message.login.instruction" /></p>
    
    <c:if test="${loginFailed}">
       <p><b><spring:message code="error.login.failed" /></b></p>
    </c:if>
    
    <c:if test="${validationErrors != null}"><div class="errors">
        <ul><c:forEach items="${validationErrors}" var="error">
            <li><c:out value="${error.message}" /></li>
        </c:forEach></ul>
    </div></c:if>
    
    <form:form method="post" modelAttribute="loginForm" >
        <form:label path="username"><spring:message code="field.login.username" />:</form:label><br />
        <form:input path="username"/><br />
        <form:errors path="username" cssClass="errors" /><br /><br />
        
        <form:label path="password"><spring:message code="field.login.password" />:</form:label><br />
        <form:password path="password"/><br />
        <form:errors path="password" cssClass="errors" /><br /><br />
        
        <input type="submit" value="<spring:message code="field.login.submit" />" />
    </form:form>
    
</template:loggedOut>
