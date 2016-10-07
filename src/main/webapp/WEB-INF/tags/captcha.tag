<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<img src="<c:url value="drawCaptcha?captchaId=${captchaId}"/>">
<c:if test="${hiddenField}">
<input type="text" name="captchaId" value="${captchaId}" hidden>
</c:if>