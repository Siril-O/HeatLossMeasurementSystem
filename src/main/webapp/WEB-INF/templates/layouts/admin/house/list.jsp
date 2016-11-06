<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="createHouseFormUrl" value="/house/create"/>
<c:url var="manageHouseUrl" value="/house/manage"/>

<div class="container">
<h1>All Houses</h1>
<a href="${createHouseFormUrl}" class="btn btn-primary">Register new House</a>
  <table class="table table-striped">
  <tr>
      <th>Country</th>
      <th>City</th>
      <th>Street</th>
      <th>House number</th>
      <th>Pipe System</th>
      <th>Actions</th>
  </tr>
    <c:forEach items="${houses}" var="house">
    <tr>
          <td>${house.location.country}</td>
          <td>${house.location.city}</td>
          <td>${house.location.street}</td>
          <td>${house.location.houseNumber}</td>
          <td>${house.pipeSystem}</td>
          <td>
          <form method="GET" action="${manageHouseUrl}">
            <input type="hidden" name="houseId" value="${house.id}">
            <input type="submit" value="Manage house" class="btn btn-primary">
          </form>
          </td>
    </tr>
    </c:forEach>
  </table>
</div>
