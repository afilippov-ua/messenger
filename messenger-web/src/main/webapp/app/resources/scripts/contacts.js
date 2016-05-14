$(document).ready(function () {
    $("#find-contact-text").on("keypress", function (event) {
        if (event.keyCode == 13)
            findContacts();
    });
    $("#contact-new-name").on("keypress", function (event) {
        if (event.keyCode == 13)
            $("#modal-btn-save-contact").click();
    });
    $("#contact-find-btn").on("click", function (event) {
        findContacts();
    });
    $("#contact-edit-btn").on("click", function () {
        var name = $("#contacts li.active a span").html();
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
    $('#modal-enter-name').on('shown.bs.modal', function () {
        $('#contact-new-name').focus();
    })
    loadContacts();
});

// find contacts by name or email
function findContacts() {
    $("#table-contacts-body").html("");
    var findText = $("#find-contact-text").val();
    if (findText == "")
        return;

    restGetUsersByNameOrEmail(findText,
        function done(dataUsers) {
            var userId = $("#user-id").val();
            restGetContactsByOwner(userId,
                function done(dataContacts) {
                    $("#table-contacts-body").html("");
                    var contacts = [];
                    contacts.push(userId);
                    dataContacts.forEach(function (contact) {
                        contacts.push(contact.contactUser.id);
                    });
                    if (dataUsers.length != 0) {
                        dataUsers.forEach(function (user) {
                            var button = $("<button>add</button>")
                                .attr("type", "button")
                                .addClass("btn")
                                .attr("user-name", ((user.name) ? user.name : user.email))
                                .on("click", function () {
                                    addNewContactOnClick(this);
                                });
                            if ($.inArray(user.id, contacts) != -1) {
                                button.addClass("btn-success")
                                button.prop('disabled', true);
                            } else {
                                button.addClass("btn-danger")
                            }
                            $("#table-contacts-body")
                                .append(
                                    $("<tr></tr>")
                                        .attr("user-id", user.id)
                                        .append(
                                            $("<td>" + user.id + "</td>"))
                                        .append(
                                            $("<td><b>" + user.name + "</b></td>"))
                                        .append(
                                            $("<td>" + user.email + "</td>"))
                                        .append(
                                            $("<td></td>").append(button)
                                        ).on("click", function () {
                                        rowOnClick(this)
                                    })
                                )
                            ;
                        })
                    }
                },
                function fail() {
                    alert("get contact list error...");
                });
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
                addContactElement(contact);
            });

            $("#contacts li:first-child").addClass("active");
            $("#messages").html("");
            loadMessages();
        },
        function fail() {
            $("#contacts").html("");
        });
}

function addContactElement(contact) {
    $("#contacts").append(
        $("<li><a href=\"#\"><b><span>" + contact.contactName + "</span></b><br><sup>(" + contact.contactUser.email + ")</sup></a></li>")
            .attr("contact-id", contact.id)
            .attr("user-id", contact.contactUser.id)
            .on("click", function () {
                selectContact(this);
            })
    )
}

// set active selected contact
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
                        $("#contacts li.active a span").html($("#contact-new-name").val());
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
            var delUserId = $("#contacts li.active").attr("user-id");
            $("tbody tr[user-id=" + delUserId + "] .btn")
                .removeClass("btn-success")
                .addClass("btn-danger")
                .prop('disabled', false);
            $("#contacts li.active").remove();
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
        function done(contactId) {
            $("#table-contacts tbody tr.active .btn")
                .removeClass("btn-danger")
                .addClass("btn-success")
                .prop('disabled', true);
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
            restGetContactById(contactId,
                function done(contact) {
                    addContactElement(contact);
                },
                function fail() {
                    alert("get contact error...");
                })
        },
        function fail() {
            $("#modal-btn-save-contact").unbind();
            $("#modal-enter-name").modal("hide");
            alert("add contact error...");
        });
}