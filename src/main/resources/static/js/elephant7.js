var autoupdateInterval = 5000;
var notificationTimeout = 5000;
var maxNameLength = 32;
var maxMessageBytes = 65535;

var autoupdateEnabled = false;
var autoupdateTimer;
var autoupdateTimerValue;

function insertReplyLinkInMsg(messageRefId) {
    document.getElementById("post_message").value=messageRefId;
    //TODO: Kill after testing
    //     alert(messageRefId);
}
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
        .text(new Date(json.timestamp).format("dd.mm.yyyy, HH:MM:ss"))).append(" | ");
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

    var postFooter = $("<a>Reply</a>").addClass("reply_link").attr("href", "#post_form").attr("id","Reply");
    post.append(postFooter);
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
    request.fail(function() {
        showNotification("Failed to receive messages!")
    });
    request.always(function() {
        if (autoupdateEnabled) startAutoupdateTimer();
    });
}

function utf8ByteLength(text) {
    var count = 0;
	var length = text.length;
	for (var i = 0; i < length; i++) {
		var c = text.charCodeAt(i);

		if (c <= 0x7F) count++;
		else if (c <= 0x7FF) count += 2;
		else if (c >= 0xD800 && c <= 0xDC00) {
			count += 4;
			++i;
		} else count += 3;
	}
	return count;
}

function showNotification(text) {
    var notification = $("<div></div>").addClass("notification").text(text);
    $("#notification_list").prepend(notification);
    setTimeout(function() {
        notification.remove();
    }, notificationTimeout);
}

$(function() {
    $("#page_top_button").click(function() {
        $("html,body").scrollTop($("a[name='page_top']").offset().top);
        return false;
    });
    $("#page_bottom_button").click(function() {
        $("html,body").scrollTop($("a[name='page_bottom']").offset().top);
        return false;
    });

    $("#thread_update").click(function() {
        updateMessages();
        return false;
    });

    $("#thread_autoupdate").show(); // We will show it only if JS works
    $("#thread_autoupdate_checkbox").change(function() {
        autoupdateEnabled = $(this).prop("checked");
        if (autoupdateEnabled) startAutoupdateTimer();
        else stopAutoupdateTimer();
    });

    // In case "maxlength" doesn't work, but JS does
    var oldPostNameText = "";
    $("#post_name").on("textchange", function() {
        var name = $(this).val();
        if (name.length > maxNameLength) {
            $(this).val(oldPostNameText);
            showNotification("Name is too long!");
        } else oldPostNameText = name;
    });

    // We will handle it manually by counting bytes so the user can post bigger messages
    var postMessageByteCounter = $("#post_message_byte_counter").text(maxMessageBytes).show();
    var postMessageBytes = 0;
    $("#post_message").removeAttr("maxlength").on("textchange", function() {
        postMessageBytes = utf8ByteLength($(this).val());
        postMessageByteCounter.text(maxMessageBytes - postMessageBytes)
            .removeClass()
            .addClass(postMessageBytes <= maxMessageBytes ? "message_bytes_good" : "message_bytes_bad");
    });

    $("#post_send").click(function() {
        if ($("#new_thread").length) return true; // Thread creation is handled by backend

        if (postMessageBytes > maxMessageBytes) {
            showNotification("Message is too long!");
            return false;
        }

        var form = $("#post_form");
        var request = $.ajax({
            url: "/api" + form.attr("action"),
            method: form.attr("method"),
            data: form.serialize()
        });
        form[0].reset();
        postMessageBytes = 0;

        request.done(function() {
            showNotification("Posted!");
            updateMessages();
        });
        request.fail(function(response) {
            showNotification($.parseJSON(response.responseText).message);
        });

        return false;
    });
});
