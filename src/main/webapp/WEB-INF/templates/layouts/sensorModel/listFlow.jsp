<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
<ul class="nav nav-tabs">
  <li><a href="./listTemperature">Temperature Sensors Model</a></li>
  <li class="active"><a href="./listFlow">Flow Sensors Model</a></li>
</ul>

<h1>Temperature Sensor Models</h1>
<a href="./create" class="btn btn-primary">Register new Sensor Model</a>
  <table class="table table-striped">
  <tr>
      <th>Name</th>
      <th>Maker</th>
      <th>Min FlowRate</th>
      <th>Max FlowRate</th>
      <th>Absolute Accuracy</th>
  </tr>
    <c:forEach items="${sensorModels}" var="sensorModel">
    <tr>
          <td>${sensorModel.name}</td>
          <td>${sensorModel.maker}</td>
          <td>${sensorModel.minFlowRate}</td>
          <td>${sensorModel.maxFlowRate}</td>
          <td>${sensorModel.absoluteAccuracy}</td>
    </tr>
    </c:forEach>
  </table>
</div>
