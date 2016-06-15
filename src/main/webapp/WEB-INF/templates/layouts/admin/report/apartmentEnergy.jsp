<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top:15px;">
   <h2>Energy Report</h2>
   <h3>Apartment:${apartment.id} House ${apartment.house.address.city} ${apartment.house.address.street} ${apartment.house.address.houseNumber}</h3>
<form action="" id="apartment_build_report_form">
  <h3>Choose time interval</h3>
  <input type="date" name="startDate" id="startDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>"/>
  <input type="date" name="endDate"  id="endDate_report_input" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />" />
  <input type="hidden" name="apartmentId" value="${apartment.id}" />
  <input type="submit" value="Power Report" class="btn btn-primary" id="power_report_btn">
  <input type="submit" value="Energy Report" class="btn btn-primary" id="energy_report_btn"/>
</form>
</div>
		<div id="chart_div"></div>
				<h2 align="center" >Apartment measurement modules</h2>
        <table class="table table-striped" style="width:500px;margin: 0 auto;">
                      <tr>
                      <th>Pipe</th>
                      <th>Action</th>
                      </tr>
                       <c:forEach items="${apartment.measurementModules}" var="module">
                    <tr class="paging_content">
                      <td>${module.pipe.ordinalNumber}</td>
                      <td>
                      <form method="GET" action="./module" class="module_report_form">
                        <input type="hidden" name="moduleId" value="${module.id}">
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
            	  data.addColumn('number', 'Energy');
            	  data.addRows([
            	  <c:forEach items="${dataMap.result}" var="entry">
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