<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form commandName="house" method="POST">
<form:input path="address.country" type="text"/>
<form:input path="address.city" type="text"/>
<form:input path="address.street" type="text"/>
<form:input path="address.houseNumber" type="number"/>
<form:input type="submit"/>
</form:form>
