<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
  <table class="table table-striped">
      <thead>
  <tr>
      <th>Country</th>
      <th>City</th>
      <th>Street</th>
      <th>House number</th>
  </tr>
      </thead>
      <tbody>

    <c:forEach items="${houses}" var="house">
    <tr>
          <td>${house.address.country}</td>
          <td>${house.address.city}</td>
          <td>${house.address.street}</td>
          <td>${house.address.houseNumber}</td>
    </tr>
    </c:forEach>
        </tbody>

  </table>
</div>
