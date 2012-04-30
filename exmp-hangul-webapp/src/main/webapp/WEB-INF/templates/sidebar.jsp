<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block sidebar-content">
    
    <%-- <c:if test="${not empty tweetForm.userId}"> --%>
        <div class="calendar-content">
            <div id="tweet-calendar" class="calendar"></div>
        </div>

        <div class="tagcrowd-content">
            <div id="tweet-tagcrowd"  class="tagcrowd"></div>
        </div>
    <%-- </c:if> --%>
    
</div>