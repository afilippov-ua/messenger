function logout() {
    $.ajax({
            url: "/j_spring_security_logout",
            type: "POST"
        })
        .done(function () {
            window.location = "/login?logout";
        })
        .fail(function () {
            alert("logout error...");
        });
}

function login() {
    $(".alert").hide();
    var email = $("#email");
    var password = $("#password");

    if(validateLoginInput(email, password)) {
        $(".form-signin").submit();
    }
}

function validateLoginInput(email, password) {
    email.parent().prev().html("");
    password.parent().prev().html("");
    var fail = false;
    if (!validateEmail(email.val())) {
        email.parent().prev()
            .html("Incorrect email");
        fail = true;
    }
    if(password.val() == "") {
        password.parent().prev()
            .html("Empty password");
        fail = true;
    }
    return !fail;
}

function registration() {
    $("#panel-info").hide();
    var email = $("#email");
    var password = $("#password");
    var passwordVerify = $("#password-verify");

    if (validateRegistrationInput(email, password, passwordVerify)) {
        restAddNewUser(email.val(), password.val(),
            function done() {
                $("#panel-info")
                    .removeClass("alert-warning")
                    .addClass("alert-success")
                    .html("User was created. Please log in!")
                    .show();
            },
            function fail(req, stat) {
                $("#panel-info")
                    .removeClass("alert-success")
                    .addClass("alert-warning")
                    .html("User with this email already exists!")
                    .show();
            })
    }
}

function validateRegistrationInput(email, password, passwordVerify) {
    email.parent().prev().html("");
    password.parent().prev().html("");
    passwordVerify.parent().prev().html("");
    var fail = false;
    if (!validateEmail(email.val())) {
        email.parent().prev()
            .html("Incorrect email");
        fail = true;
    }
    if(password.val() == "") {
        password.parent().prev()
            .html("Empty password");
        fail = true;
    }
    if (password.val() != passwordVerify.val()) {
        passwordVerify.parent().prev()
            .html("Password doesn't match confirmation");
        fail = true;
    }
    return !fail;
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}