window.onload = function onLoad() {
    loadContacts();
    window.setInterval("loadMessages(false);", 1000);
};

function addContact() {

    if ($("#email")[0].value != "") {
        $.get("//localhost:8555/api/users/?email=" + $("#email")[0].value, function (data, status) {

            var user = data[0];
            if (user != undefined) {

                $.post("//localhost:8555/api/users/" + $("#userId")[0].value + "/contacts/" + user.id + "?name=" + $("#name")[0].value, function (data, status) {
                    if (status != "success") {
                        alert("error!");
                    }
                    loadContacts();
                });
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

    if ($("#contacts")[0] != undefined && $("#userId")[0] != undefined) {

        $.get("//localhost:8555/api/users/" + $("#userId")[0].value + "/contacts", function (data, status) {

            $("#contacts")[0].innerHTML = "";

            for (var index = 0; index < data.length; index++) {

                var currentContact = data[index];

                var newOption = document.createElement("option");
                newOption.setAttribute("id", currentContact.id);
                newOption.setAttribute("data-user-id", currentContact.contactUser.id);
                newOption.setAttribute("className", "contact");
                newOption.innerHTML = currentContact.contactName;

                $("#contacts")[0].insertBefore(newOption, $("#contacts")[0].lastElementChild);

            }
        })
    }
}