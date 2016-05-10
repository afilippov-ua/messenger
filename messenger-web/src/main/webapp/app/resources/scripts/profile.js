$(document).ready(function () {
    getUserData();

    $(".btn").on("click", function () {
        var userId = $("#user-id").val();
        var email = $("#email");
        var currentPassword = $("#current-password");
        var newPassword = $("#new-password");
        var username = $("#username");

        if (verifyInputData(email, username, currentPassword, newPassword)) {
            restGetUserById(userId,
                function done(data) {
                    // TODO: add validation
                    data.email = email.val();
                    data.password = newPassword.val();
                    data.name = username.val();

                    restUpdateUser(userId, data,
                        function done() {
                            currentPassword.val("");
                            newPassword.val("");
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
        }
    })
});

function verifyInputData(email, username, currentPassword, newPassword) {
    email.parent().prev().html("");
    currentPassword.parent().prev().html("");
    fail = false;
    if (email.val() == "") {
        email.parent().prev()
            .html("You have to enter your current email");
        fail = true;
    }
    if (newPassword.val() != "" && currentPassword.val() == "") {
        currentPassword.parent().prev()
            .html("You have to enter your current password");
        fail = true;
    }
    return !fail;
}

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