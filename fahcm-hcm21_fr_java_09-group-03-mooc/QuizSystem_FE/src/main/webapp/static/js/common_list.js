function clearFilter(link) {
	window.location = link;	
}

function showDeleteConfirmModal(link, entityName,nextURL) {
	entityId = link.attr("entityId");
	console.log(link.attr("href"));
	console.log(nextURL);
	  $('#yesButton').on('click',function () {
			console.log('Click');
            deleteNew(link.attr("href"),nextURL);
        });

	/*$("#yesButton").attr("href", link.attr("href"));	*/
	
	
	$("#confirmText").text("Are you sure you want to delete this "
							 + entityName + " ID " + entityId + "?");
	$("#confirmModal").modal();	
}

function deleteNew(url,nextURL) {
	console.log('In');
		console.log(nextURL);
    $.ajax({
        url: url,
        type: 'DELETE',
        contentType: 'application/json',

       success: function (result) {
            window.location.href = nextURL+'?mess=success#mycollection-tab';
        },
        error: function (error) {
            window.location.href = nextURL+'?mess=error#mycollection-tab';
        }
    });
}