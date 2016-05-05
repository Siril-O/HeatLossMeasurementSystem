<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
		<div id="linechart_material"></div>
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
          <form method="POST" action="./manage">
            <input type="hidden" name="houseId" value="${house.id}">
            <input type="submit" value="Reports of Power Consumptions" class="btn btn-primary">
          </form>
          </td>
    </tr>
    </c:forEach>
  </table>
</div>
		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['line']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date(time)');
            	  data.addColumn('number', 'Power(Wat)');
            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '<fmt:formatDate pattern="HH:mm" value="${entry.key}" />', ${entry.value} ],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Power consumption of heat  in the building',
            		          subtitle: 'in wat'
            		        },
            		        width: 900,
            		        height: 500
            		      };

            		      var chart = new google.charts.Line(document.getElementById('linechart_material'));

            		      chart.draw(data, options);
              }
            </script>