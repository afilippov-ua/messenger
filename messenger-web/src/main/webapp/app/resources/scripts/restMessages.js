function restAddNewMessage(userId, receiverId, text, cbDone, cbFail) {
    $.ajax({
        url: restMessagePath + "?userId=" + userId + "&receiverId=" + receiverId,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(text)
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