/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {KeyboardEvent, useCallback, useRef, useState} from 'react';

import {SendIcon} from './Icons';

interface ChatbotInputProps {
	disabled: boolean;
	onSubmit: (text: string) => void;
	placeholder: string;
}

export default function ChatbotInput({
	disabled,
	onSubmit,
	placeholder,
}: ChatbotInputProps) {
	const [value, setValue] = useState('');
	const textareaRef = useRef<HTMLTextAreaElement>(null);

	const adjustHeight = useCallback(() => {
		const textarea = textareaRef.current;

		if (!textarea) {
			return;
		}

		const style = window.getComputedStyle(textarea);
		const lineHeight =
			parseFloat(style.lineHeight) || parseFloat(style.fontSize) * 1.2;
		const maxHeight = lineHeight * 4;

		textarea.style.height = 'auto';

		const newHeight = Math.min(textarea.scrollHeight, maxHeight);

		textarea.style.height = newHeight + 'px';
		textarea.style.overflowY =
			textarea.scrollHeight > maxHeight ? 'auto' : 'hidden';
	}, []);

	const handleSubmit = useCallback(() => {
		const trimmed = value.trim();

		if (!trimmed || disabled) {
			return;
		}

		setValue('');

		if (textareaRef.current) {
			textareaRef.current.style.height = 'auto';
		}

		onSubmit(trimmed);
	}, [disabled, onSubmit, value]);

	const handleKeyDown = useCallback(
		(event: KeyboardEvent<HTMLTextAreaElement>) => {
			if (event.key === 'Enter' && !event.shiftKey) {
				event.preventDefault();

				handleSubmit();
			}
			else if (event.key === 'Enter' && event.shiftKey) {
				requestAnimationFrame(adjustHeight);
			}
		},
		[adjustHeight, handleSubmit]
	);

	return (
		<div className="aihub-input-area">
			<textarea
				className="aihub-textarea"
				disabled={disabled}
				onChange={(event) => {
					setValue(event.target.value);

					requestAnimationFrame(adjustHeight);
				}}
				onKeyDown={handleKeyDown}
				placeholder={placeholder}
				ref={textareaRef}
				rows={1}
				value={value}
			/>

			<button
				aria-label="Send"
				className="aihub-send"
				disabled={disabled || !value.trim()}
				onClick={handleSubmit}
			>
				<SendIcon />
			</button>
		</div>
	);
}
