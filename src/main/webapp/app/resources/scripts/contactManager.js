window.onload = function onLoad() {
    loadContacts();
};

function addContact() {

    var email = $("#email");
    var name = $("#name");
    var ownerId = $("#userId");

    if (email.val() != "") {
        $.get("//localhost:8555/api/users/?email=" + email.val(), function (dataGet, status) {

            if (dataGet.length) {
                var user = dataGet[0];
                $.post("//localhost:8555/api/users/" + ownerId.val() + "/contacts/" + user.id + "?name=" + name.val(), function (dataPost, status) {
                    if (status != "success") {
                        alert("error!");
                    } else {
                        email.val("");
                        name.val("");
                    }
                    loadContacts();
                });
            } else {
                alert("user not found!")
            }
        });
    }

}

function deleteContact() {

    var currentContact = $("#contacts")[0].selectedOptions[0];
    if (currentContact != undefined) {
        $.ajax({
            url: "//localhost:8555/api/users/" + $("#userId")[0].value + "/contacts/" + currentContact.id,
            type: 'DELETE',
            success: function(result) {
                loadContacts();
            }
        });
    }
}

function loadContacts() {

    var contacts = $("#contacts");
    var userId = $("#userId");

    if (contacts.length && userId.length) {

        $.get("//localhost:8555/api/users/" + userId[0].value + "/contacts", function (data, status) {

            contacts.html("");

            $.each(data, function(index, element){
                $("<option />").
                    appendTo(contacts[0]).
                    addClass("contact").
                    attr("id", element.id).
                    attr("data-user-id", element.contactUser.id).
                    html(element.contactName);
            });
        })
    }
}