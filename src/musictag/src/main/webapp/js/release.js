$(function() {
	addMoreControlListener();
	addMoreListener();
});

function addMoreListener() {
	$('[data-more]').on('click', function() {
		var name = $(this).closest('tr').find('.name');
		var flag = 'data-name';
		if (name.attr(flag) === 'hide') {
			name.attr(flag, 'show');
			name.find('.detail').fadeOut(500);
		} else {
			name.attr(flag, 'hide');
			name.find('.detail').fadeIn(500);
		}
	});
}

function addMoreControlListener() {
	$('.track-table tr').on('mouseover', function() {
		$(this).find('[data-more] a').show();
	});
	$('.track-table tr').on('mouseout', function() {
		$(this).find('[data-more] a').hide();
	});
}