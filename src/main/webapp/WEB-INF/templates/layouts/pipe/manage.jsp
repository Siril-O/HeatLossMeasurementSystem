<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Manage Pipe ${sectionContext.pipe.ordinalNumber} ${sectionContext.pipe.house.address.city} ${sectionContext.pipe.house.address.street} ${sectionContext.pipe.house.address.houseNumber} house</h1>
       <c:choose>
           <c:when test="${empty sectionContext.pipe.measurementSections}">
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
                  <c:forEach items="${sectionContext.pipe.measurementSections}" var="measurementSection">
                     <tr>
                        <td>Measurement Section # ${measurementSection.ordinalNumber}</td>
                         <td>${measurementSection.sectionType}</td>
                         <td>
                           <form method="POST" action="../measurementSection/manage">
                                   <input type="hidden" name="measurementSectionId" value="${measurementSection.id}">
                                   <input type="submit" value="Manage Section" class="btn btn-primary">
                           </form>
                         </td>
                     </tr>
                       </c:forEach>
                       </table>
           </c:otherwise>
       </c:choose>
  <form:form commandName="measurementSection" method="POST" action="../measurementSection">
       <legend>Add new Measurement section</legend>

       <h4>Apartment or residential:</h4>
       <div class="radio">
         <label><input type="radio" name="allocation" id="apartment-allocation-selected"  onclick="apartmentAllocationSelected()"/>Apartment Section</label>
         </br>
         <label><input type="radio" name="allocation"  id="residential-allocation-selected" checked="checked" onclick="residentialAllocationSelected()"/>Pipe main Section</label>
       </div>

       <div id="apartment-allocation" style="display:none;">
            <h4>Add existing Apartment</h4>
            <fieldset class="form-group">
                    <label for="auto-complete-apartment">Apartment Number</label>
                    <input type="text" name="existingApartment" class="form-control" id="auto-complete-apartment" placeholder="Enter number"/>
                    <input type="hidden" name="existingApartmentId" id="auto-complete-apartment-hidden"/>
            </fieldset>
       </div>

       <h4>With Sensors:</h4>
       <div class="radio">
         <label><input type="radio" name="sensors-presence" id="sensors-present-selected"  onclick="sensorsPresenceSelected()"/>With Sensor</label>
         </br>
         <label><input type="radio" name="sensors-presence"  id="sensors-absent-selected" checked="checked" onclick="sensorsAbsenceSelected()"/>Without Sensor</label>
       </div>

      <div id="sensors-form" style="display:none">
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
      </div>
      <form:hidden path="pipe.id"/>
      <input type="submit" value="Create Measurement section" class="btn btn-primary">
</form:form>

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
var apartmentAllocation = document.getElementById("apartment-allocation");
var sensorsForm = document.getElementById("sensors-form");
function apartmentAllocationSelected(){
 apartmentAllocation.style.display="block";
};
function residentialAllocationSelected(){
 apartmentAllocation.style.display="none";
};

function sensorsPresenceSelected(){
 sensorsForm.style.display="block";
};
function sensorsAbsenceSelected(){
 sensorsForm.style.display="none";
};
</script>

