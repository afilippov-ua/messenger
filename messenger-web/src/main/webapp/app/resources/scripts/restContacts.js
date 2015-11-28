function restAddNewContact(userId, contactId, contactName, cbDone, cbFail) {
    $.ajax({
        url: restContactPath + "?ownerId=" + userId + "&contactId=" + contactId,
        method: "POST",
        headers: {"name": encodeURIComponent(contactName)}
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetContactById(contactId, cbDone, cbFail) {
    $.ajax({
        url: restContactPath + "/" + contactId,
        method: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetContactsByOwner(ownerId, cbDone, cbFail) {
    $.ajax({
        url: restContactPath + "?ownerId=" + ownerId,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

function restUpdateContact(contactId, contact, cbDone, cbFail) {
    $.ajax({
        url: restContactPath + "/" + contactId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(contact)
    })
        .done(cbDone)
        .fail(cbFail);
}

function restDeleteContact(contactId, cbDone, cbFail) {
    $.ajax({
        url: restContactPath + "/" + contactId,
        type: "DELETE"
    })
        .done(cbDone)
        .fail(cbFail);
}