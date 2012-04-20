<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="block index-content">
    <form:form modelAttribute="tweetForm">
        <form:hidden id="user_id" path="userId" />
        <form:hidden id="locale" path="locale" />
        <div>
            <div class="block">
                <fmt:message key="index.label.hangul" />
            </div>
            <div class="block">
                <textarea id="hangul" cols=40 rows=4></textarea>
            </div>
            
            <c:if test="${not empty tweetForm.userId}">
                <div class="block tweet-command-block">
                    <input 
                        id="tweet-button"
                        class="command-button"
                        type="button"
                        value="<fmt:message key="button.tweet" />"
                    />
                </div>
            </c:if>    
                
            <div class="block">
                <fmt:message key="index.label.alphabet" />
            </div>
            <div class="block">
                <textarea id="roman" cols=40 rows=4></textarea>
            </div>
        </div>
    </form:form>
            
    <%-- insert the alphabet table --%>
    <tiles:insertTemplate 
        template="/WEB-INF/views/_alphabet.jsp"
    />
    
</div>