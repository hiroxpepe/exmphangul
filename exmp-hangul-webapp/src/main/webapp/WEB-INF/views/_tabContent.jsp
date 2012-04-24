<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="tab-content">
    
    <ul>
        <li><a href="#tab1"><fmt:message key="index.label.entry" /></a></li>
        <li><a href="#tab2"><fmt:message key="index.label.setting" /></a></li>
    </ul>
    
    <div id="tab1" class="tab">
        <div id="tweet-list-block"
            class="tweet-list-content">
        </div>
    </div>
    
    <div id="tab2" class="tab">
        <div class="setting-content">
            <table class="setting-table">
                <tr>
                    <td class="label-td">
                        <label for="response_list_mode">
                            <fmt:message key="index.label.setting.list.mode" />
                        </label>
                    </td>
                    <td class="input-td">
                        <%--<form:input id="response-list-mode" path="responseListMode" />--%>
                        <form:select 
                            id="response-list-mode"
                            path="responseListMode"
                            items="${responseListModeList}"
                        />
                    </td>
                </tr>
                <tr>
                    <td class="label-td">
                        <label for="user_list_name">
                            <fmt:message key="index.label.setting.list.name" />
                        </label>
                    </td>
                    <td class="input-td">
                        <%--<form:input id="user-list-name" path="userListName" />--%>
                        <form:select 
                            id="user-list-name"
                            path="userListName"
                            items="${userListNameList}"
                        />
                    </td>
                </tr>
            </table>
            <div class="setting-command-block">
                <input id="setting-button"
                       class="command-button"
                       type="button" 
                       value="<fmt:message key="button.setting" />"
                 />
            </div>
        </div>
    </div>
</div>