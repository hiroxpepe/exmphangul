<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="block index-content">
    <form>
        <div>
            <div class="block">
                <fmt:message key="label.hangul" />
            </div>
            <div class="block">
                <textarea id="hangul" cols=40 rows=4></textarea>
            </div>
            <div class="block">
                <fmt:message key="label.roman" />
            </div>
            <div class="block">
                <textarea id="roman" cols=40 rows=4></textarea>
            </div>
        </div>
    </form>
</div>