<%--@elvariable id="contacts" type="java.util.Set<projava4webbook.addressbook.Contact>"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>Address Book</title>
</head>
<body>
    <h2>Address Book Contacts</h2>
    
    <c:choose>
        <c:when test="${fn:length(contacts) == 0}">
            <em>There are no contacts in the address book.</em>
        </c:when>
        <c:otherwise>
            <c:forEach items="${contacts}" var="contact">
                <strong>
                    <c:out value="${contact.lastName}, ${contact.firstName}" />
                </strong><br />
                <c:out value="${contact.address}" /><br />
                <c:out value="${contact.phoneNumber}" /><br />
                <c:if test="${contact.birthday != null}">
	                Birthday: ${contact.birthday}<br />
                </c:if>
                Created: ${contact.dateCreated}<br /><br />
            </c:forEach>
        </c:otherwise>
    </c:choose>
</body>
</html>