<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block menu-content">
    <div class="menu-bar">
        <div class="menu-bar-left">
            <span>
                <a href="<c:url value="/" />"><fmt:message key="menu.index" /></a>
            </span>
            <span>
                <a href="<c:url value="/login.html" />"><fmt:message key="menu.login" /></a>
            </span>
            <span>
                <a href="<c:url value="/logout.html" />"><fmt:message key="menu.logout" /></a>
            </span>
        </div>
        <div class="menu-bar-right">
        </div>
    </div>
</div>