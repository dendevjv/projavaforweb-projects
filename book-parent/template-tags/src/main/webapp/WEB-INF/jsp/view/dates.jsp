<%-- @elvariable id="date" type="java.util.Date" --%>
<%-- @elvariable id="calendar" type="java.util.Calendar" --%>
<%-- @elvariable id="instant" type="java.timeInstant" --%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:main htmlTitle="Displaying Dates Properly">
    <b>Date:</b>
    <projava4web:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" /><br />
    
    <b>Date, time first with separator:</b>
    <projava4web:formatDate value="${date}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
    
    <b>Calendar:</b>
    <projava4web:formatDate value="${calendar}" type="both" dateStyle="full" timeStyle="full" /><br />
    
    <b>Instant:</b>
    <projava4web:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" /><br />
    
    <b>Instant, time first with separator:</b>
    <projava4web:formatDate value="${instant}" type="both" dateStyle="full" timeStyle="full" timeFirst="true" separateDateTimeWith=" on " /><br />
</template:main>
