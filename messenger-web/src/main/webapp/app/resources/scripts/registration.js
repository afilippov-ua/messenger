
function register() {

    var email = $("#email")[0].value;
    var password = $("#password")[0].value;
    if (email != undefined && password != undefined) {
        $.ajax({
            url: "//localhost:8555/register",
            type: 'POST',
            beforeSend: function (request)
            {
                request.setRequestHeader("email", email);
                request.setRequestHeader("password", password);
            },
            success: function(result) {
                alert("success@!!!")
            }
        });
    }
}