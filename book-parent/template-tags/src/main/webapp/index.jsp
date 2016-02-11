<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
    <h2>Hello World!</h2>
    
    <p>Go to <a href="<c:url value="/index" />">Index Servlet</a></p>
    
    <p>Go to view <a href="<c:url value="/index?dates" />">Dates formatting</a></p>
    
    <p>Go to view <a href="<c:url value="/index?text" />">Text abbreviation formatting</a></p>
</body>
</html>
