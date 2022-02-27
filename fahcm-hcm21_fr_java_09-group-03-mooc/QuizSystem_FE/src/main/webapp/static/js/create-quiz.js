
$(document).ready(function() {
    $(document).on("click", "#addQuestionBtn", function(e) {
        url = urlAddQuestion + $(this).data('value');
        $(this).parent("div").load(url);
        e.preventDefault();
    });

    $(document).on("click", ".addAnswerBtn", function(e) {
		n = $(this).data('value');
        url = urlAddAnswer + n + "/" + $(this).val();
        $(this).closest(".new-answer-container").load(url, function() {
			element = document.getElementById("questions" + n + ".multiple1");
			changeInputType(element);
		});
        e.preventDefault();
    });

    $(document).on("change", ".multiple", function() {
        changeInputType(this);
		$(".answer" + $(this).data('value')).prop('checked', false);
    });

	$(document).on("change", "input[type='radio']", function() {
		singleSelection(this);
	});

    $(document).on("click", ".dismissBtn", function() {
        $(this).closest(".card.question-card").remove();
    });
    
    $(document).on("click", ".dismissAnswerBtn", function() {
        $(this).closest(".row").remove();
    });

    function changeInputType(element) {
        if ($(element).is(':checked')) {
            $("#allAnswers" + $(element).data('value')).find("input[type='radio']").attr("type", 'checkbox');
        } else {
			$("#allAnswers" + $(element).data('value')).find("input[type='checkbox']").attr("type", 'radio');
		}
    }

	function singleSelection(element) {
		var n = $(element).attr('class').substring(23);
		$(".answer" + n).prop('checked', false);
		$(element).prop('checked', true);
	}
});