$(document).ready(function($) {
    $(document).on("change", "input[type='radio']", function() {
        var n = $(this).attr("id").split('.')[0].substring(9);
        $("input[id^='questions" + n + ".answers']").prop('checked', false);
        $(this).prop('checked', true);
    });
});