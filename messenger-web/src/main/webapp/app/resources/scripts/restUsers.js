function restAddNewUser(email, password, cbDone, cbFail) {
    $.ajax({
        url: restUserPath,
        type: "POST",
        headers: {
            "email": encodeURIComponent(email),
            "password": encodeURIComponent(password)
        }
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetUsersByNameOrEmail(findText, cbDone, cbFail) {
    $.ajax({
        url: restUserPath,
        type: "GET",
        headers: {"findText": encodeURIComponent(findText)}
    })
        .done(cbDone)
        .fail(cbFail);
}