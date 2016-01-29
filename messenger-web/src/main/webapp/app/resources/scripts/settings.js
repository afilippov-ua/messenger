var globalPath, restUserPath, restContactPath, restMessagePath;

$(document).ready(function () {
    globalPath = $("#app-path").val();
    restUserPath = globalPath + "/api/users";
    restContactPath = globalPath + "/api/contacts";
    restMessagePath = globalPath + "/api/messages";
});