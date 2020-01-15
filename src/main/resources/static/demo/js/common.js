$(".list_dt").on("click", function() {
    $('.list_dd').stop();
    $(this).siblings("dt").removeAttr("id");
    if ($(this).attr("id") == "open") {
        $(this).removeAttr("id").siblings("dd").slideUp();
    } else {
        $(this).attr("id", "open").next().slideDown().siblings("dd").slideUp();
    }
});


var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdI7nKKyhxF5IqIj39xFz8Dess7zcTs7eCAZshLejTal+QlSnGcdugBnh54pfoofM9mJ1rPcLfDjX84/SrhuE4Zg8V5ecE++56Fcwk+1jZLS0SJGxAgAmOnwA2cQgUWzgonpPcmSufNevVVIgLM3A/1Yibhm1lu4rZjIPzDcU+aQIDAQAB";