window.onload = function onLoad() {
    loadContacts();
}

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
}

function onContactClick(elem) {
    $("#contacts li.active").removeClass("active");
    $(elem).addClass("active");
    $("#messages").html("");
    loadMessages()
}

function getContactsByEmailEvent(event) {
    if (event.keyCode == 13)
        getContactsByEmail();
}

function getContactsByEmail() {
    $("#table-contacts-body").html("");
    var email = $("#find-email").val();
    if (email == "")
        return;

    $.ajax({
        url: "//localhost:8555/api/users",
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader("email", email);
        },
        success: function (result) {
            $("#table-contacts-body").html("");
            if (result.length != 0) {
                result.forEach(function (user) {
                    $("#table-contacts-body")
                        .append(
                        $("<tr></tr>")
                            .append(
                            $("<td>" + user.id + "</td>"))
                            .append(
                            $("<td><b>" + user.name + "</b></td>"))
                            .append(
                            $("<td>" + user.email + "</td>"))
                            .append(
                            $("<td></td>").append(
                                $("<button>add</button>")
                                    .attr("type", "button")
                                    .addClass("btn")
                                    .addClass("btn-danger"))
                        )

                    );
                })
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("error: " + textStatus);
        }
    });
}

function getContactsByNameEvent(event) {
    if (event.keyCode == 13)
        getContactsByName();
}

function getContactsByName() {
    $("#table-contacts-body").html("");
    var name = $("#find-name").val();
    if (name == "")
        return;

    $.ajax({
        url: "//localhost:8555/api/users",
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader("name", name);
        },
        success: function (result) {
            $("#table-contacts-body").html("");
            if (result.length != 0) {
                result.forEach(function (user) {
                    $("#table-contacts-body")
                        .append(
                        $("<tr onclick=\"rowOnClick(this)\"></tr>")
                            .append(
                                $("<td>" + user.id + "</td>"))
                            .append(
                                $("<td><b>" + user.name + "</b></td>"))
                            .append(
                                $("<td>" + user.email + "</td>"))
                            .append(
                                $("<td></td>").append(
                                        $("<button>add</button>")
                                            .attr("type", "button")
                                            .addClass("btn")
                                            .addClass("btn-xs")
                                            .addClass("btn-danger"))
                                    )

                    );
                })
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("error: " + textStatus);
        }
    });
}

function rowOnClick(elem) {
    $("#table-contacts tr.active").removeClass("active");
    $(elem).addClass("active");
}