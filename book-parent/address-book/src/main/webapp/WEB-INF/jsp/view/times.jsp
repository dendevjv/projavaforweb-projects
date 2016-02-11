<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String[] fmtStyles = {"full", "long", "medium", "short"};
	request.setAttribute("fmtStyles", fmtStyles);
%>
<!DOCTYPE html>
<html>
<head>
<title>Time zones, Formatting time</title>
</head>
<body>
    <h2>Time zones, Formatting time</h2>
    
    <h3>formatDate</h3>
    
    <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
    <c:forEach items="${fmtStyles}" var="fmtStyle">
        <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
        <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
    </c:forEach>
    
    <h3>plus2TimeZone</h3>
    <fmt:timeZone value="${plus2TimeZone}">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
	    <c:forEach items="${fmtStyles}" var="fmtStyle">
	        <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
	        <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
	    </c:forEach>
    </fmt:timeZone>
    
    <h3>plus3TimeZone</h3>
    <fmt:timeZone value="${plus3TimeZone}">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
	    <c:forEach items="${fmtStyles}" var="fmtStyle">
	        <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
	        <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
	    </c:forEach>
    </fmt:timeZone>
    
    <h3>minus2TimeZone</h3>
    <fmt:timeZone value="${minus2TimeZone}">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
        <c:forEach items="${fmtStyles}" var="fmtStyle">
            <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
            <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
        </c:forEach>
    </fmt:timeZone>
    
    <h3>minus3TimeZone</h3>
    <fmt:timeZone value="${minus3TimeZone}">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
        <c:forEach items="${fmtStyles}" var="fmtStyle">
            <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
            <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
        </c:forEach>
    </fmt:timeZone>
    
    <h3>America/Los_Angeles</h3>
    <fmt:timeZone value="America/Los_Angeles">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
        <c:forEach items="${fmtStyles}" var="fmtStyle">
            <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
            <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
        </c:forEach>
    </fmt:timeZone>
    
    <h3>GMT-8</h3>
    <fmt:timeZone value="GMT-8">
        <p><fmt:formatDate value="${today}" type="both" dateStyle="long" timeStyle="long"/></p>
        <c:forEach items="${fmtStyles}" var="fmtStyle">
            <fmt:formatDate value="${today}" type="date" dateStyle="${fmtStyle}"/><br />
            <fmt:formatDate value="${today}" type="time" timeStyle="${fmtStyle}"/><br />
        </c:forEach>
    </fmt:timeZone>
    
</body>
</html>