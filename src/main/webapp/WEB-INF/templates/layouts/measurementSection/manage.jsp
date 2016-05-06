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
</table>
<table class="table table-striped">
    <c:set var="module" value="${measurementSection.measurementModule}"/>
    <tr>
    <th>SensorModel</th>
    <th>SensorType</th>
    </tr>
    <tr>
        <td>${module.inputAdditional.name} ${module.inputAdditional.maker}</td>
        <td>Input Additional</td>
    </tr>
    <tr>
        <td>${module.input.name} ${module.input.maker}</td>
        <td>Input</td>
    </tr>
    <tr>
        <td>${module.flow.name} ${module.flow.maker}</td>
        <td>Flow</td>
    </tr>
    <tr>
        <td>${module.output.name} ${module.output.maker}</td>
        <td>Output</td>
    </tr>
    <tr>
        <td>${module.outputAdditional.name} ${module.outputAdditional.maker}</td>
        <td>Output Additional</td>
    </tr>
</table>