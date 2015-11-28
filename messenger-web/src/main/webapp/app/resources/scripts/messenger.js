$(document).ready(function () {
    window.setInterval("loadMessages();", 1000)

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

    restGetMessagesByOwner(userId, receiverId,
        function done(data) {
            data.forEach(function (message) {
                addUpdateMessage(message, userId, receiverName)
            });
        },
        function fail() {
            $("#messages").html("");
        })
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
                .append(
                $("<span>Me:</span>").addClass("badge-sender"))
                .append(message.text))
    } else {
        // receiver message
        $("#messages").append(
            $("<div></div>")
                .addClass("well-receiver")
                .attr("messageId", message.id)
                .append(
                $("<span>" + receiverName + "</span>").addClass("badge-receiver"))
                .append(message.text))
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