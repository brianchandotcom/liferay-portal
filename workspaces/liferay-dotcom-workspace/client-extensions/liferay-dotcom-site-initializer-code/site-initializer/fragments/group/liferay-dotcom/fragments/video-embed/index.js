/*global _wq */

var overlay = fragmentElement.querySelector('.f-video-embed-overlay');

if (overlay) {
	if (configuration.wistiaId) {
		window._wq = window._wq || [];

		_wq.push({
			id: overlay.getAttribute('data-wistia-id'),
			onReady: function (video) {
				overlay.addEventListener('click', function () {
					overlay.classList.add('inline-video');
					video.play();
				});
			},
		});
	}
	else if (configuration.html5videoUrl) {
		var video = fragmentElement.querySelector('video');

		overlay.addEventListener('click', function () {
			overlay.classList.add('inline-video');
			video.play();
		});
	}
	else if (configuration.youtubeId) {
		var videoPlayer = fragmentElement.querySelector('.f-video-embed-player');

		overlay.addEventListener('click', function () {
			videoPlayer.src += '&autoplay=1';
			overlay.classList.add('inline-video');
		});
	}
}