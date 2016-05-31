<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="top-menu">
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/HeatLossSystem/">HeatLossSystem</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="/HeatLossSystem/report">Reports</a></li>
				<li><a href="/HeatLossSystem/house/list">Houses</a></li>
				<li><a href="/HeatLossSystem/sensorModel/listTemperature">Sensor Model</a></li>
                <li><c:url var="logOutUrl" value="/logout" />
                	<form action="${logOutUrl}" method="post" class="navbar-form navbar-right">
                		<div class="form-group">
                			<a href="/QuizSystem/jsp/user/info">${user.name}, ${user.surname}</a>
                			<input class="btn btn-primary" type="submit" value="Log Out" />
                			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                		</div>
                	</form>
                </li>
			</ul>
        </div>
    </div>
</div>