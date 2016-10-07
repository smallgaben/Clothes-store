<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>

<!--banner-->
<div class="banner-top">
    <div class="container">
        <h1>Single</h1>
        <em></em>

        <h2><a href="index.jsp">Home</a><label>/</label>Single</a></h2>
    </div>
</div>
<div class="single">

    <div class="container">
        <div class="col-md-9">
            <div class="col-md-5 grid">
                <div class="flexslider">
                    <ul class="slides">
                        <li data-thumb="file/product/${product.image_path}">
                            <div class="thumb-image"><img src="file/product/${product.image_path}" data-imagezoom="true"
                                                          class="img-responsive"></div>
                        </li>
                        <li data-thumb="file/product/${product.image_path}">
                            <div class="thumb-image"><img src="file/product/${product.image_path}" data-imagezoom="true"
                                                          class="img-responsive"></div>
                        </li>
                        <li data-thumb="file/product/${product.image_path}">
                            <div class="thumb-image"><img src="file/product/${product.image_path}" data-imagezoom="true"
                                                          class="img-responsive"></div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-md-7 single-top-in">
                <div class="span_2_of_a1 simpleCart_shelfItem">
                    <h3>${product.name}</h3>

                    <div class="price_single">
                        <span class="reducedfrom item_price">$${product.price}</span>
                        <a href="#">click for offer</a>

                        <div class="clearfix"></div>
                    </div>
                    <h4 class="quick">Quick Overview:</h4>

                    <p class="quick_desc"> Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet
                        doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; es</p>

                    <div class="col-md-12">
                        <div class="row">
                            <input onchange="checkPositive()" type="number" id="countProduct"/>
                            <button onclick="basketService('addCount', ${product.id})" class="btn btn-primary">Add to
                                cart
                            </button>
                        </div>

                    </div>

                    <!--&lt;!&ndash;quantity&ndash;&gt;-->
                    <!--<script>-->
                    <!--$('.value-plus').on('click', function(){-->
                    <!--var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)+1;-->
                    <!--divUpd.text(newVal);-->
                    <!--});-->

                    <!--$('.value-minus').on('click', function(){-->
                    <!--var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)-1;-->
                    <!--if(newVal>=1) divUpd.text(newVal);-->
                    <!--});-->


                    <!--</script>-->
                    <!--&lt;!&ndash;quantity&ndash;&gt;-->


                    <div class="clearfix"></div>
                </div>

            </div>
            <div class="clearfix"></div>
            <!---->
            <div class="clearfix"></div>
        </div>
        <!---->
    </div>
    <!----->
    <div class="clearfix"></div>
</div>
</div>


</div>
<!--//content-->
<!--//footer-->
<%@include file="/jspf/footer.jspf" %>

<!--//footer-->
<script src="js/imagezoom.js"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script defer src="js/jquery.flexslider.js"></script>
<link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen"/>

<script>
$(window).load(function() {
  $('.flexslider').flexslider({
    animation: "slide",
    controlNav: "thumbnails"
  });
});

</script>

<script src="js/simpleCart.min.js"></script>
<!-- slide -->
<script src="js/bootstrap.min.js"></script>


</body>
</html>