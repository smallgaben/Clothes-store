<%@include file="/jspf/title.jspf" %>

<body>
<!--header-->
<%@include file="/jspf/header.jspf" %>
<!--banner-->
<div class="banner-top">
    <div class="container">
        <h1>Register</h1>
        <em></em>

        <h2><a href="index.jsp">Home</a><label>/</label>Register</h2>
    </div>
</div>
<!--login-->
<div class="container">
    <div class="login">
        <form id="registrForm" name="registrForm" method="post" action="registerUser" enctype="multipart/form-data" onsubmit="return validation(this);">
            <div class="col-md-6 login-do">
                <div class="login-mail">
                    <input name="username" type="text" inputer placeholder="Name" value="${sessionScope.inputers.username}">
                    <i class="glyphicon glyphicon-user"></i>
                    <c:if test="${not empty sessionScope.errors.username}">
                    <span>${sessionScope.errors.username}</span>
                    </c:if>
                    <span id="username_alert" class="hidden">Wrong name (name must be at least 5 characters)</span>
                </div>
                <div class="login-mail">
                    <input name="realname" type="text" inputer placeholder="Real Name" value="${sessionScope.inputers.realname}">
                    <i class="glyphicon glyphicon-user"></i>
                    <c:if test="${not empty sessionScope.errors.realname}">
                                        <span>${sessionScope.errors.realname}</span>
                                        </c:if>
                    <span id="realname_alert" class="hidden">Wrong name (name must be at least 5 characters)</span>
                </div>
                <div class="login-mail">
                    <input name="surname" type="text" inputer placeholder="Surname" value="${sessionScope.inputers.surname}">
                    <i class="glyphicon glyphicon-user"></i>
                    <c:if test="${not empty sessionScope.errors.surname}">
                                        <span>${sessionScope.errors.surname}</span>
                                        </c:if>
                    <span id="surname_alert" class="hidden">Wrong name (name must be at least 5 characters)</span>
                </div>
                <div class="login-mail">
                    <input name="phonenum" type="text" inputer placeholder="Phone Number" value="${sessionScope.inputers.phonenum}">
                    <i class="glyphicon glyphicon-phone"></i>
                    <c:if test="${not empty sessionScope.errors.phonenum}">
                                        <span>${sessionScope.errors.phonenum}</span>
                                        </c:if>
                    <span id="phonenum_alert" class="hidden">Wrong phone number(xxx-xxx-xxxx)</span>
                </div>
                <div class="login-mail">
                    <input name="email" type="text" inputer placeholder="Email" value="${sessionScope.inputers.email}">
                    <i class="glyphicon glyphicon-envelope"></i>
                    <c:if test="${not empty sessionScope.errors.email}">
                                        <span>${sessionScope.errors.email}</span>
                                        </c:if>
                    <span id="email_alert" class="hidden">Wrong email(xx@xx.xx)</span>
                </div>
                <div class="login-mail">
                    <input id="password" name="password" type="password" inputer placeholder="Password">
                    <i class="glyphicon glyphicon-lock"></i>
                    <c:if test="${not empty sessionScope.errors.password}">
                                                            <span>${sessionScope.errors.password}</span>
                                                            </c:if>
                    <span id="password_alert" class="hidden">Wrong password, password must conatins upper case letter and digit symbol</span>
                </div>
                <div class="login-mail">
                    <input id="confirmpassword" name="confirmpassword" type="password" inputer
                           placeholder="Confirm Password">
                    <i class="glyphicon glyphicon-lock"></i>
                    <c:if test="${not empty sessionScope.errors.confirmpassword}">
                                                            <span>${sessionScope.errors.confirmpassword}</span>
                                                            </c:if>
                    <span id="confirmpassword_alert" class="hidden">Passwords are not equals</span>
                </div>
                <div>
                    <input type="file" name="image" id="image" />
                </div>
                <div>
                    <e:captcha></e:captcha>
                </div>
                <div class="login-mail">
                    <input type="text" name="captchaInput" required placeHolder="enter captcha">
                </div>
                <label class="hvr-skew-backward">
                    <input type="submit" value="Submit">
                </label>
                    <c:if test="${not empty sessionScope.existingError}">
                        <span>${sessionScope.existingError}</span>
                    </c:if>
            </div>
            <div class="col-md-6 login-right">
                <h3>Completely Free Account</h3>

                <p>Pellentesque neque leo, dictum sit amet accumsan non, dignissim ac mauris. Mauris rhoncus, lectus
                    tincidunt tempus aliquam, odio
                    libero tincidunt metus, sed euismod elit enim ut mi. Nulla porttitor et dolor sed condimentum.
                    Praesent porttitor lorem dui, in pulvinar enim rhoncus vitae. Curabitur tincidunt, turpis ac
                    lobortis hendrerit, ex elit vestibulum est, at faucibus erat ligula non neque.</p>
                <a href="login.jsp" class="hvr-skew-backward">Login</a>

            </div>

            <div class="clearfix"></div>
        </form>
    </div>

    <!--   <script src="js/validationJS.js"></script> -->
    <!--<script src="js/validationJQuery.js"></script>-->

</div>

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
<%@include file="/jspf/footer.jspf" %>

<!--//footer-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="js/simpleCart.min.js"></script>
<!-- slide -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>