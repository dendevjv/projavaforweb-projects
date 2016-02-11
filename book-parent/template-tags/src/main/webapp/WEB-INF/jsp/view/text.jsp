<%-- @elvariable id="shortText" type="java.lang.String" --%>
<%-- @elvariable id="longText" type="java.lang.String" --%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="projava4web" uri="http://www.projava4webbook.com/jsp/tld/projava4web" %>
<template:main htmlTitle="Abbreviating text">
    <p>Short text: <b> ${projava4web:abbreviateString(shortText, 32)}</b></p>
    <p>Long text: <b> ${projava4web:abbreviateString(longText, 32)}</b></p>
</template:main>