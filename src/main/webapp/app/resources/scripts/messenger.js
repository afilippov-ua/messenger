var userId = document.getElementById("userId").value;

window.onload = function onLoad() {
    loadContacts();
    window.setInterval("loadMessages();", 1000);
}

function loadContacts() {

    var contactList = document.getElementById("contacts")

    if (contactList != null && userId != null) {

        var xmlhttp = new XMLHttpRequest();

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                var contactsArray = JSON.parse(xmlhttp.responseText);
                for (index = 0; index < contactsArray.length; index++) {

                    var currentContact = contactsArray[index];

                    var newOption = document.createElement("option");
                    newOption.id = currentContact.contactUser.id;
                    newOption.innerHTML = currentContact.contactUser.name;

                    contactList.insertBefore(newOption, contactList.lastElementChild);

                }
            }
        }

        xmlhttp.open("GET", "//localhost:8555/api/users/" + userId + "/contacts", true);
        xmlhttp.send();
    }
}

function loadMessages() {

    var receiver = document.getElementById("contacts").selectedOptions[0];
    var receiverId = receiver.id;
    var receiverName = receiver.innerHTML;

    if (document.getElementById("messages") != null) {

        var xmlhttp = new XMLHttpRequest();

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var result = "";
                var messages = JSON.parse(xmlhttp.responseText);
                for (index = 0; index < messages.length; index++) {
                    var currentMessage = messages[index];
                    if (result != "") {
                        result += "\n";
                    }

                    if (currentMessage.userSender == userId) {
                        result = result + "Me: " + currentMessage.text;
                    } else {
                        result = result + receiverName + ": " + currentMessage.text;
                    }
                }
                document.getElementById("messages").value = result;
                document.getElementById("messages").scrollTop = document.getElementById("messages").scrollHeight;
            }
        }

        xmlhttp.open("GET", "//localhost:8555/api/users/" + userId + "/messages?receiverId=" + receiverId, true);
        xmlhttp.send();
    }

}

function sendMessageOnEnter(event) {
    if (event.keyCode == 13)
        sendMessage();
}

function sendMessage() {

    var receiverId = document.getElementById("contacts").selectedOptions[0].id;

    var xmlhttp = new XMLHttpRequest();
    var messageField = document.getElementById("newMessage");
    var errorField = document.getElementById("errorLabel");
    var message = messageField.value;
    var csrf = document.getElementById("csrf");

    errorField.innerText = "";

    if (message != "") {

        var params = "?" + csrf.name + "=" + csrf.value + "&receiverId=" + receiverId + "&messageText=" + message;

        xmlhttp.open("POST", "//localhost:8555/api/users/" + userId + "/messages" + params, true);
        xmlhttp.send();

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                messageField.value = "";
                loadMessages();
            } else if (xmlhttp.readyState == 4) {
                errorField.innerHTML = "posting message error... please, try again later...";
            }
        }
    }
}