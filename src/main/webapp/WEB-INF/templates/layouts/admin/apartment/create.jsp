<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="createApartmentUrl" value="/apartment"/>

<h1>Register new House</h1>
<form:form commandName="apartment" method="POST" action="${createApartmentUrl}">
<fieldset>
<legend>Create Apartment</legend>
            <fieldset class="form-group">
                 <label for="number">Number</label>
                 <form:input path="number" type="text" class="form-control" id="number" placeholder="Enter number"/>
            </fieldset>
            <fieldset class="form-group">
                 <label for="owner">Owner</label>
                 <form:input path="owner" type="text" class="form-control" id="owner" placeholder="Enter owner"/>
            </fieldset>
            <fieldset class="form-group">
                 <label for="rooms">Rooms Quantity</label>
                 <form:input path="rooms" type="text" class="form-control" id="rooms" placeholder="Enter rooms"/>
            </fieldset>
            <fieldset class="form-group">
                 <label for="floor">Floor</label>
                 <form:input path="floor" type="text" class="form-control" id="floor" placeholder="Enter floor"/>
            </fieldset>
       </div>
       <form:hidden path="house.id"/>
<input type="submit" class="btn btn-primary" value="Add Apartment"/>
</form:form>
