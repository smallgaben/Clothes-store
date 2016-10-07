var email_pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var name_pattern = /^.{5,}/;
var password_pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
var phonenum_pattern= /^(\([0-9]{3}\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$/;

var pattern_mas= new Map();

pattern_mas.set("username",name_pattern);
pattern_mas.set("email",email_pattern);
pattern_mas.set("password",password_pattern);
pattern_mas.set("phonenum",phonenum_pattern);

$(document).ready(function(){
    $("#registrForm").submit(function(){
        var accepted;

        var elem = $("#registrForm").find("[inputer]");

        elem.each(function checkPattern(index, element){
            var pattern=pattern_mas.get(element.name);

            if(element.value == null || element.value.length === 0){
                showMessage(element);
                accepted=false;
                return;
            }

            if(element.value.match(pattern)){
                hideMessage(element);
                accepted=true;
                return;
            }

            showMessage(element);
            accepted=false;
        });

        return accepted;
    });

    function showMessage(element){
        $("#"+element.name+"_"+"alert").removeClass("hidden");
        $("#"+element.name+"_"+"alert").addClass("show");
    }

    function hideMessage(element){
        $("#"+element.name+"_"+"alert").removeClass("show");
        $("#"+element.name+"_"+"alert").addClass("hidden");
    }
});