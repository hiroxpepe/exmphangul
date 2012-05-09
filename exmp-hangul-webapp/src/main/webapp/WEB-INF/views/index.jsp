<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="block index-content">
        
    <%-- insert the alphabet table --%>
    <tiles:insertTemplate 
        template="/WEB-INF/views/content/_alphabetGrid.jsp"
    />
    
    <form:form  id="tweet-form" modelAttribute="tweetForm">
        <form:hidden id="user-id" path="userId" />
        <form:hidden id="screen-name" path="screenName" />
        <form:hidden id="locale" path="locale" />
        
        <div id="input-content-wrapper" class="content-wrapper">
            <input type="hidden" id="reply-status-id" value="">
            <input type="hidden" id="reply-user-name" value="">
            
            <div class="block">
                <fmt:message key="index.label.hangul" />
            </div>
            <div class="block">
                <%--caret start=<span class="caretStart"></span>--%>
                <input type="hidden" id="caretStart" value="">
                <textarea id="tweet"></textarea>
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
                <textarea id="alphabet"></textarea>
            </div>
            
            <div class="block tweet-command-block">
                <input 
                    id="alphabet-grid-button"
                    class="command-button"
                    type="button"
                    value="<fmt:message key="button.alphabet.grid" />"
                />
            </div>
            
        </div>
            
        <c:if test="${not empty tweetForm.userId}">
            <%-- insert the tab content template. --%>
            <tiles:insertTemplate 
            template="/WEB-INF/views/content/_tabContent.jsp"
            />
        </c:if>
        
    </form:form>
</div>