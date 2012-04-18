<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block index-content">
    <form>
        <div>
            <h3><fmt:message key="label.hangul" /></h3>
            <div>
                <textarea id="hangul" cols=40 rows=4></textarea>
            </div>
            <h3><fmt:message key="label.roman" /></h3>
            <div>
                <textarea id="roman" cols=40 rows=4></textarea>
            </div>
        </div>
    </form>
</div>