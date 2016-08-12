var autoupdateInterval = 5000;
var autoupdateEnabled = false;
var autoupdateTimer;
var autoupdateTimerValue;

function startAutoupdateTimer() {
    clearTimeout(autoupdateTimer);
    autoupdateTimerValue = autoupdateInterval;
    var timer = function() {
        autoupdateTimerValue -= 1000;
        if (autoupdateTimerValue <= 0) updateMessages();
        else {
            setAutoupdateNotifierText("(" + (autoupdateTimerValue / 1000) + ")");
            autoupdateTimer = setTimeout(timer, 1000);
        }
    };
    autoupdateTimer = setTimeout(timer, 1000);
    setAutoupdateNotifierText("(" + (autoupdateTimerValue / 1000) + ")");
}

function stopAutoupdateTimer() {
    clearTimeout(autoupdateTimer);
    setAutoupdateNotifierText("");
}

function setAutoupdateNotifierText(text) {
    $("#thread_autoupdate_notifier").text(text);
}

function constructPost(json, id) {
    var post = $("<div></div>").addClass("post").attr("id", json.post_id);

    var postHeader = $("<div></div>").addClass("post_header");
    postHeader.append($("<a></a>").addClass("post_id")
        .attr("href", "#" + json.post_id)
        .text("#" + json.post_id)).append(" | ");
    postHeader.append($("<span></span>").addClass("post_number")
        .text(id)).append(" | ");
    postHeader.append($("<span></span>").addClass("post_timestamp")
        .text(dateFormat(new Date(json.timestamp), "dd.mm.yyyy, HH:MM:ss"))).append(" | ");
    var posterName = $("<span></span>").text(json.name);
    switch(json.type) {
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

    var postContent = $("<div></div>").addClass("post_content");
    var lines = json.message.split("\n");
    for (var i = 0; i < lines.length; i++) {
        postContent.append(document.createTextNode(lines[i]));
        if (i < lines.length - 1) postContent.append($("<br/>"));
    }
    post.append(postContent);

    return post;
}

function updateMessages() {
    if (autoupdateEnabled) {
        stopAutoupdateTimer();
        setAutoupdateNotifierText("(...)");
    }
    var request = $.ajax({
        url: "/api" + window.location.pathname,
        method: "get"
    });
    request.done(function(response) {
        var postList = $("#post_list");
        postList.empty();
        var posts = response.message;
        for (var i = 0; i < posts.length; i++) {
            postList.append(constructPost(posts[i], i + 1));
        }
    });
    request.always(function() {
        if (autoupdateEnabled) startAutoupdateTimer();
    });
}

$(function() {
    $("#thread_autoupdate").show(); // Will show only if JS works
    $("#thread_autoupdate_checkbox").change(function() {
        autoupdateEnabled = $(this).prop("checked");
        if (autoupdateEnabled) startAutoupdateTimer();
        else stopAutoupdateTimer();
    });

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
