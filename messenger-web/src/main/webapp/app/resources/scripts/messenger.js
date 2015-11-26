window.onload = function onLoad() {
    window.setInterval("loadMessages();", 1000)
};

function loadMessages() {
    var userId = $("#userId").val();
    var receiverId = $("#contacts li.active").attr("userId");
    var receiverName = $("#contacts li.active a").html();

    if(receiverId == undefined)
        return;

    $.ajax({
        url: "//localhost:8555/api/messages?senderId=" + userId + "&receiverId=" + receiverId,
        type: 'GET',
        success: function (result) {
            result.forEach(function (message) {
                addUpdateMessage(message, userId, receiverName)
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus = "404") {
                $("#messages").html("");
            }
        }
    });
};

function addUpdateMessage(message, userId, receiverName) {
    if ($("#messages [messageId=" + message.id + "]").html() != undefined)
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

function sendMessageOnEnter(event) {
    if (event.keyCode == 13)
        sendMessage();
}

function sendMessage() {
    var userId = $("#userId").val();
    var receiverId = $("#contacts li.active").attr("userId");
    if (receiverId == undefined)
        return;

    $.ajax({
        url: "//localhost:8555/api/messages?userId=" + userId + "&receiverId=" + receiverId,
        type: "POST",
        contentType: "application/json",
        data: "\"" + $("#messageText").val() + "\"",
        success: function (result) {
            $("#warningInfo").hide();
            $("#messageText").val("");
            loadMessages();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#warningInfo").show();
        }
    });
}