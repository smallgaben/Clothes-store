<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty sessionScope.user}">
<h2>PERSONAL PAGE</h2>
<p>Username: ${sessionScope.user.username}</p>
<p>Avatar:</p></br>
<img src="<c:url value="file/avatars/${sessionScope.user.imagePath}"/>" width="300" height="300"/></br>
</c:if>