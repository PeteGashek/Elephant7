function updateMessages() {
    console.log("Updating messages.");
}

$(function() {
    $("#thread_update").click(function() {
        updateMessages();
        return false;
    });
    $("#post_send").click(function() {
        if ($("#new_thread").length) return true;
        
        var form = $("#post_form");
        var request = $.ajax({
            url: "/api" + form.attr("action"),
            method: form.attr("method"),
            data: form.serialize()
        });
        form[0].reset();

        request.done(updateMessages);
        request.fail(function(response) {
            console.log("Error: " + $.parseJSON(response.responseText).message);
        });

        return false;
    });
});
