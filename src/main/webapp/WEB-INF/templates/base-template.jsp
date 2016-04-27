<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <tiles:getAsString name="title" />
        </title>
            <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
            <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
            <script src="<c:url value="/resources/js/jquery.1.10.2.min.js" />" ></script>
                        <script src="<c:url value="/resources/js/jquery.autocomplete.min.js" />" ></script>

         </head>
    <body>
        <div class="container">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="navigation" />
            <tiles:insertAttribute name="content" />
            <tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>