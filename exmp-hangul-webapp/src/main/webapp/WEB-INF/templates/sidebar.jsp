<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="block sidebar-content">
    
    <c:if test="${not empty tweetForm.userId}">
        <%-- insert the profile content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/_profile.jsp"
        />
        
        <%-- insert the calendar content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/_calendar.jsp"
        />
        
        <%-- insert the tagcrowd content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/_tagcrowd.jsp"
        />
        
        <%-- insert the recent content --%>
        <tiles:insertTemplate 
            template="/WEB-INF/views/_recent.jsp"
        />
    </c:if>
    
</div>