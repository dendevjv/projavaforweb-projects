<%--@elvariable id="date" type="java.util.Date"--%>
<%--@elvariable id="alerts" type="int"--%>
<%--@elvariable id="numCritical" type="int"--%>
<%--@elvariable id="numImportant" type="int"--%>
<%--@elvariable id="numTrivial" type="int"--%>
<%--@elvariable id="exception" type="java.lang.Exception"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="title.alerts" /></title>
    </head>
    
	<body>
	   <h2><spring:message code="title.alerts" /></h2>
	   
	   <p><em><fmt:message key="alerts.current.date">
           <fmt:param value="${date}" />
       </fmt:message></em></p>
	   
	   <p>
		   <fmt:message key="number.alerts">
		       <fmt:param value="${alerts}" />
		   </fmt:message>
		   <c:if test="${alerts > 0}">
		       &nbsp;<spring:message code="alert.details" >
		           <spring:argument value="${numCritical}" />
	               <spring:argument value="${numImportant}" />
	               <spring:argument value="${numTrivial}" />
		       </spring:message>
		   </c:if>
	   </p>
	   
	   <c:if test="${exception != null}">
	       <p><spring:message message="${exception}" /></p>
	   </c:if>
	</body>
</html>
