$(document).ready(function () {
    getUserData();

    $(".btn").on("click", function () {
        var userId = $("#user-id").val();
        var email = $("#email");
        var newPassword = $("#new-password");
        var username = $("#username");

        if (verifyInputData(email, username, newPassword)) {
            restGetUserById(userId,
                function done(data) {
                    // TODO: add validation
                    data.email = email.val();
                    data.password = newPassword.val();
                    data.name = username.val();

                    restUpdateUser(userId, data,
                        function done() {
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
    });

    $("img")
        .on("click", function () {
            var input = $("#input-file");
            if (input) {
                input.click();
            }
        })
        .on("error", function () {
            $("img").attr('src', 'resources/no_image.jpg');
        });

    $("#input-file").on("change", function () {
        var inputFile = $("#input-file");
        var file = inputFile[0].files[0];

        inputFile.prev().html("");
        if (!verifyFileTypes(file.name)) {
            inputFile.val("");
            inputFile.prev().html("Upload JPG or PNG images only");
            return;
        }
        uploadPicture(file,
            function done(imageUrl) {
                $("img").attr("src", imageUrl);
            },
            function fail(data) {
                inputFile.prev().html("Can't upload image");
            });
        inputFile.val("");
    });
});

function uploadPicture(file, cbDone, cbFail) {
    var fileExtension = file.name.split('.').pop();
    var contentType;
    if (fileExtension == "jpg") {
        contentType = "image/jpeg";
    } else if (fileExtension == "png") {
        contentType = "image/png";
    } else
        return;

    var formData = new FormData();
    formData.append("image", file);

    $.ajax({
            url: globalAvatarPath,
            method: "POST",
            cache: false,
            // dataType: 'text/plain',
            processData: false,
            contentType: false,
            data: formData
        })
        .done(cbDone)
        .fail(cbFail);
}

function verifyInputData(email, username, newPassword) {
    email.parent().prev().html("");
    fail = false;
    if (email.val() == "") {
        email.parent().prev()
            .html("You have to enter your current email");
        fail = true;
    }
    return !fail;
}

function verifyFileTypes(fileName) {
    var allowedExtensions = ["jpg", "png"];
    var fileExtension = fileName.split('.').pop();
    for (var i = 0; i <= allowedExtensions.length; i++) {
        if (allowedExtensions[i] == fileExtension) {
            return true;
        }
    }
    return false;
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
    restGetUserById
}