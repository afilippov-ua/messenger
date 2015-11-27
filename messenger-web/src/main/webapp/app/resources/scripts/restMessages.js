function restAddNewMessage(userId, receiverId, text, callback) {
    $.ajax({
        url: "//localhost:8555/api/messages?userId=" + userId + "&receiverId=" + receiverId,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(text),
        success: callback,
        error: callback
    });
}

function restGetMessagesByOwner(userId, receiverId, callback) {
    $.ajax({
        url: "//localhost:8555/api/messages?senderId=" + userId + "&receiverId=" + receiverId,
        type: "GET",
        success: callback,
        error: callback
    });
}