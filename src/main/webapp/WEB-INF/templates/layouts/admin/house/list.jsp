<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
<h1>All Houses</h1>
<a href="./create" class="btn btn-primary">Register new House</a>
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
          <td>${house.address.country}</td>
          <td>${house.address.city}</td>
          <td>${house.address.street}</td>
          <td>${house.address.houseNumber}</td>
          <td>${house.pipeSystem}</td>
          <td>
          <form method="GET" action="./manage">
            <input type="hidden" name="houseId" value="${house.id}">
            <input type="submit" value="Manage house" class="btn btn-primary">
          </form>
          </td>
    </tr>
    </c:forEach>
  </table>
</div>
