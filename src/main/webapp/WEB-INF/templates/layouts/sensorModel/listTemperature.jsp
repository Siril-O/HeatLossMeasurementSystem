<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
<ul class="nav nav-tabs">
  <li class="active"><a href="./listTemperature">Temperature Sensors Model</a></li>
  <li><a href="./listFlow">Flow Sensors Model</a></li>
</ul>

<h1>Temperature Sensor Models</h1>
<a href="./create" class="btn btn-primary">Register new Sensor Model</a>
  <table class="table table-striped">
  <tr>
      <th>Name</th>
      <th>Maker</th>
      <th>Min Temperature</th>
      <th>maxTemperature</th>
      <th>Absolute Accuracy</th>
      <th>Actions</th>
  </tr>
    <c:forEach items="${sensorModels}" var="sensorModel">
    <tr>
          <td>${sensorModel.name}</td>
          <td>${sensorModel.maker}</td>
          <td>${sensorModel.minTemperature}</td>
          <td>${sensorModel.maxTemperature}</td>
          <td>${sensorModel.absoluteAccuracy}</td>
          <td>
          <form method="POST" action="./manage">
            <input type="hidden" name="sensorModelId" value="${sensorModel.id}">
            <input type="submit" value="Manage Model" class="btn btn-primary">
          </form>
          </td>
    </tr>
    </c:forEach>
  </table>
</div>
