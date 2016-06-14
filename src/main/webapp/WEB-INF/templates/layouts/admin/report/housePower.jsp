<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="urlParams" value="houseId=${house.id}&" scope="request"/>

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

${pagingUrl}
		<table class="table table-striped" style="width:500px">
              <tr>
              <th>Number</th>
              <th>action</th>

              </tr>
               <c:forEach items="${apartments}" var="apartment">
               <tr>
              <td>${apartment.number}</td>
              <td>
              <form method="GET" action="./apartment" id="apartment_report_form">
                <input type="hidden" name="apartmentId" value="${apartment.id}">
                <input type="hidden" name="startDate" id ="startDate_apartment_input" value="" />
                <input type="hidden" name="endDate" id="endDate_apartment_input" value="" />
                <input type="submit" value="Report" class="btn btn-primary">
              </form>
              </td>
              </tr>
              </c:forEach>
        </table>


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



              function changeDate(input, output){
                 let input = document.getElementById(input);
                 let output = document.getElementById(output);
                output.value = input.value;
              };

              $(function() {
                  $('#apartment_report_form').submit(function() {
                  changeDate('startDate_report_input', 'startDate_apartment_input');
                  changeDate('endDate_report_input', 'endDate_apartment_input');
                      return true;
                  });
              });
            </script>