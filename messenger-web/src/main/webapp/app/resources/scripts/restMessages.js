function restAddNewMessage(userId, receiverId, text, cbDone, cbFail) {
    $.ajax({
        url: restMessagePath,
        type: "POST",
        headers: {
            "userId": userId,
            "receiverId": receiverId
        },
        contentType: "application/json",
        data: JSON.stringify(encodeURIComponent(text))
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetMessagesByOwner(userId, receiverId, cbDone, cbFail) {
    $.ajax({
        url: restMessagePath + "?senderId=" + userId + "&receiverId=" + receiverId,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}