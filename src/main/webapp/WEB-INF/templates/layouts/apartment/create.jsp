<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Register new House</h1>
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
      <fieldset class="form-group">
              <label for="Amount of pipes">Amount of pipes</label>
              <input type="text" class="form-control" id="Pipe-system-type" placeholder="Enter Amount of pipes" name="amountOfPipes"/>
          </fieldset>
      </fieldset>
<input type="submit" class="btn btn-primary" value="Create House"/>

</form:form>
