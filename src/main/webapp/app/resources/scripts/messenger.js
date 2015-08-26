
var userId = document.getElementById("userId").value;
var lastDate = null;

window.onload = function onLoad() {
    loadContacts();
    window.setInterval("loadMessages(false);", 1000);
};

/////////////////////////////////////////// LOAD CONTACTS ////////////////////////////////////////

function loadContacts() {

    var contactList = document.getElementById("contacts")

    if (contactList != null && userId != null) {

        var xmlhttp = new XMLHttpRequest();

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                contactList.innerHTML = "";

                var contactsArray = JSON.parse(xmlhttp.responseText);
                for (var index = 0; index < contactsArray.length; index++) {

                    var currentContact = contactsArray[index];

                    var newOption = document.createElement("option");
                    newOption.setAttribute("id", currentContact.id);
                    newOption.setAttribute("data-user-id", currentContact.contactUser.id);
                    newOption.setAttribute("className", "contact");
                    newOption.innerHTML = currentContact.contactName;

                    contactList.insertBefore(newOption, contactList.lastElementChild);

                }
            }
        }

        xmlhttp.open("GET", "//localhost:8555/api/users/" + userId + "/contacts", true);
        xmlhttp.send();
    }
}

/////////////////////////////////////////// LOAD MESSAGES ////////////////////////////////////////
function loadMessages(fullUpdate, scrollOnBottom) {

    var receiver = document.getElementById("contacts").selectedOptions[0];
    if (receiver == undefined) {
        message_container.innerHTML = "";
    } else {

        var receiverId = receiver.getAttribute("data-user-id");
        var receiverName = receiver.innerHTML;
        var message_container = document.getElementById("messages");

        if (message_container != null
            && userId != null
            && receiverId != null) {

            var xmlhttp = new XMLHttpRequest();

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                    var messages = JSON.parse(xmlhttp.responseText);

                    if (fullUpdate == true) {
                        // clear all message data
                        message_container.innerHTML = "";
                        lastDate = null;
                    }

                    for (index = 0; index < messages.length; index++) {
                        if (findCreateMessage(message_container, messages[index], receiverName)){
                            scrollOnBottom = true;
                        }
                    }

                    if (scrollOnBottom) {
                        message_container.scrollTop = message_container.scrollHeight;
                    }
                }
            };

            xmlhttp.open("GET", "//localhost:8555/api/users/" + userId + "/messages?receiverId=" + receiverId, true);
            xmlhttp.send();
        }
    }
}

function findCreateMessage(message_container, currentMessage, receiverName) {

    var result = false;

    // find this message by ID and create if it was not found
    if (document.getElementById("msg" + currentMessage.id) == null) {

        // message date
        var curDate = currentMessage.messageDate.substr(0, 10).split("-").join(".");
        if (curDate != lastDate) {
            lastDate = curDate;
            message_container.appendChild(createDateBlock(curDate));
        }

        var messageBlock;

        // author & time
        if (currentMessage.userSender == userId) {
            // my message
            messageBlock = createMessage(currentMessage, receiverName, true);
        } else {
            // to me
            messageBlock = createMessage(currentMessage, receiverName, false);
        }

        message_container.appendChild(messageBlock);

        result = true;
    }

    return result;
}

function createDateBlock(curDate) {

    var newInnerDiv = document.createElement("div");
    newInnerDiv.className = "date";
    newInnerDiv.align = "center";
    newInnerDiv.innerHTML = curDate;

    var newDiv = document.createElement("div");
    newDiv.align = "center";
    newDiv.appendChild(newInnerDiv);

    return newDiv;

}

function createMessage(currentMessage, receiverName, isMyMessage) {

    var newDiv = document.createElement("div");
    newDiv.id = "msg" + currentMessage.id;

    var author;
    if (isMyMessage) {
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