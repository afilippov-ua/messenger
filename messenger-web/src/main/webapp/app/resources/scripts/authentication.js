function logout() {
    $.ajax({
        url: "/j_spring_security_logout",
        type: "POST"
    })
        .done(function () {
            window.location = "/login";
        })
        .fail(function () {
            alert("logout error...");
        });
}

function registration() {
    $("#panel-info").hide();
    restAddNewUser($("#email").val(), $("#password").val(),
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