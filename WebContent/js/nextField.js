$(document).ready(function() {
	document.getElementById(sequence[0]).focus();
});
$(document).keypress(function(e) {
	if (e.which == 13) {
		var nextId = findNextId(e.target.id);
		if (nextId != "") {
			document.getElementById(nextId).focus();
		}
	}
});

function findNextId(current) {
	if (jQuery.inArray(current, sequence) >= 0) {
		if (sequence.length > jQuery.inArray(current, sequence) + 1) {
			if ($(
					document.getElementById(sequence[jQuery.inArray(current,
							sequence) + 1])).is(":visible")) {
				return sequence[jQuery.inArray(current, sequence) + 1];
			} else {
				return findNextId(sequence[jQuery.inArray(current, sequence) + 1]);
			}

		}
	}
	return "";
}