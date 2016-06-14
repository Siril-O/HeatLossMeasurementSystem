<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top:15px;">
<form action="" id="report_form">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" id="startDate_report_input"
    value="<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>"
    onchange="changeDate(startDate_report_input, startDate_apartment_input )"/>
  <input type="date" name="endDate"  id="endDate_report_input"
    value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />"
    onchange="changeDate(endDate_report_input, endDate_apartment_input)"/>
  <input type="hidden" name="houseId" value="${house.id}" />
  <input type="submit" value="Power Report" class="btn btn-primary" id="power_report_btn">
  <input type="submit" value="Energy Report" class="btn btn-primary" id="energy_report_btn"/>
</form>
</div>
		<div id="linechart_material" ></div>

		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['corechart']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date');
            	  data.addColumn('number', 'Input Energy');
            	  data.addColumn('number', 'Consumed Energy');
            	  data.addColumn('number', 'Loss Energy');

            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '${entry.date}', ${entry.input}, ${entry.consumed}, ${entry.loss}],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Energy in KJoules',
            		          subtitle: 'in wat'
            		        },
            		        width: 900,
            		        height: 600
            		      };

            		      var chart = new google.visualization.ColumnChart(document.getElementById('linechart_material'));

            		      chart.draw(data, options);
              }
            </script>