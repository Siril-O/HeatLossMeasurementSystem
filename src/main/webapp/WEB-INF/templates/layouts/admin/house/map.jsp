<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <div id="map" style="height:400px"></div>
    <h1>All Houses</h1>
    <a href="house/create" class="btn btn-primary">Register new House</a>
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
              <form method="POST" action="house/manage">
                <input type="hidden" name="houseId" value="${house.id}">
                <input type="submit" value="Manage house" class="btn btn-primary">
              </form>
              <form method="GET" action="report/power/house">
                <input type="hidden" name="houseId" value="${house.id}">
                <input type="submit" value="Report" class="btn btn-primary">
              </form>
              </td>
        </tr>
        </c:forEach>
      </table>
</div>

<script>
function initMap() {
  var myLatLng = {lat: 50.45, lng: 30.523333};

  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 12,
    center: myLatLng
  });

<c:forEach items="${houses}" var="house">

  var marker = new google.maps.Marker({
    position: {lat: ${house.address.lat}, lng: ${house.address.lng}},
    title: 'Select house!',
    url: '/HeatLossSystem/report/power/house?houseId=${house.id}'
  });
  marker.setMap(map);
  google.maps.event.addListener(marker, 'click', function() {
      window.location.href = this.url;
  });
  </c:forEach>
}

    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=${APIkey}&signed_in=true&callback=initMap"></script>
