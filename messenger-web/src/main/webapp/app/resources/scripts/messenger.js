var userId = $("#userId").prop("value");
var lastDate = null;

window.onload = function onLoad() {
    loadContacts();
    window.setInterval("loadMessages(false);", 1000);
};

function loadContacts() {
    $.ajax({
        url: "//localhost:8555/api/contacts?ownerId=" + userId,
        method: 'GET',
        success: function(data) {
            $("#contacts").html("");
            for (var index = 0; index < data.length; index++) {
                var currentContact = data[index];
                $("#contacts").append(
                    $('<option>' + currentContact.contactName + '</option>')
                        .attr("id", currentContact.id)
                        .attr("data-user-id", currentContact.contactUser.id)
                        .addClass("contact"));
            }
        }
    });
}

function loadMessages(fullUpdate, scrollOnBottom) {
    var receiverId = $("#contacts :selected").attr("data-user-id");
    if (receiverId == null) {
        $("#messages").html("");
        return;
    }

    $.ajax({
        url: "//localhost:8555/api/messages?senderId=" + userId + "&receiverId="+receiverId,
        method: 'GET',
        success: function(data) {
            $("#messages").html("");

            if (fullUpdate == true) {
                // clear all message data
                $("#messages").html("");
                lastDate = null;
            }

            for (index = 0; index < data.length; index++) {
                if (findCreateMessage(data[index])){
                    scrollOnBottom = true;
                }
            }

            if (scrollOnBottom) {
                message_container.scrollTop = message_container.scrollHeight;
            }
        }
    });
}

function findCreateMessage(currentMessage) {

    var result = false;

    // find this message by ID and create if it was not found
    if ($("#msg" + currentMessage.id) == null) {
        // message date
        var curDate = currentMessage.messageDate.substr(0, 10).split("-").join(".");
        if (curDate != lastDate) {
            lastDate = curDate;
            $("#messages").append(
                $("<div></div>").center().append(
                    $("<div>curDate</div>")
                        .addClass("date")
                        .center()));
        }

        var messageBlock;
        // author & time
        if (currentMessage.userSender == userId) {
            // my message
            messageBlock = createMessage(currentMessage, $("#contacts :selected").html(), true);
        } else {
            // receiver message
            messageBlock = createMessage(currentMessage, $("#contacts :selected").html(), false);
        }

        $("#messages").append(messageBlock);

        result = true;
    }

    return result;
}

//function createDateBlock(curDate) {
//
//    newInnerDiv.className = "date";
//    newInnerDiv.align = "center";
//    newInnerDiv.innerHTML = curDate;
//
//    var newDiv = document.createElement("div");
//    newDiv.align = "center";
//    newDiv.appendChild(newInnerDiv);
//
//    return newDiv;
//
//}

function createMessage(currentMessage, receiverName, isMyMessage) {

    $("<div></div>")
        .attr("id", currentMessage.id);

    var newDiv = document.createElement("div");
    newDiv.id = "msg" + currentMessage.id;

    var author;
    if (isMyMessage) {
        $("<div></div>")
            .attr("id", currentMessage.id)
            .addClass("message_wrapper_me");
        newDiv.className = "message_wrapper_me";
        author = "Me: ";
    } else {
        newDiv.className = "message_wrapper_interlocutor";
        author = receiverName + ": ";
    }

    var newInnerDiv = document.createElement("div");
    newInnerDiv.className = "message_wrapper";

    newInnerDiv.appendChild(createTimeBlock(currentMessage));
    newInnerDiv.appendChild(createAuthorBlock(currentMessage, author));
    newInnerDiv.appendChild(createMessageTextBlock(currentMessage, isMyMessage));

    newDiv.appendChild(newInnerDiv);

    return newDiv;
}

function createAuthorBlock(currentMessage, author) {

    var newSpan = document.createElement("span");
    newSpan.className = "author";
    newSpan.innerHTML = author;

    return newSpan;
}

function createTimeBlock(currentMessage) {

    var curTime = currentMessage.messageDate.substr(11, 5);

    var newSpan = document.createElement("span");
    newSpan.className = "time";
    newSpan.innerHTML = "[" + curTime + "]  ";

    return newSpan;
}

function createMessageTextBlock(currentMessage, isMyMessage) {

    var newSpan = document.createElement("span");

    if (isMyMessage) {
        newSpan.className = "message_text_me";
    } else {
        newSpan.className = "message_text_interlocutor";
    }
    newSpan.innerHTML = currentMessage.text;

    return newSpan;

}

/////////////////////////////////////////// SEND MESSAGE ////////////////////////////////////////
function sendMessageOnEnter(event) {
    if (event.ctrlKey && event.keyCode == 10)
        sendMessage();
}

function sendMessage() {

    var receiver = document.getElementById("contacts").selectedOptions[0];
    if (receiver != undefined) {

        var receiverId = receiver.getAttribute("data-user-id");

        var xmlhttp = new XMLHttpRequest();
        var messageField = document.getElementById("newMessage");
        var errorField = document.getElementById("errorLabel");
        var message = messageField.value;

        errorField.innerText = "";

        if (message != "") {

            var params = "?receiverId=" + receiverId;

            xmlhttp.open("POST", "//localhost:8555/api/users/" + userId + "/messages" + params, true);
            xmlhttp.setRequestHeader("Content-Type", "application/json");
            xmlhttp.send("\"" + message + "\"");

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    messageField.value = "";
                    loadMessages(true, true);

                } else if (xmlhttp.readyState == 4) {
                    errorField.innerHTML = "posting message error... please, try again later...";
                }
            }
        }
    }
}