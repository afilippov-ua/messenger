function restGetUsersByNameOrEmail(findText, callback) {
    $.ajax({
        url: restUserPath,
        type: "GET",
        headers: {"findText": findText},
        success: callback,
        error: callback
    });
}