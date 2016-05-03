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
          <script src="<c:url value="/resources/js/jquery-1.10.2.js" />" ></script>
            <script src="<c:url value="/resources/js/jquery-ui.js" />" ></script>
            <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
            <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
            <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
         </head>
    <body>
        <div class="container">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="top-menu" />
            <div id="content-wrapper">
            <tiles:insertAttribute name="content" />
        </div>
            <tiles:insertAttribute name="paging" />
            <tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>