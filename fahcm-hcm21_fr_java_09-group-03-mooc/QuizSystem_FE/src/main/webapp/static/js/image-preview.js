function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('.img-preview')
                .attr('src', e.target.result)
                .width(100)
                .height(100)
                .css("opacity", "0.9");
        };
        reader.readAsDataURL(input.files[0]);
    }
}
function submitFile() {
    $(".file-upload-form").submit(); // Form submission
}
