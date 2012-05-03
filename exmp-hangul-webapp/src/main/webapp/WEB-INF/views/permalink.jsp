<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="block permalink-content">
    
    <form:form  id="tweet-form" modelAttribute="tweetForm">
        <form:hidden id="user-id" path="userId" />
        <form:hidden id="screen-name" path="screenName" />
        <form:hidden id="locale" path="locale" />
    </form:form>
    
    <div class="permalink-header">
        <div class="permalink-date">${year} - ${month} - ${day}</div>
    </div>
    <div class="permalink-wrapper">
        <table>
            <c:forEach var="tweetModel" items="${tweetModelList}" varStatus="status">
            <tr>
                <td>
                    <p class="permalink-text">
                        <c:out value="${tweetModel.text}" />
                    </p>
                    <p class="permalink-detail">
                        <span>by <c:out value="${tweetModel.userName}" /></span>
                        <span> at <c:out value="${tweetModel.created}" /></span>
                    </p>
                <td>
            <tr>
            </c:forEach>
        </table>
    </div>
</div>