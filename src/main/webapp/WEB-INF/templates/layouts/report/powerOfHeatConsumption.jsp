<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<div id="linechart_material"></div>

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