function logout() {
    $.ajax({
        url: '/j_spring_security_logout',
        type: 'POST',
        success: function (data) {
            window.location = "/login";
        },
        error: function (data) {
            //
        }
    });
}

function registration() {

    $("#successInfo").hide();
    $("#warningInfo").hide();

    $.ajax({
        url: "//localhost:8555/api/users",
        type: 'POST',
        beforeSend: function (request)
        {
            request.setRequestHeader("email", $("#email").val());
            request.setRequestHeader("password", $("#password").val());
        },
        success: function(result, status, XMLHttpRequest) {
            if (XMLHttpRequest.status = 200) {
                $("#successInfo").html("User was created. Please log in!").show();
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus = "404"){
                $("#warningInfo").html("User with this email already exists!").show();
            }
        }
    });
}