window.onload = function onLoad() {
    window.setInterval("loadMessages();", 1000)
};

function loadMessages() {
    var userId = $("#userId").val();
    var receiverId = $("#contacts li.active").attr("user-id");
    var receiverName = $("#contacts li.active a").html();

    if(!receiverId)
        return;

    restGetMessagesByOwner(userId, receiverId, function(data, statusText){
        if (statusText == "success"){
            data.forEach(function (message) {
                addUpdateMessage(message, userId, receiverName)
            });
        } else {
            $("#messages").html("");
        }
    })
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
    var receiverId = $("#contacts li.active").attr("user-id");
    var text = $("#messageText").val();
    if (!receiverId || !text)
        return;

    restAddNewMessage(userId, receiverId, text, function(data, statusText){
        if (statusText == "success") {
            $("#warningInfo").hide();
            $("#messageText").val("");
            loadMessages();
        } else {
            $("#warningInfo").show();
        }
    });
}