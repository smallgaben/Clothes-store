<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            User: ${sessionScope.order.user.username}
        </div>
        <div class="col-md-3">
            Address: ${sessionScope.order.address}
        </div>
        <div class="col-md-3">
            Credit card: ${sessionScope.order.creditCard}
        </div>
        <div class="col-md-3">
            Created time: ${sessionScope.order.timestamp}
        </div>
    </div>
    <div class="row">
        Ordered items
        <table class="table">
            <tbody>
            <c:forEach var="item" items="${sessionScope.order.itemsList}">
                <tr>
                    <td>
                        <img width="70" height="70" src="file/product/${item.product.image_path}"/>
                    </td>
                    <td>
                        <p>${item.product.name}</p>
                    </td>
                    <td>
                        <p>$${item.productPrice}</p>
                    </td>
                    <td>
                        <p>${item.productCount}</p>
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
    </div>
    <div class="row">
        <a class="hvr-skew-backward" href="confirmOrder">Confirm</a>
        <a class="hvr-skew-backward" href="cancelOrder">Cancel</a>
    </div>
</div>

<%@include file="/jspf/footer.jspf" %>
</body>
</html>