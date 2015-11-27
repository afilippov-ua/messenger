function restGetUsersByNameOrEmail(findText, callback) {
    $.ajax({
        url: "//localhost:8555/api/users",
        type: "GET",
        headers: {"findText": findText},
        success: callback,
        error: callback
    });
}