$(document).ready(function($) {
    $("form").validate({
        rules: {
            name: {
                required: true,
                minlength: 2,
            },
            email: {
                required: true,
                email: true,
            },
            password: {
                required: true,
                minlength: 8
            },
            repassword: {
                required: true,
                equalTo : "#password"
            },
            role: {
                required: true
            },
        },
        messages: {
            name: {
                required: "PLEASE ENTER YOUR NAME",
                minlength: "NAME MUST BE MINIMUM OF 2 CHARACTERS"
            },
            email: {
                required: "PLEASE ENTER YOUR EMAIL ADDRESS",
                email: "INVALID EMAIL ADDRESS",
            },
            password: {
                required: "PLEASE ENTER A PASSWORD",
                minlength: "PASSWORD MUST BE MINIMUM OF 8 CHARACTERS"
            },
            repassword: {
                required: "PLEASE CONFIRM THE ABOVE PASSWORD",
                equalTo : "PASSWORDS DO NOT MATCH"
            },
            role: {
                required: "PLEASE SELECT YOUR OCCUPATION"
            },
        },
        errorPlacement: function(error, element) {
            if (element.is("#role")) {
                error.appendTo("#role-error");
            }
            else {
                element.after(error);
            }
        },
        highlight: function (element) {
            // disable button
            $(':input[type="submit"]').prop('disabled', true);
            // add a class "errorClass" to the element
            $(element).addClass('errorClass');
            $(element).next().hide();
        },
        unhighlight: function (element) {
            // enable button
            $(':input[type="submit"]').prop('disabled', false);
            // class "errorClass" remove from the element
            $(element).removeClass('errorClass');
            $(element).next().next().show();
        },
    });
});