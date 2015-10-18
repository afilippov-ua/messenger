
function registration() {

    var email = $("#email")[0].value;
    var password = $("#password")[0].value;
    if (email != undefined && password != undefined) {
        $.ajax({
            url: "//localhost:8555/api/users",
            type: 'POST',
            beforeSend: function (request)
            {
                request.setRequestHeader("email", email);
                request.setRequestHeader("password", password);
            },
            success: function(result, status, XMLHttpRequest) {
                if (XMLHttpRequest.status = 200) {
                    $("#info")[0].innerHTML = "User was created. Please log in!";
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (textStatus = "404"){
                    $("#info")[0].innerHTML = "User with this email already exists!";
                }
            }
        });
    }
}