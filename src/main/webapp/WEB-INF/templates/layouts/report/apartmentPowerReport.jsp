<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>

<div style="margin-top:15px;">
<form action="">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${dataMap.startDate}" />">
  <input type="date" name="endDate"   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${dataMap.endDate}" />">
  <input type="hidden" name="apartmentId" value="${apartment.id}" />
  <input type="submit" value="build Report" class="btn btn-primary">
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
            	  <c:forEach items="${dataMap.result}" var="entry">
            	  [ '<fmt:formatDate pattern="dd.MM.hh:mm:ss" value="${entry.key}" />', ${entry.value} ],
            	  </c:forEach>
            	  ]);

            	  var options = {
            		        chart: {
            		          title: 'Power of Apartment',
            		          subtitle: 'in Kwat'
            		        },
            		        width: 900,
            		        height: 500
            		      };

            		      var chart = new google.visualization.AreaChart(document.getElementById('linechart_material'));

            		      chart.draw(data, options);
              }
            </script>