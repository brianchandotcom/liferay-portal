/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export function BotIcon() {
	return (
		<svg viewBox="0 0 24 24">
			<path d="M12 2a2 2 0 012 2c0 .74-.4 1.39-1 1.73V7h1a7 7 0 017 7v1a3 3 0 01-3 3H6a3 3 0 01-3-3v-1a7 7 0 017-7h1V5.73A2 2 0 0112 2zm-4 9a2 2 0 100 4 2 2 0 000-4zm8 0a2 2 0 100 4 2 2 0 000-4z" />
		</svg>
	);
}

export function ChatIcon() {
	return (
		<svg viewBox="0 0 24 24">
			<path d="M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm0 14H6l-2 2V4h16v12z" />
		</svg>
	);
}

export function CloseIcon() {
	return (
		<svg viewBox="0 0 16 16">
			<path d="M9.41 8l4.3-4.29a1 1 0 10-1.42-1.42L8 6.59l-4.29-4.3a1 1 0 00-1.42 1.42L6.59 8l-4.3 4.29a1 1 0 101.42 1.42L8 9.41l4.29 4.3a1 1 0 001.42-1.42z" />
		</svg>
	);
}

export function ErrorIcon() {
	return (
		<svg viewBox="0 0 16 16">
			<circle cx="8" cy="8" fill="#da1414" r="8" />

			<path d="M7 4h2v5H7zm0 6h2v2H7z" fill="#fff" />
		</svg>
	);
}

export function SendIcon() {
	return (
		<svg viewBox="0 0 24 24">
			<path d="M3.4 20.4l17.45-7.48a1 1 0 000-1.84L3.4 3.6a.993.993 0 00-1.39.91L2 9.12c0 .5.37.93.87.99L17 12 2.87 13.88c-.5.07-.87.5-.87 1l.01 4.61c0 .71.73 1.2 1.39.91z" />
		</svg>
	);
}

export function StarsIcon() {
	return (
		<svg viewBox="0 0 16 16">
			<path d="M8 0l1.5 4.5L14 6l-4.5 1.5L8 12l-1.5-4.5L2 6l4.5-1.5L8 0zm5 8l.9 2.6L16 11.5l-2.1.9L13 15l-.9-2.6-2.1-.9 2.1-.9L13 8zM3 10l.6 1.7L5 12.3l-1.4.6L3 14.6l-.6-1.7L1 12.3l1.4-.6L3 10z" />
		</svg>
	);
}
