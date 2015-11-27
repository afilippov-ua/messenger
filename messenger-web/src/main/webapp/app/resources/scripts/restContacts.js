function restAddNewContact(userId, contactId, contactName, callback) {
    $.ajax({
        url: "//localhost:8555/api/contacts?ownerId=" + userId + "&contactId=" + contactId,
        method: "POST",
        headers: {"name": contactName},
        success: callback,
        error: callback
    });
}

function restGetContactById(contactId, callback) {
    $.ajax({
        url: "//localhost:8555/api/contacts/" + contactId,
        method: "GET",
        success: callback,
        error: callback
    });
}

function restGetContactsByOwner(ownerId, callback) {
    $.ajax({
        url: "//localhost:8555/api/contacts?ownerId=" + ownerId,
        type: "GET",
        success: callback,
        error: callback
    });
}

function restUpdateContact(contactId, contact, callback) {
    $.ajax({
        url: "//localhost:8555/api/contacts/" + contactId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(contact),
        success: callback,
        error: callback
    });
}

function restDeleteContact(contactId, callback) {
    $.ajax({
        url: "//localhost:8555/api/contacts/" + contactId,
        type: "DELETE",
        success: callback,
        error: callback
    });
}