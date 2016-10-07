<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>

<table class="table">
    <thead>
    <tr>
        <td>Image</td>
        <td>Name</td>
        <td>Price</td>
        <td>Number of items</td>
        <td>Total price</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${sessionScope.basket.readItems()}">
        <tr id="${entry.key.id}">
            <td>
                <img width="70" height="70" src="file/product/${entry.key.image_path}"/>
            </td>
            <td>
                <p>${entry.key.name}</p>
            </td>
            <td>
                <p>$${entry.key.price}</p>
            </td>
            <td>
                <p>${entry.value}</p>
            </td>
            <td>
                <p><span id="totalPrice">$${sessionScope.basket.countTotalPrice(entry.key)}</span></p>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <h2>Final price: ${sessionScope.basket.countTotalPrice()}</h2>
        </td>
    </tr>
    </tbody>
</table>
<div class="container">
    <div class="col-md-6 contact-top">
        <form action="proceedOrder" method="post">
            <div>
                <span>Your Address </span>
                <input name="address" type="text" value="" required>
                <span>${sessionScope.errors.get("addres")}</span>
            </div>
            <div>
                <span>Your Credit card </span>
                <input name="creditCard" type="number" value="" required>
                <span>${sessionScope.errors.get("addres")}</span>
            </div>
            <label class="hvr-skew-backward">
                <input type="submit" value="Proceed">
            </label>
            <a class="hvr-skew-backward" href="${pageContext.request.contextPath}/checkout.jsp">Cancel</a>
        </form>
    </div>
</div>

<%@include file="/jspf/footer.jspf" %>
</body>
</html>
