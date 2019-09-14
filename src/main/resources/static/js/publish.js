$("input").focus(function () {
    $("#publishError").hide();
    $("#publishBlank").hide();
});

$("textarea").focus(function () {
    $("#publishError").hide();
    $("#publishBlank").hide();
});

function checkPublishForm() {
    if ($("#title").val().length == 0) {
        $("#publishBlankTip").text("标题不能为空哦！");
        $("#publishBlank").show();
        return false;
    }else if ($("#description").val().length == 0) {
        $("#publishBlankTip").text("描述不能为空哦！");
        $("#publishBlank").show();
        return false;
    }else if ($("#tag").val().length == 0) {
        $("#publishBlankTip").text("标签不能为空哦！");
        $("#publishBlank").show();
        return false;
    }
    return true;
}

