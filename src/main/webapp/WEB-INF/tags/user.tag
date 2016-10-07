<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${pageContext.request.getLocale()}"/>
<fmt:setBundle basename="resource"/>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <li><a href="personal.jsp">${sessionScope.user.username}</a></li>
        <li><a href="logout">Logout</a></li>
    </c:when>
    <c:otherwise>
                <li><a href="login.jsp"><fmt:message key="login"/></a></li>
                <li><a href="registerUser"><fmt:message key="register"/></a></li>
                <li><a href="checkout.jsp"><fmt:message key="checkout"/></a></li>
    </c:otherwise>
</c:choose>