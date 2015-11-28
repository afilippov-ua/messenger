$(document).ready(function () {
    getUserData();

    $("#btn-save").on("click", function () {
        var userId = $("#user-id").val();
        restGetUserById(userId,
            function done(data) {
                // TODO: add validation
                data.email = $("#email").val();
                data.password = $("#password").val();
                data.name = $("#username").val();

                restUpdateUser(userId, data,
                    function done() {
                        $("#password").val("");
                        $("#panel-info")
                            .html("Changes have been successful update!")
                            .removeClass("alert-warning")
                            .addClass("alert-success")
                            .show();
                    },
                    function fail() {
                        $("#panel-info")
                            .html("Update user info error!")
                            .removeClass("alert-success")
                            .addClass("alert-warning")
                            .show();
                    })
            },
            function fail() {
                $("#panel-info")
                    .html("Update user info error!")
                    .removeClass("alert-success")
                    .addClass("alert-warning")
                    .show();
            })
    })
});

function getUserData() {
    restGetUserById($("#user-id").val(),
        function done(data) {
            $("#email").val(data.email);
            $("#username").val(data.name);
        },
        function fail() {
            $("#panel-info")
                .html("Get user data error!")
                .removeClass("alert-success")
                .addClass("alert-warning")
                .show();
        });
}