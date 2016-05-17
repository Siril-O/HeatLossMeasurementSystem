<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Manage Pipe ${sectionContext.pipe.ordinalNumber} ${sectionContext.pipe.house.address.city} ${sectionContext.pipe.house.address.street} ${sectionContext.pipe.house.address.houseNumber} house</h1>
       <c:choose>
           <c:when test="${empty sectionContext.pipe.measurementModules}">
              <h3>There are no measurement sections</h3>
           </c:when>
           <c:otherwise>
           <table class="table table-striped">
                  <tr>
                      <th>Number</th>
                      <th>Section Type</th>
                      <th>Measurement Module Type</th>
                      <th>Action</th>
                  </tr>
                  <c:forEach items="${sectionContext.pipe.measurementModules}" var="measurementModule">
                     <tr>
                        <td>Measurement Section # ${measurementSection.ordinalNumber}</td>
                         <td>${measurementSection.sectionType}</td>
                         <td>
                           <form method="POST" action="../measurementModule/manage">
                                   <input type="hidden" name="measurementModuleId" value="${measurementModule.id}">
                                   <input type="submit" value="Manage Module" class="btn btn-primary">
                           </form>
                         </td>
                     </tr>
                       </c:forEach>
                       </table>
           </c:otherwise>
       </c:choose>
              <h3>Add new Measurement module</h3>
       <h4>Apartment or Residential:</h4>
       <div class="radio">
         <label><input type="radio" name="allocation" id="apartment-measurementModule-selected"  onclick="apartmentMeasurementModuleSelected()"/>Apartment Module</label>
         </br>
         <label><input type="radio" name="allocation"  id="residential-measurementModule-selected" checked="checked" onclick="residentialMeasurementModuleSelected()"/>Other Module</label>
       </div>

  <div id="measurementModuleForm" style="display:none">
  <form:form commandName="measurementModule" method="POST" action="../measurementModule">
            <form:radiobuttons path="moduleType" items="${moduleTypes}"/>
             <fieldset class="form-group">
                     <label for="temperatureSensorModel">Choose temperature Sensors Model</label>
                     <select class="form-control"  id="temperatureSensorModel" name="temperatureSensorModelId" >
                      <c:forEach items="${sectionContext.temperatureSensorModels}" var="temperatureSensorModel">
                        <option value="${temperatureSensorModel.id}">${temperatureSensorModel.name}</option>
                      </c:forEach>
                     </select>
            </fieldset>
            <fieldset class="form-group">
                     <label for="flowSensorModel">Choose Flow Sensor Model</label>
                     <select  class="form-control" id="flowSensorModel" name="flowSensorModelId">
                      <c:forEach items="${sectionContext.flowSensorModels}" var="flowSensorModel">
                      <option value="${flowSensorModel.id}">${flowSensorModel.name}</option>
                      </c:forEach>
                     </select>
            </fieldset>
      <form:hidden path="pipe.id"/>
      <input type="submit" value="Create Measurement section" class="btn btn-primary">
</form:form>
</div>


  <div id="apartmentMeasurementModuleForm">
  <form:form commandName="apartmentMeasurementModule" method="POST" action="../measurementModule/apartment">
            <h4>Choose Apartment</h4>
            <fieldset class="form-group">
                    <label for="auto-complete-apartment">Apartment Number</label>
                    <input type="text" name="apartment" class="form-control" id="auto-complete-apartment" placeholder="Enter number"/>
                    <input type="hidden" name="apartmentId" id="auto-complete-apartment-hidden"/>
            </fieldset>
             <fieldset class="form-group">
                     <label for="temperatureSensorModel">Choose temperature Sensors Model</label>
                     <select class="form-control"  id="temperatureSensorModel" name="temperatureSensorModelId" >
                      <c:forEach items="${sectionContext.temperatureSensorModels}" var="temperatureSensorModel">
                        <option value="${temperatureSensorModel.id}">${temperatureSensorModel.name}</option>
                      </c:forEach>
                     </select>
            </fieldset>
            <fieldset class="form-group">
                     <label for="flowSensorModel">Choose Flow Sensor Model</label>
                     <select  class="form-control" id="flowSensorModel" name="flowSensorModelId">
                      <c:forEach items="${sectionContext.flowSensorModels}" var="flowSensorModel">
                      <option value="${flowSensorModel.id}">${flowSensorModel.name}</option>
                      </c:forEach>
                     </select>
            </fieldset>
      <form:hidden path="pipe.id"/>
      <input type="submit" value="Create Measurement section" class="btn btn-primary">
</form:form>
</div>



<script>
$(document).ready(function() {
            var serverURL = '${pageContext.request.contextPath}/utils/appartment' + document.getElementById('auto-complete-apartment').value;
         $('#auto-complete-apartment').autocomplete({
             source: serverURL,
             minLength: 1,
             delay: 50,
                     select: function( event, ui ) {
                     document.getElementById('auto-complete-apartment').value = ui.item.label ;
                     document.getElementById('auto-complete-apartment-hidden').value =  ui.item.value;
                     return false;
                     }
         });
  });
</script>
<script>
var apartmentMeasurementModuleDiv = document.getElementById("apartmentMeasurementModuleForm");
var measurementModuleDiv = document.getElementById("measurementModuleForm");
function apartmentMeasurementModuleSelected(){
 apartmentMeasurementModuleDiv.style.display="block";
 measurementModuleDiv.style.display="none";
};
function residentialMeasurementModuleSelected(){
 apartmentMeasurementModuleDiv.style.display="none";
 measurementModuleDiv.style.display="block";
 };

</script>

