<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body>
    <h3>Using &lt;c:out&gt; tag</h3>
    <p>var1 : <c:out value="${var1}" /></p>
    <p>var2 (without XML excaping) : ${var2}</p>
    <p>var2 (with XML excaping) : <c:out value="${var2}" /></p>
    <p>default value : <c:out value="${noneExistingVariable}" default="Text for noneExistingVariable" /></p>
    <p>
        default value : 
        <c:out value="${noneExistingVariable}" escapeXml="false">
            <ul>
                <li>Default attribute may contain EL expressions</li>
                <li>Nested contents in &lt;c:out&gt; is used as default</li> 
            </ul>
        </c:out>
    </p>
    <c:out value="${varScript}" />
    
    <h3>Using &lt;c:url&gt; tag</h3>
    <p><a href="<c:url value="http://www.example.com/info/page.html" />">Absolute URL</a></p>
    <p><a href="<c:url value="http://www.example.com/info/page.html" >
        <c:param name="parameter1" value="${var1}" />
        <c:param name="parameter2" value="${var3}" />
    </c:url>">Absolute URL with parameters</a></p>
    <p><a href="<c:url value="/page123" />">Relative URL</a></p>
    
    <h3>Using &lt;c:if&gt; tag</h3>
    <c:if test="${var3 == 'simpleWord'}">
        <p>var3 == 'simpleWord'</p>
    </c:if>
    
    <h3>Using &lt;c:choose&gt; tag</h3>
    <c:choose>
        <c:when test="${fn:contains(var3, 'words')}">
            var3 contains 'words'
        </c:when>
        <c:when test="${fn:contains(var3, 'Wordz')}">
            var3 contains 'Word'
        </c:when>
        <c:when test="${fn:contains('oneWord', 'Word')}">
            'oneWord' contains 'Word'
        </c:when>
    </c:choose>
    
    <h3>Using &lt;c:forEach&gt;</h3>
    <h4>var, begin, end</h4>
    <ol>
	    <c:forEach var="i" begin="0" end="5">
	        <li>item ${i}</li>
	    </c:forEach>
    </ol>
    <h4>items, var</h4>
    <ol>
	    <c:forEach items="${days}" var="day" >
	        <li>Day of week: ${day}</li>
	    </c:forEach>
    </ol>
    <h4>varStatus</h4>
    <ol>
        <c:forEach items="${days}" var="day" varStatus="status" >
            <li>Day of week: ${day}<br />
                <%-- ${status.begin}<br />
                ${status.end}<br />
                ${status.step}<br /> --%>
                ${status.count}<br />
                ${status.current}<br />
                ${status.index}<br />
                ${status.first}<br />
                ${status.last}<br />
            </li>
        </c:forEach>
    </ol>
    
    <h3>Using &lt;c:forTokens&gt;</h3>
    <ol>
        <c:forTokens items="One-Two-Three" delims="-" var="number" >
            <li>${number}</li>
        </c:forTokens>
    </ol>
    
    <h3>Using &lt;c:import&gt; tag</h3>
    
    <c:import url="/WEB-INF/jsp/view/some-text-for-import.txt" var="importedText" />
    <p><c:choose>
        <c:when test="${fn:contains(importedText, 'plain')}">
            <strong>${importedText}</strong>
        </c:when>
        <c:otherwise>
            <em>${importedText}</em>
        </c:otherwise>
    </c:choose></p>
    
    <c:set var="importedText" value="Changed value of imported text" />
    <p><c:choose>
        <c:when test="${fn:contains(importedText, 'plain')}">
            <strong>${importedText}</strong>
        </c:when>
        <c:otherwise>
            <em>${importedText}</em>
        </c:otherwise>
    </c:choose></p>
</body>
</html>