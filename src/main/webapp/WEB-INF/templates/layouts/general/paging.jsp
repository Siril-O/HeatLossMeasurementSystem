 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <div class="paging-container">
  <ul class="pagination">
      <c:forEach begin="1" end="${pagesQuantity}" var="i">
      <c:set var="pageOffset" value="${(i - 1) * limit}" />
          <li <c:if test="${offset == (pageOffset)}"> class="active" </c:if> >
          <a  href="?offset=${pageOffset}">${i} </a></li>
  </c:forEach>
  </ul>
  </div>