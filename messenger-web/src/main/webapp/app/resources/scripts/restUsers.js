function restAddNewUser(email, password, username, cbDone, cbFail) {
    $.ajax({
            url: restUserPath,
            type: "POST",
            data: {
                "email": encodeURIComponent(email),
                "password": encodeURIComponent(password),
                "username": encodeURIComponent(username)
            }
        })
        .done(cbDone)
        .fail(cbFail);
}

function restGetUserById(id, cbDone, cbFail) {
    $.ajax({
            url: restUserPath + "/" + id,
            type: "GET"
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

function restUpdateUser(id, user, cbDone, cbFail) {
    $.ajax({
            url: restUserPath + "/" + id,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(user)
        })
        .done(cbDone)
        .fail(cbFail);
}