<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form:form commandName="house" method="POST" action="./">
     <fieldset class="form-group">
        <label for="w-country">Country</label>
        <form:input path="address.country" type="text" class="form-control" id="w-country" placeholder="Enter country"/>
     </fieldset>
     <fieldset class="form-group">
          <label for="city">City</label>
          <form:input path="address.city" type="text" class="form-control" id="city" placeholder="Enter city"/>
     </fieldset>
     <fieldset class="form-group">
            <label for="street">Country</label>
            <form:input path="address.street" type="text" class="form-control" id="street" placeholder="Enter street"/>
     </fieldset>
     <fieldset class="form-group">
                  <label for="houseNumber">Country</label>
                  <form:input path="address.houseNumber" type="text" class="form-control" id="houseNumber" placeholder="Enter houseNumber"/>
      </fieldset>
<input type="submit" class="btn btn-primary"/>

</form:form>

  <script>
  $(document).ready(function() {

	$('#w-country').autocomplete({
		serviceUrl: '${pageContext.request.contextPath}/utils/country',
		paramName: "tagName",
		delimiter: ",",
	   transformResult: function(response) {

		return {
		  //must convert json to javascript object before process
		  suggestions: $.map($.parseJSON(response), function(item) {

		      return { value: item.tagName, data: item.id };
		   })

		 };

            }

	 });

  });
  </script>