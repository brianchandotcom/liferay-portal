/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_FONT_SIZE = 16;

document.addEventListener('DOMContentLoaded', () => {
	let currentFontSize = DEFAULT_FONT_SIZE;

	const controlPanel = document.createElement('div');

	controlPanel.id = 'accessibility-control-panel';
	controlPanel.style.position = 'fixed';
	controlPanel.style.top = '20px';
	controlPanel.style.right = '20px';
	controlPanel.style.backgroundColor = 'transparent';
	controlPanel.style.border = 'none';
	controlPanel.style.padding = '5px';
	controlPanel.style.zIndex = '9999';
	controlPanel.style.fontFamily = 'Arial, sans-serif';
	controlPanel.style.display = 'flex';
	controlPanel.style.gap = '5px';
	controlPanel.style.alignItems = 'center';

	const increaseButton = document.createElement('button');

	increaseButton.textContent = 'A+';
	increaseButton.style.padding = '5px 10px';
	increaseButton.style.border = '1px solid #1e6f6f';
	increaseButton.style.backgroundColor = '#1e6f6f';
	increaseButton.style.color = '#fff';
	increaseButton.style.borderRadius = '4px';
	increaseButton.style.cursor = 'pointer';
	increaseButton.style.fontSize = '12px';
	increaseButton.addEventListener('click', () => {
		if (currentFontSize < 24) {
			currentFontSize += 2;
			document.documentElement.style.fontSize = `${currentFontSize}px`;
		}
	});

	const decreaseButton = document.createElement('button');

	decreaseButton.textContent = 'A-';
	decreaseButton.style.padding = '5px 10px';
	decreaseButton.style.border = '1px solid #1e6f6f';
	decreaseButton.style.backgroundColor = '#1e6f6f';
	decreaseButton.style.color = '#fff';
	decreaseButton.style.borderRadius = '4px';
	decreaseButton.style.cursor = 'pointer';
	decreaseButton.style.fontSize = '12px';
	decreaseButton.addEventListener('click', () => {
		if (currentFontSize > 12) {
			currentFontSize -= 2;
			document.documentElement.style.fontSize = `${currentFontSize}px`;
		}
	});

	const grayscaleToggle = document.createElement('div');

	grayscaleToggle.style.width = '20px';
	grayscaleToggle.style.height = '20px';
	grayscaleToggle.style.borderRadius = '50%';
	grayscaleToggle.style.background =
		'linear-gradient(to right, black 50%, white 50%)';
	grayscaleToggle.style.cursor = 'pointer';
	grayscaleToggle.style.border = '1px solid #ccc';
	grayscaleToggle.addEventListener('click', (event) => {
		const clickX = event.offsetX;
		const toggleWidth = grayscaleToggle.offsetWidth;
		if (clickX < toggleWidth / 2) {
			document.documentElement.style.filter = 'grayscale(100%)';
		}
		else {
			document.documentElement.style.filter = 'none';
		}
	});

	controlPanel.appendChild(decreaseButton);
	controlPanel.appendChild(increaseButton);
	controlPanel.appendChild(grayscaleToggle);

	document.body.appendChild(controlPanel);
});
