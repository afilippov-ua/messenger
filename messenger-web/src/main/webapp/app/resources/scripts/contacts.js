window.onload = function onLoad() {
    loadContacts();
};

function loadContacts() {
    $("#contacts").html("");
    $.ajax({
        url: "//localhost:8555/api/contacts?ownerId=" + $("#userId").val(),
        type: 'GET',
        success: function (result) {
            result.forEach(function (contact) {
                $("#contacts").append(
                    $("<li onclick=\"onContactClick(this)\"><a class=\"contact\" href=\"#\">" + contact.contactName + "</a></li>")
                        .attr("contactId", contact.id)
                        .attr("userId", contact.contactUser.id));
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus = "404") {
                $("#contacts").html("");
            }
        }
    });
};

function addContact() {
    var contactId = $("#add-contact-contact-list li").attr("user-id");
    $.ajax({
        url: "//localhost:8555/api/contacts?ownerId=" + $("#userId").val() + "&contactId=" + contactId,
        type: "POST",
        beforeSend: function (request) {
            request.setRequestHeader("name", $("#add-contact-username").val());
        },
        success: function (result) {
            $("#add-contact-email").val("");
            $("#add-contact-contact").html("");
            $("#add-contact-username").val("");
            loadContacts();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#add-contact-contact-error")
                .html("user is already exists in your contact list!");
        }
    });
};

function getContactEvent(event) {
    if (event.keyCode == 13)
        getContact();
}

function getContact() {
    $("#add-contact-contact-error").html("");
    var email = $("#add-contact-email").val();
    if (email == "")
        return;

    $.ajax({
        url: "//localhost:8555/api/users",
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader("email", email);
        },
        success: function (result) {
            $("#add-contact-contact-info").html("");
            if (result.length == 0) {
                $("#add-contact-contact-info")
                    .html("user was not found!");
            } else {
                result.forEach(function (user) {
                    // 1 user
                    $("#add-contact-contact-info")
                        .html(user.name + " (" + user.email + ")")
                        .attr("user-id", user.id);
                })
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // ERROR
        }
    });
}