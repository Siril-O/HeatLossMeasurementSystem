<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
		<div id="chart_div"></div>
<h1>All Houses</h1>
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
          <form method="POST" action="./energy">
            <input type="hidden" name="houseId" value="${house.id}">
            <input type="submit" value="Reports of Energy Consumptions" class="btn btn-primary">
          </form>
          </td>
    </tr>
    </c:forEach>
  </table>
</div>
		<script type="text/javascript">
            google.load("visualization", "1", {packages:["corechart"]});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date(time)');
            	  data.addColumn('number', 'Consumed Energy(KJoule)');
            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '${entry.key.pipe.id}:#${entry.key.ordinalNumber}', ${entry.value} ],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Energy consumed  heat in the building in time Period',
            		          subtitle: 'in KJoules'
            		        },
            		        width: 900,
            		        height: 500
            		      };

      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
      chart.draw(data, options);

              }
            </script>