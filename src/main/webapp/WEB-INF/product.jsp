<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>

<!--banner-->
<div class="banner-top">
    <div class="container">
        <h1>Products</h1>
        <em></em>

        <h2><a href="index.jsp">Home</a><label>/</label>Products</h2>
    </div>
</div>
<div class="col-md-3 col-md-offset-4">
    <div class="col-md-6 form-group">
        <label for="sort">Sort by:</label>
        <select name="sortBy" class="form-control" id="sort" form="filterForm">
            <option
            <c:if test="${'name_asc'==sortBy}">selected</c:if>
            value="name_asc">name A-Z
            </option>
            <option
            <c:if test="${'name_desc'==sortBy}">selected</c:if>
            value="name_desc">name Z-A
            </option>
            <option
            <c:if test="${'price_asc'==sortBy}">selected</c:if>
            value="price_asc">price small to big
            </option>
            <option
            <c:if test="${'price_desc'==sortBy}">selected</c:if>
            value="price_desc">price big to small
            </option>
            <option
            <c:if test="${'brand_asc'==sortBy}">selected</c:if>
            value="brand_asc">brand A-Z
            </option>
            <option
            <c:if test="${'brand_desc'==sortBy}">selected</c:if>
            value="brand_desc">brand Z-A
            </option>
            <option
            <c:if test="${'type_asc'==sortBy}">selected</c:if>
            value="type_asc">type A-Z
            </option>
            <option
            <c:if test="${'type_desc'==sortBy}">selected</c:if>
            value="type_desc">type Z-A
            </option>
        </select>
    </div>
    <div class="col-md-6 form-group">
        <label for="size">Size:</label>
        <select name="paginationSize" class="form-control" id="size" form="filterForm">
            <option
            <c:if test="${'2'==paginationSize}">selected</c:if>
            >2</option>
            <option
            <c:if test="${'5'==paginationSize}">selected</c:if>
            >5</option>
            <option
            <c:if test="${'7'==paginationSize}">selected</c:if>
            >7</option>
            <option
            <c:if test="${'10'==paginationSize}">selected</c:if>
            >10</option>
        </select>
    </div>
</div>

<!--content-->
<div class="product">
    <div class="container">
        <div class="col-md-9">
            <div class="mid-popular">
                <c:if test="${products.isEmpty()}">
                    Nothing Found
                </c:if>
                <div>
                    <ul class="pagination">
                        <c:forEach begin="1" end="${pagesNum}" varStatus="loop">
                            <li><a class="color5"
                                   href="listProducts?${queryString}&page=${loop.index}">${loop.index}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <c:forEach var="product" items="${products}">
                    <div class="col-md-6 item-grid1 simpleCart_shelfItem">
                        <div class=" mid-pop">
                            <div class="pro-img">
                                <img src="file/product/${product.image_path}" class="img-responsive" alt="">

                                <div class="zoom-icon ">
                                    <a class="picture" href="file/product/${product.image_path}" rel="title"
                                       class="b-link-stripe b-animate-go  thickbox"><i
                                            class="glyphicon glyphicon-search icon "></i></a>
                                    <a href="singleProduct?productId=${product.id}"><i
                                            class="glyphicon glyphicon-menu-right icon"></i></a>
                                </div>
                            </div>
                            <div class="mid-1">
                                <div class="women">
                                    <div class="women-top">
                                        <span>${product.category}</span>

                                        <p><a href="singleProduct?productId=${product.id}">${product.name}</a></p>
                                    </div>
                                    <div class="img item_add">
                                        <button onclick="basketService('add',${product.id})"><img src="images/ca.png"
                                                                                                  alt=""></button>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="mid-2">
                                    <p>${product.price}$</p>

                                    <div class="clearfix"></div>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:forEach>
                <div class="clearfix"></div>
                <div>
                    <ul class="pagination">
                        <c:forEach begin="1" end="${pagesNum}" varStatus="loop">
                            <li><a class="color5"
                                   href="listProducts?${queryString}&page=${loop.index}">${loop.index}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <form id="filterForm" class="form-group" method="get" action="listProducts" name="filterForm">
            <input name="page" value="1" hidden/>

            <div class="col-md-3 product-bottom">
                <!--categories-->
                <div class=" rsidebar span_1_of_left">
                    <h4 class="cate">Categories</h4>

                    <div class="col col-4">
                        <c:forEach var="category" items="${categories}">
                            <label class="checkbox"><input type="checkbox" value="${category}"
                                <c:if test="${checked_categories.contains(category)}">checked</c:if>
                                name="category"/><i></i>${category}</label>
                        </c:forEach>
                    </div>
                </div>
                <!--initiate accordion-->
                <script type="text/javascript">
                    $(function () {
                        var menu_ul = $('.menu-drop > li > ul'),
                                menu_a = $('.menu-drop > li > a');
                        menu_ul.hide();
                        menu_a.click(function (e) {
                            e.preventDefault();
                            if (!$(this).hasClass('active')) {
                                menu_a.removeClass('active');
                                menu_ul.filter(':visible').slideUp('normal');
                                $(this).addClass('active').next().stop(true, true).slideDown('normal');
                            } else {
                                $(this).removeClass('active');
                                $(this).next().stop(true, true).slideUp('normal');
                            }
                        });

                    });



                </script>
                <!--//menu-->
                <section class="sky-form ">
                    <div>
                        <label class="item4" for="name">Name: </label>
                        <input class="form-control" id="name" type="text" name="name" value="${name}"/>
                    </div>
                    <div>
                        <p>Price: </p><br/>

                        <div>
                            <label class="item4" for="from">From:</label>
                            <input class="form-control" id="from" name="priceFrom" type="number" value="${priceFrom}"/>
                        </div>
                        <div>
                            <label class="item4" for="to">To:</label>
                            <input class="form-control" id="to" name="priceTo" type="number" value="${priceTo}"/>
                        </div>
                    </div>
                </section>

                <!---->
                <section class="sky-form">
                    <h4 class="cate">Type</h4>

                    <div class="row row1 scroll-pane">
                        <div class="col col-4">
                            <c:forEach var="type" items="${types}">
                                <label class="checkbox"><input value="${type}" type="checkbox"
                                    <c:if test="${checked_types.contains(type)}">checked</c:if>
                                    name="type"><i></i>${type}</label>
                            </c:forEach>
                        </div>
                    </div>
                </section>
                <section class="sky-form">
                    <h4 class="cate">Brand</h4>

                    <div class="row row1 scroll-pane">
                        <div class="col col-4">
                            <c:forEach var="brand" items="${brands}">
                                <label class="checkbox"><input value="${brand}" type="checkbox"
                                    <c:if test="${checked_brands.contains(brand)}">checked</c:if>
                                    name="brand"><i></i>${brand}</label>
                            </c:forEach>
                        </div>
                    </div>
                </section>
                <section class="sky-form">
                    <button type="submit" class="btn btn-info">Filter</button>
                </section>
            </div>
        </form>
    </div class="clearfix">
</div>
<!--products-->

<!--//products-->
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
<%@include file="/jspf/footer.jspf" %>

<!--//footer-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="js/simpleCart.min.js"></script>
<!-- slide -->
<script src="js/bootstrap.min.js"></script>
<!--light-box-files -->
<script src="js/jquery.chocolat.js"></script>
<link rel="stylesheet" href="css/chocolat.css" type="text/css" media="screen" charset="utf-8">
<!--light-box-files -->
<script type="text/javascript" charset="utf-8">
    $(function () {
        $('a.picture').Chocolat();
    });

</script>
</body>
</html>