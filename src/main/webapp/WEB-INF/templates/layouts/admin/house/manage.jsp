<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Manage House ${house.address.city} ${house.address.street} ${house.address.houseNumber}</h1>
<form:form commandName="house" method="POST" action="./">
<fieldset>
<legend>Address</legend>
     <fieldset class="form-group">
        <label for="w-country">Country</label>
        <form:input path="address.country" type="text" class="form-control" id="w-country" placeholder="Enter country"/>
        <input type="hidden" name="countryId" id="w-country-hidden"/>
     </fieldset>
     <fieldset class="form-group">
          <label for="city">City</label>
          <form:input path="address.city" type="text" class="form-control" id="city" placeholder="Enter city"/>
     </fieldset>
     <fieldset class="form-group">
            <label for="street">Street</label>
            <form:input path="address.street" type="text" class="form-control" id="street" placeholder="Enter street"/>
     </fieldset>
     <fieldset class="form-group">
                     <label for="houseNumber">House Number</label>
                     <form:input path="address.houseNumber" type="text" class="form-control" id="houseNumber" placeholder="Enter houseNumber"/>
            </fieldset>
     </fieldset>
     <fieldset class="form-group">
                 <label for="Pipe-system-type">Street</label>
                 <form:input path="address.street" type="text" class="form-control" id="Pipe-system-type" placeholder="Enter street"/>
     </fieldset>
     <fieldset>
     <legend>Pipe System type</legend>
     <form:select path="pipeSystem" class="form-control">
         <form:option value="" label="Select Pipe System Type" />
         <form:options items="${pipeTypes}" />
     </form:select>
     </form:form>
     <c:choose>
                <c:when test="${empty house.pipes}">
                   <h3>There are no Pipes is this house</h3>
                </c:when>
                <c:otherwise>
           <h4>Pipes</h4>
                  <table class="table table-striped">
                       <tr>
                           <th>Number</th>
                           <th>Action</th>
                       </tr>
                            <c:forEach items="${house.pipes}" var="pipe">
                       <tr>
                                 <td>Pipe ${pipe.ordinalNumber}</td>
                                 <td>
                                 <form method="POST" action="../pipe/manage">
                                    <input type="hidden" name="pipeId" value="${pipe.id}">
                                    <input type="submit" value="Manage Pipe" class="btn btn-primary">
                                 </form>
                                 </td>
                           </tr>
                     </c:forEach>
                     </table>
                </c:otherwise>
     </c:choose>
                   <form method="POST" action="../pipe">
                   <input type="hidden" name="houseId" value="${house.id}"/>
                   <input type="submit" value="Add pipe"  class="btn btn-primary"/>
                   </form>

         <c:choose>
                         <c:when test="${empty house.apartments}">
                            <h3>There are no Apartments in this house</h3>
                         </c:when>
                         <c:otherwise>
                         <h4>Apartments</h4>
                           <table class="table table-striped">
                                <tr>
                                    <th>Number</th>
                                    <th>Owner</th>
                                    <th>rooms</th>
                                    <th>floor</th>
                                </tr>
                                     <c:forEach items="${house.apartments}" var="apartment">
                                <tr>
                                          <td>${apartment.number}</td>
                                          <td>${apartment.owner}</td>
                                          <td>${apartment.rooms}</td>
                                          <td>${apartment.floor}</td>
                                    </tr>
                              </c:forEach>
                              </table>
                         </c:otherwise>
              </c:choose>
         <form method="POST" action="../apartment/create">
              <input type="hidden" name="houseId" value="${house.id}">
              <input type="submit" value="Add Apartment" class="btn btn-primary">
         </form>


  <script>
  $(document).ready(function() {
            var serverURL = '${pageContext.request.contextPath}/utils/country' + document.getElementById('w-country').value;
         $('#w-country').autocomplete({
             source: serverURL,
             minLength: 1,
             delay: 50,
                     select: function( event, ui ) {
                     document.getElementById('w-country').value = ui.item.label ;
                     document.getElementById('w-country-hidden').value =  ui.item.value;
                     return false;
                     }
         });
  });
  </script>