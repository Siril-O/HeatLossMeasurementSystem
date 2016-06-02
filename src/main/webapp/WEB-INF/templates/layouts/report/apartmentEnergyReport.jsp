<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>

<div style="margin-top:15px;">
<form action="">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${now}" />">
  <input type="date" name="endDate"   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${now}" />">
  <input type="submit" value="build Report">
</form>
</div>
		<div id="chart_div"></div>

		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['corechart']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date');
            	  data.addColumn('number', 'Energy');
            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '<fmt:formatDate pattern="dd.MM.yyyy" value="${entry.key}" />', ${entry.value} ],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Energy Consumed by Apartment',
            		          subtitle: 'in KWat'
            		        },
            		        width: 900,
            		        height: 500
            		      };

                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));

            		      chart.draw(data, options);
              }
            </script>