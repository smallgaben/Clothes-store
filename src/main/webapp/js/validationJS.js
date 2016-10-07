function validation(form) {
    var email_pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var name_pattern = /^.{5,}/;
    var password_pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    var phonenum_pattern = /^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;

    var pattern_mas = new Map();

    pattern_mas.set("username", name_pattern);
    pattern_mas.set("realname", name_pattern);
    pattern_mas.set("surname", name_pattern);
    pattern_mas.set("phonenum", phonenum_pattern);
    pattern_mas.set("email", email_pattern);
    pattern_mas.set("password", password_pattern);
    pattern_mas.set("confirmpassword", password_pattern);

    var elem = $(form).find('[inputer]');

    for (var i = 0; i < elem.length; i++) {
        if(elem[i].name == "confirmpassword"){
            return checkPassword();
        }

        if (!checkPattern(pattern_mas.get(elem[i].name), elem[i])) {
            return false;
        }
    }
}

function checkPassword() {
    var span = document.getElementById("confirmpassword_alert");
    if (document.getElementById("password").value != document.getElementById("confirmpassword").value) {
        span.classList.remove("hidden");
        span.classList.add("show");
        return false;
    }

    span.classList.remove("show");
    span.classList.add("hidden");
    return true;
}

function checkPattern(pattern, element) {
    if (element.value == null || element.value.length === 0) {
        showMessage(element);
        return false;
    }

    if (pattern.test(element.value)) {
        hideMessage(element);
        return true;
    }

    showMessage(element);
    return false;
}

function showMessage(element) {
    var span = document.getElementById(element.name + "_" + "alert");
    span.classList.remove("hidden");
    span.classList.add("show");
    return true;
}

function hideMessage(element) {
    var span = document.getElementById(element.name + "_" + "alert");
    span.classList.remove("show");
    span.classList.add("hidden");
    return false;
}