<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top:15px;">
<h2>Measurement Module Power Chart</h2>
<form action="" id="module_build_report_form">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" id="startDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>"/>
  <input type="date" name="endDate"  id="endDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />" />
  <input type="hidden" name="moduleId" value="${module.id}" />
  <input type="submit" value="Power Report" class="btn btn-primary" id="power_report_btn">
  <input type="submit" value="Energy Report" class="btn btn-primary" id="energy_report_btn"/>
</form>
</div>

		<div id="linechart_material"></div>

		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['corechart']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date');
            	  data.addColumn('number', 'Power');
            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '<fmt:formatDate pattern="dd.MM.hh:mm:ss" value="${entry.key}" />', ${entry.value} ],
            	  </c:forEach>
            	  ]);

            	  var options = {
            	            curveType: 'function',
            		        chart: {
            		          title: 'Power of Measurement Module',
            		          subtitle: 'in Kwat'
            		        },
            		        width: 900,
            		        height: 500
            		      };

            		      var chart = new google.visualization.LineChart(document.getElementById('linechart_material'));

            		      chart.draw(data, options);
              }
            </script>