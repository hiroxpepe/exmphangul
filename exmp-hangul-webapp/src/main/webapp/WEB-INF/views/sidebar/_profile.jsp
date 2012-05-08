<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="profile-content">
    <div id="user-profile" class="profile">
        <table>
            <tr>
                <td>
                    <img src="${profileModel.imageURL}" width="48" height="48" border="0">
                </td>
                <td>
                    <div class="profileName"><b>${profileModel.screenName}</b></div>
                    <div id="user-show-profile" class="show-profile"><fmt:message key="sidebar.label.show.profile" /></div>
                    <div id="user-description" class="description">${profileModel.description}</div>
                </td>
            </tr>
        </table>
    </div>
</div>