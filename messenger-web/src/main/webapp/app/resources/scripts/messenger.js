$(document).ready(function () {
    window.setInterval("loadMessages();", 1000);
    $("#message-text").on("keypress", function (event) {
        if (event.keyCode == 13)
            sendMessage();
    });
});

function loadMessages() {
    var userId = $("#user-id").val();
    var receiverId = $("#contacts li.active").attr("user-id");
    var receiverName = $("#contacts li.active a").html();

    if (!receiverId)
        return;

    restGetMessagesByOwner(userId, receiverId, undefined,
        function done(data) {
            if (data.length == 30                     // data is available
                && $("#load-msg").length == 0         // button isn't exist
                && $("#messages div").length == 0) {  // no messages

                $("#messages").append($("<div></div>")
                    .attr("id", "load-msg")
                    .append($("<a>load more messages</a>")
                        .attr("href", "#")));
                $("#load-msg").on("click", function (event) {
                    loadMoreMessages(userId, receiverId, receiverName);
                });
            }
            data.forEach(function (message) {
                addUpdateMessage(message, userId, receiverName)
            });
        },
        function fail() {
            $("#messages").html("");
        })
}

function loadMoreMessages(userId, receiverId, receiverName) {
    var loadMsgBtn = $("#load-msg");
    var firstLoadedMessageId = loadMsgBtn.next().attr("messageId");

    if (!firstLoadedMessageId)
        return;

    restGetMessagesByOwner(userId, receiverId, firstLoadedMessageId,
        function done(data) {
            if (data.length == 0) {
                loadMsgBtn.remove();
            }
            else {
                var lastElem = loadMsgBtn;
                data.forEach(function (message) {
                    lastElem = addPreviousMessage(message, userId, receiverName, lastElem)
                });
            }
        })
}

function addPreviousMessage(message, userId, receiverName, lastElement) {
    var currentElem;
    if (message.userSender == userId) {
        // my message
        currentElem = $("<div></div>")
            .addClass("well-sender")
            .attr("messageId", message.id)
            .append($("<span>Me:</span>").addClass("badge-sender-left"))
            .append(message.text)
            .append($("<span>" + message.messageDate.substr(0, 19) + "</span>").addClass("badge-sender-right"))
    } else {
        // receiver message
        currentElem = $("<div></div>")
            .addClass("well-receiver")
            .attr("messageId", message.id)
            .append($("<span>" + receiverName + "</span>").addClass("badge-receiver-left"))
            .append(message.text)
            .append($("<span>" + message.messageDate.substr(0, 19) + "</span>").addClass("badge-receiver-right"))
    }

    lastElement.after(currentElem)
    return currentElem;
}

function addUpdateMessage(message, userId, receiverName) {
    if ($("#messages [messageId=" + message.id + "]").length != 0)
        return;

    if (message.userSender == userId) {
        // my message
        $("#messages").append(
            $("<div></div>")
                .addClass("well-sender")
                .attr("messageId", message.id)
                .append($("<span>Me:</span>").addClass("badge-sender-left"))
                .append(message.text)
                .append($("<span>" + message.messageDate.substr(0, 19) + "</span>").addClass("badge-sender-right"))
        )
    } else {
        // receiver message
        $("#messages").append(
            $("<div></div>")
                .addClass("well-receiver")
                .attr("messageId", message.id)
                .append($("<span>" + receiverName + "</span>").addClass("badge-receiver-left"))
                .append(message.text)
                .append($("<span>" + message.messageDate.substr(0, 19) + "</span>").addClass("badge-receiver-right"))
        )
    }

    var div = $("#messages");
    div.scrollTop(div.prop('scrollHeight'));
}

function sendMessage() {
    var userId = $("#user-id").val();
    var receiverId = $("#contacts li.active").attr("user-id");
    var text = $("#message-text").val();
    if (!receiverId || !text)
        return;

    restAddNewMessage(userId, receiverId, text,
        function done() {
            $("#warningInfo").hide();
            $("#message-text").val("");
            loadMessages();
        },
        function fail() {
            $("#warningInfo").show();
        });
}