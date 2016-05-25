<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <div id="map" style="height:400px"></div>
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
    url: '/HeatLossSystem/report/?houseId=${house.id}'
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
