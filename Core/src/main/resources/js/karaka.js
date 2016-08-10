function updateMessages() {
    var request = $.ajax({
        url: "/api" + window.location.pathname,
        method: "get"
    });
    request.done(function(response) {
        var postList = $("#post_list");
        postList.empty();
        var posts = response.message;
        for (var i = 0; i < posts.length; i++) {
            var post = $("<div></div>").addClass("post").attr("id", posts[i].post_id);

            var postHeader = $("<div></div>").addClass("post_header");
            postHeader.append($("<a></a>").addClass("post_id")
                .attr("href", "#" + posts[i].post_id)
                .text("#" + posts[i].post_id)).append(" | ");
            postHeader.append($("<span></span>").addClass("post_number")
                .text(i + 1)).append(" | ");
            postHeader.append($("<span></span>").addClass("post_timestamp")
                .text(dateFormat(new Date(posts[i].timestamp), "dd.mm.yyyy, HH:MM:ss"))).append(" | ");
            var posterName = $("<span></span>").text(posts[i].name);
            switch(posts[i].type) {
                case "OP":
                    posterName.addClass("post_op");
                    break;
                case "SAGE":
                    posterName.addClass("post_sage");
                    break;
                default:
                    posterName.addClass("post_normal");
            }
            postHeader.append(posterName);
            post.append(postHeader);

            post.append($("<div></div>").addClass("post_content").text(posts[i].message));

            postList.append(post);
        }
    });
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
