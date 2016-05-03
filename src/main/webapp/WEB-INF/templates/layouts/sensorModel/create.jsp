<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form>
<h4>Choose sensor model type:</h4>
<div class="radio">
  <label><input type="radio" name="sensorType" id="temp-sensor-selected" checked="checked" onclick="tempSensorSelected()"/>Temperature Sensor</label>
</div>
<div class="radio">
  <label><input type="radio" name="sensorType"  id="flow-sensor-selected" onclick="flowSensorSelected()"/>Flow Sensor</label>
</div>
</form>

<div id="flowSensorModel-form" style="display:none;">
<form:form commandName="flowSensorModel" method="POST" action="./flow">
    <h2>Create Flow Sensor Model:</h2>
     <fieldset class="form-group">
        <label for="name">Name:</label>
        <form:input path="name" type="text" class="form-control" id="name" placeholder="Enter name"/>
     </fieldset>
          <fieldset class="form-group">
             <label for="maker">Maker:</label>
             <form:input path="maker" type="text" class="form-control" id="maker" placeholder="Enter maker"/>
          </fieldset>
     <fieldset class="form-group">
        <label for="maxFlowRate">Max Flow Rate:</label>
        <form:input path="maxFlowRate" type="text" class="form-control" id="maxFlowRate" placeholder="Enter maxFlowRate"/>
     </fieldset>
     <fieldset class="form-group">
          <label for="minFlowRate">Min Flow Rate:</label>
          <form:input path="minFlowRate" type="text" class="form-control" id="minFlowRate" placeholder="Enter minFlowRate"/>
     </fieldset>
     <fieldset class="form-group">
            <label for="absoluteAccuracy">Absolute Accuracy:</label>
            <form:input path="absoluteAccuracy" type="text" class="form-control" id="absoluteAccuracy" placeholder="Enter absoluteAccuracy"/>
     </fieldset>
<input type="submit" class="btn btn-primary"/>
</form:form>
</div>

<div id="tempSensorModel-form" >
<form:form commandName="tempSensorModel" method="POST" action="./temperature">
    <h2>Create Temperature Sensor Model:</h2>
     <fieldset class="form-group">
        <label for="name">Name:</label>
        <form:input path="name" type="text" class="form-control" id="name" placeholder="Enter name"/>
     </fieldset>
          <fieldset class="form-group">
             <label for="maker">Maker:</label>
             <form:input path="maker" type="text" class="form-control" id="maker" placeholder="Enter maker"/>
          </fieldset>
     <fieldset class="form-group">
        <label for="minTemperature">Min Temperature:</label>
        <form:input path="minTemperature" type="text" class="form-control" id="minTemperature" placeholder="Enter minTemperature"/>
     </fieldset>
     <fieldset class="form-group">
          <label for="maxTemperature">Max Temperature:</label>
          <form:input path="maxTemperature" type="text" class="form-control" id="maxTemperature" placeholder="Enter maxTemperature"/>
     </fieldset>
     <fieldset class="form-group">
            <label for="absoluteAccuracy">Absolute Accuracy:</label>
            <form:input path="absoluteAccuracy" type="text" class="form-control" id="absoluteAccuracy" placeholder="Enter absoluteAccuracy"/>
     </fieldset>
<input type="submit" class="btn btn-primary"/>
</form:form>
</div>
<script>
var flowForm = document.getElementById("flowSensorModel-form");
var tempForm = document.getElementById("tempSensorModel-form");
function tempSensorSelected(){
 tempForm.style.display="block";
 flowForm.style.display="none";
};
function flowSensorSelected(){
 tempForm.style.display="none";
 flowForm.style.display="block";
};
</script>