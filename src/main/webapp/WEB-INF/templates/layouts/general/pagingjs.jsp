 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

               <div class="paging-container">
                  <ul class="pagination">
                      <c:forEach begin="1" end="${pagesQuantity}" var="i">
                          <li><a href="#">${i}</a></li>
                  </c:forEach>
                  </ul>
                </div>