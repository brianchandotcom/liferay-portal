/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const overlay = fragmentElement.querySelector('.f-video-embed-overlay');

if (overlay) {
	if (configuration.wistiaId) {
		window._wq = window._wq || [];

		window._wq.push({
			id: overlay.dataset.wistiaId,
			onReady(video) {
				overlay.addEventListener('click', () => {
					overlay.classList.add('inline-video');
					video.play();
				});
			},
		});
	}
	else if (configuration.html5videoUrl) {
		const video = fragmentElement.querySelector('video');

		overlay.addEventListener('click', () => {
			overlay.classList.add('inline-video');
			video.play();
		});
	}
	else if (configuration.youtubeId) {
		const videoPlayer = fragmentElement.querySelector(
			'.f-video-embed-player'
		);

		overlay.addEventListener('click', () => {
			videoPlayer.src += '&autoplay=1';
			overlay.classList.add('inline-video');
		});
	}
}
