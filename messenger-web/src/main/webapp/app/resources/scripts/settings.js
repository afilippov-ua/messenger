var globalPath,
    restUserPath,
    restContactPath,
    restMessagePath,
    globalFilePath,
    globalAvatarPath;

$(document).ready(function () {
    globalPath = $("#app-path").val();
    restUserPath = globalPath + "/api/users";
    restContactPath = globalPath + "/api/contacts";
    restMessagePath = globalPath + "/api/messages";
    globalFilePath = globalPath + "/api/files";
    globalAvatarPath = globalFilePath + "/avatars";
});