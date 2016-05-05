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
				<li><a href="/HeatLossSystem/report">Admin</a></li>
				<li><a href="/HeatLossSystem/house/list">Houses</a></li>
				<li><a href="/HeatLossSystem/sensorModel/listTemperature">Sensor Model</a></li>
			</ul>
        </div>
    </div>
</div>