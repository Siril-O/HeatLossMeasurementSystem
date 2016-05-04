<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Manage measurementSection info</h1>
<table class="table table-striped">
    <tr>
        <td>OrdinalNumber</td>
        <td>${measurementSection.ordinalNumber}</td>
    </tr>
    <tr>
        <td>Pipe</td>
        <td>${measurementSection.pipe.ordinalNumber}</td>
    </tr>
    <tr>
        <td>Apartment</td>
        <td>${measurementSection.apartment.number}</td>
    </tr>
    <tr>
        <td>House</td>
        <td>${measurementSection.pipe.house.address.city} ${measurementSection.pipe.house.address.street} ${measurementSection.pipe.house.address.houseNumber}</td>
    </tr>
    <tr>
        <td>SectionType</td>
        <td>${measurementSection.sectionType}</td>
    </tr>
    <tr>
        <td>ModuleType</td>
        <td>${measurementSection.moduleType}</td>
    </tr>
</table>
<table class="table table-striped">
    <tr>
    <th>SensorModel</th>
    <th>OrdinalNumber</th>
    <th>SensorType</th>
    </tr>
    <c:forEach items="${measurementSection.sensors}" var="sensor">
    <tr>
        <td>${sensor.sensorModel.name}</td>
        <td>${sensor.ordinalNumber}</td>
        <td>${sensor.sensorType}</td>
    </tr>
    </c:forEach>
</table>