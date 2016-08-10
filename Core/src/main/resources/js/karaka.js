function updateMessages() {
    console.log("Updating messages.");
}

$(function() {
    $("#thread_update").click(function() {
        updateMessages();
        return false;
    });
    $("#post_send").click(function() {
        var nameField = $("#post_name");
        var messageField = $("#post_message");
        var request = $.ajax({
            url: $("#post_form").attr("action"),
            method: "post",
            data: {
                type: $("input[name='post_type']:checked").val(),
                name: nameField.val(),
                message: messageField.val()
            }
        });
        nameField.val("");
        messageField.val("");

        request.done(updateMessages);
        request.fail(function(response) {
            console.log("Error: " + $.parseJSON(response.responseText).message);
        });
    });
});