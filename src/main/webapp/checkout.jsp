<%@include file="jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="jspf/header.jspf" %>

<!--banner-->
<div class="banner-top">
    <div class="container">
        <h1>Checkout</h1>
        <em></em>

        <h2><a href="index.jsp">Home</a><label>/</label>Checkout</a></h2>
    </div>
</div>
<!--login-->
<script>$(document).ready(function (c) {
    $('.close1').on('click', function (c) {
        $('.cart-header').fadeOut('slow', function (c) {
            $('.cart-header').remove();
        });
    });
});


</script>
<script>$(document).ready(function (c) {
    $('.close2').on('click', function (c) {
        $('.cart-header1').fadeOut('slow', function (c) {
            $('.cart-header1').remove();
        });
    });
});


</script>
<script>$(document).ready(function (c) {
    $('.close3').on('click', function (c) {
        $('.cart-header2').fadeOut('slow', function (c) {
            $('.cart-header2').remove();
        });
    });
});


</script>
<div class="container">
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
                    <input id="countProduct" type="number" onchange="checkPositive(); setBasketItemCount('setCount', ${entry.key.id}, this)" value="${entry.value}">
                </td>
                <td>
                    <p><span id="totalPrice${entry.key.id}">$${sessionScope.basket.countTotalPrice(entry.key)}</span></p>
                </td>
                <td>
                    <button class="btn" onclick="basketService('delete',${entry.key.id})"><img src="images/close.png">
                    </button>
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

    <div class="produced">
        <a href="proceedOrder" class="hvr-skew-backward">Produced To Buy</a>
    </div>
    <div>
        <span>${sessionScope.basketErrorMessage}</span>
    </div>
</div>

<script>
    function setBasketItemCount(condition, id, element) {
        var bodyParameters = {'condition': condition, 'id': id, 'counter': element.value};
        $.post("basketService", bodyParameters, function (data) {
            $('#basketItems').html('items in basket: ' + data.itemsCount);
            $('#basketPrice').html('$' + data.itemsPrice);
            $('#totalPrice'+id).html(data.productPrice);
        });
    }
</script>

<!--//login-->
<!--brand-->
<div class="container">
    <div class="brand">
        <div class="col-md-3 brand-grid">
            <img src="images/ic.png" class="img-responsive" alt="">
        </div>
        <div class="col-md-3 brand-grid">
            <img src="images/ic1.png" class="img-responsive" alt="">
        </div>
        <div class="col-md-3 brand-grid">
            <img src="images/ic2.png" class="img-responsive" alt="">
        </div>
        <div class="col-md-3 brand-grid">
            <img src="images/ic3.png" class="img-responsive" alt="">
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!--//brand-->
</div>

</div>
<!--//content-->
<!--//footer-->
<%@include file="jspf/footer.jspf" %>

<!--//footer-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="js/simpleCart.min.js"></script>
<!-- slide -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>