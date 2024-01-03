document.addEventListener('DOMContentLoaded', function() {
	var anchors = document.querySelectorAll('.f-anchor-target');

	var anchorNavLinks = document.querySelectorAll('.anchor-nav-link');

	window.onscroll = function() {
		var current = '';

		anchors.forEach(function(anchor) {
			var sectionTop = anchor.offsetTop;

			var windowHeight = window.innerHeight;

			if (scrollY >= sectionTop - (windowHeight*0.33)) {
				current = anchor.getAttribute('id');
			}
		});

		anchorNavLinks.forEach(function(anchorNavLink) {
			anchorNavLink.classList.remove('active');
			if (
				anchorNavLink.classList.contains('anchor-nav-link-#' + current)
			) {
				anchorNavLink.classList.add('active');
			}
		});
	};
});