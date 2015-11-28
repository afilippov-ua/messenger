$(document).ready(function () {
    $("#find-contact-text").on("keypress", function (event) {
        if (event.keyCode == 13)
            findContacts();
    });
    $("#find-contact-btn").on("click", function (event) {
        findContacts();
    });
    $("#contact-edit-btn").on("click", function () {
        var name = $("#contacts li.active a").html();
        if (name == undefined)
            return;

        $("#contact-new-name").val(name);
        $("#modal-btn-save-contact").bind('click', function () {
            editContact();
        });
        $("#modal-enter-name").modal("show");
    });
    $("#contact-delete-btn").on("click", function () {
        var contactId = $("#contacts li.active").attr("contact-id");
        if (!contactId)
            return;

        $("#modal-btn-confirm").bind('click', function () {
            deleteContact(contactId);
        });
        $("#modal-confirm").modal("show");
    });

    loadContacts();
});

// find contacts by name or email
function findContacts() {
    $("#table-contacts-body").html("");
    var findText = $("#find-contact-text").val();
    if (findText == "")
        return;

    restGetUsersByNameOrEmail(findText,
        function done(data) {
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
        },
        function fail(request, statusText) {
            alert("get users by name or email error...");
        });
}

// load list of user contacts
function loadContacts() {
    $("#contacts").html("");
    restGetContactsByOwner($("#user-id").val(),
        function done(data) {
            data.forEach(function (contact) {
                $("#contacts").append(
                    $("<li onclick=\"selectContact(this)\"><a href=\"#\">" + contact.contactName + "</a></li>")
                        .attr("contact-id", contact.id)
                        .attr("user-id", contact.contactUser.id));
            });

            $("#contacts li:first-child").addClass("active");
            $("#messages").html("");
            loadMessages();
        },
        function fail() {
            $("#contacts").html("");
        });
}

// set active on selected contact
function selectContact(elem) {
    $("#contacts li.active").removeClass("active");
    $(elem).addClass("active");
    $("#messages").html("");
    loadMessages();
}

// update contact data
function editContact() {
    var contactId = $("#contacts li.active").attr("contact-id");
    var newName = $("#contact-new-name").val();
    if (contactId && newName) {
        restGetContactById(contactId,
            function done(data) {
                var contact = data;
                contact.contactName = newName;
                restUpdateContact(contactId, contact,
                    function done() {
                        $("#contacts li.active a").html($("#contact-new-name").val());
                        $("#contact-new-name").val("");
                        $("#modal-btn-save-contact").unbind();
                        $("#modal-enter-name").modal("hide");
                    },
                    function fail() {
                        alert("update contact error...");
                    });
            },
            function fail() {
                alert("get contact error...");
            });
    }
}

// delete selected contact
function deleteContact(contactId) {
    restDeleteContact(contactId,
        function done() {
            loadContacts();
            $("#modal-confirm").modal("hide");
            $("#modal-btn-confirm").unbind();
        },
        function fail() {
            alert("delete contact error...");
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
    var userId = $("#user-id").val();
    var contactId = $("#table-contacts tbody tr.active").attr("user-id");
    var contactName = $("#contact-new-name").val();

    restAddNewContact(userId, contactId, contactName,
        function done() {
            loadContacts();
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
        },
        function fail() {
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
            alert("add contact error...");
        });
}