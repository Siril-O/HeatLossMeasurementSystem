<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="content">
    <c:if test="${message != null}">
        <c:out value="${message}"/>
    </c:if>

    <h2>Hi</h2>

</div>