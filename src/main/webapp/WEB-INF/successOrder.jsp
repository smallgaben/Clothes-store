<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>

<div class="container">
<h2>Your order:</h2>
    <table class="table">
                <tbody>
                <c:forEach var="item" items="${successOrder.itemsList}">
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
                </tbody>
    </table>
</div>

 <h2>is successfully proceed.</h2>



<%@include file="/jspf/footer.jspf" %>