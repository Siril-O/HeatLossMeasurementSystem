<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top:15px;">
<h2>House Power Chart</h2>
<form action="" id="report_form">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" id="startDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>"/>
  <input type="date" name="endDate"  id="endDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />" />
  <input type="hidden" name="houseId" value="${house.id}" />
  <input type="submit" value="Power Report" class="btn btn-primary" id="power_report_btn">
  <input type="submit" value="Energy Report" class="btn btn-primary" id="energy_report_btn"/>
</form>
</div>
		<div id="linechart_material" ></div>
		<h2 align="center" >House Apartments</h2>
        <table class="table table-striped" style="width:500px;margin: 0 auto;">
              <tr>
              <th>Number</th>
              <th>action</th>
              </tr>
               <c:forEach items="${house.apartments}" var="apartment">
               <tr class="paging_content">
              <td>${apartment.number}</td>
              <td>
              <form method="GET" action="./apartment" class="apartment_report_form">
                <input type="hidden" name="apartmentId" value="${apartment.id}">
                <input type="hidden" name="startDate" class ="startDate_apartment_input" value="" />
                <input type="hidden" name="endDate" class="endDate_apartment_input" value="" />
                <input type="submit" value="Report" class="btn btn-primary">
              </form>
              </td>
              </tr>
              </c:forEach>
        </table>
                <jsp:include page="/WEB-INF/templates/layouts/general/pagingjs.jsp"/>
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
            	  <c:forEach items="${dataMap.reportEntries}" var="entry">
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
              };
            </script>