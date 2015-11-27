window.onload = function onLoad() {
    loadContacts();
}

// SEARCH
function findContactsByNameOrEmailOnEnter(event) {
    if (event.keyCode == 13)
        findContactsByNameOrEmail();
}

function findContactsByNameOrEmail() {
    $("#table-contacts-body").html("");
    var findText = $("#find-contact-text").val();
    if (findText == "")
        return;

    restGetUsersByNameOrEmail(findText, function(data, statusText) {
        if (statusText == "success") {
            $("#table-contacts-body").html("");
            if (data.length != 0) {
                data.forEach(function (user) {
                    $("#table-contacts-body")
                        .append(
                        $("<tr onclick='rowOnClick(this)'></tr>")
                            .attr("user-id", user.id)
                            .append(
                            $("<td>" + user.id + "</td>"))
                            .append(
                            $("<td><b>" + user.name + "</b></td>"))
                            .append(
                            $("<td>" + user.email + "</td>"))
                            .append(
                            $("<td></td>").append(
                                $("<button onclick='addNewContactOnClick(this)'>add</button>")
                                    .attr("type", "button")
                                    .addClass("btn")
                                    .addClass("btn-danger")
                                    .attr("user-name", user.name))
                            )
                    );
                })
            }
        }
    });
}

// MY CONTACTS
function loadContacts() {
    $("#contacts").html("");
    restGetContactsByOwner($("#userId").val(), function (data, statusText) {
        if (statusText == "success") {
            data.forEach(function (contact) {
                $("#contacts").append(
                    $("<li onclick=\"contactOnClick(this)\"><a href=\"#\">" + contact.contactName + "</a></li>")
                        .attr("contact-id", contact.id)
                        .attr("user-id", contact.contactUser.id));
            });

            $("#contacts li:first-child").addClass("active");
            $("#messages").html("");
            loadMessages();
        }
        else
            $("#contacts").html("");
    });
}

function contactOnClick(elem) {
    $("#contacts li.active").removeClass("active");
    $(elem).addClass("active");
    $("#messages").html("");
    loadMessages();
}

function editContactOnClick() {
    var name = $("#contacts li.active a").html();
    if (name == undefined)
        return;

    $("#contact-new-name").val(name);
    $("#modal-btn-save-contact").bind('click', function () {
        editContactConfirm();
    })
    $("#modal-enter-name").modal("show");
}

function editContactConfirm() {
    var contactId = $("#contacts li.active").attr("contact-id");
    var newName = $("#contact-new-name").val();
    if (contactId && newName) {
        restGetContactById(contactId, function (data, statusText) {
            if (statusText == "success") {
                var contact = data;
                contact.contactName = newName;

                restUpdateContact(contactId, contact, function (data, statusText) {
                    if (statusText == "nocontent") {
                        $("#contacts li.active a").html($("#contact-new-name").val());
                        $("#contact-new-name").val("");
                        $("#modal-btn-save-contact").unbind();
                        $("#modal-enter-name").modal("hide");
                    } else {
                        alert("update contact error...");
                    }
                });
            } else {
                alert("get contact error...");
            }
        });
    }
}

function deleteContactOnClick() {
    var contactId = $("#contacts li.active").attr("contact-id");
    if (!contactId)
        return;

    $("#modal-btn-confirm").bind('click', function() {
        deleteContactConfirm(contactId);
    });
    $("#modal-confirm").modal("show");
}

function deleteContactConfirm(contactId){
    restDeleteContact(contactId, function(data, statusText) {
        if (statusText = "nocontent") {
            loadContacts();
            $("#modal-confirm").modal("hide");
            $("#modal-btn-confirm").unbind();
        } else {
            alert("delete contact error...");
        }
    });
}

// NEW CONTACTS
function rowOnClick(elem) {
    $("#table-contacts tr.active").removeClass("active");
    $(elem).addClass("active");
}

function addNewContactOnClick(elem) {
    var contactName = $(elem).attr("user-name");

    $("#contact-new-name").val(contactName);
    $("#modal-btn-save-contact").bind('click', function () {
        addNewContactConfirm();
    });
    $("#modal-enter-name").modal("show");
}

function addNewContactConfirm() {
    var userId = $("#userId").val();
    var contactId = $("#table-contacts tbody tr.active").attr("user-id");
    var contactName = $("#contact-new-name").val();

    restAddNewContact(userId, contactId, contactName, function(data, statusText, response){
        if (statusText == "success") {
            loadContacts();
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
        } else {
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
            alert("add contact error...");
        }
    });
}