<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top:15px;">
<form action="">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}" />">
  <input type="date" name="endDate"   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />">
  <input type="hidden" name="houseId" value="${house.id}" />
  <input type="submit" value="build Report">
</form>
</div>
		<div id="linechart_material" ></div>

		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['corechart']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date');
            	  data.addColumn('number', 'Input Power');
            	  data.addColumn('number', 'Consumed Power');
            	  data.addColumn('number', 'Loss Power');

            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '${entry.date}', ${entry.input}, ${entry.consumed}, ${entry.loss}],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Heat Power Losses by House',
            		          subtitle: 'in wat'
            		        },
            		        width: 900,
            		        height: 600
            		      };

            		      var chart = new google.visualization.AreaChart(document.getElementById('linechart_material'));

            		      chart.draw(data, options);
              }
            </script>