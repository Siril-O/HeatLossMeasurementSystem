
		<div id="linechart_material"></div>

		<script type="text/javascript">
            google.load('visualization', '1.1', {packages: ['line']});
            google.setOnLoadCallback(drawChart);

              function drawChart() {
            	  var data = new google.visualization.DataTable();
            	  data.addColumn('string', 'Date');
            	  data.addColumn('number', 'Power');
            	  data.addRows([
            	  <c:forEach items="${dataMap}" var="entry">
            	  [ '${entry.key.date}', ${entry.value} ],
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