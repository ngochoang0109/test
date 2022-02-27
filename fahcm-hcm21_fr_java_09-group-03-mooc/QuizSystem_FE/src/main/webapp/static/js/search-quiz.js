   $(document).ready(function () {
	var contextRoot = /*[[@{/}]]*/ '' 
        $('#keySearch').keyup(function () {
            $("#list-auto").empty();
            if ($('#keySearch').val().trim().length !== 0) {
                $("#div-auto").css("height", "230px");
                $.ajax({
                    url: urlAutocomplte,
                    type: "get",
                    data: {
                        keySearch: $('#keySearch').val(),
                    },
                    success: function (data) {
                        $.each(data, function (i, item) {
                            $("#list-auto").append("<a class='list-group-item list-group-item-action pt-1 pb-1' href='/quiz/view/"+item.id+"'>" + item.title + "</a>");
                        });
                    },
                    error: function (b) {
                        alert("error")
                    }
                });
            }
        });
    });