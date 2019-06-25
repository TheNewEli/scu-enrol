$().ready(function () {
    $("#login_form").validate({
        rules: {
            loginName: "required",
            password: {
                required: true,
                minlength: 1
            },
        },
        messages: {
            loginName: "请输入登入名",
            password: {
                required: "请输入密码"

            },
        }
    });
});
$(function () {
    $("#register_btn").bind("click", function () {
        $("#register_form").css("display", "block");
        $("#login_form").css("display", "none");
    });
    $("#back_btn").bind("click", function () {
        $("#register_form").css("display", "none");
        $("#login_form").css("display", "block");
    });
});