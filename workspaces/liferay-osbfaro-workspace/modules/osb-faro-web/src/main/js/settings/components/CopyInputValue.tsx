/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import Clipboard from 'clipboard';
import React, {useEffect, useRef, useState} from 'react';
import {Alert} from '~/shared/types';

interface ICopyInputValueProps {
	addAlert: (params: {
		alertType: any;
		message: string;
		timeout?: boolean;
	}) => any;
	disabled?: boolean;
	title?: string;
	value: string;
}

const CopyInputValue = ({
	addAlert,
	disabled,
	title,
	value,
}: ICopyInputValueProps) => {
	const [isUrlCopied, setIsUrlCopied] = useState(false);
	const [copyTitle, setCopyTitle] = useState(
		Liferay.Language.get('click-to-copy')
	);
	const buttonRef = useRef(null);

	useEffect(() => {
		if (!buttonRef.current) {
			return;
		}

		const _clipboard = new Clipboard(buttonRef.current);

		_clipboard.on('success', (event: {clearSelection: () => void}) => {
			setCopyTitle(Liferay.Language.get('copied'));

			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get(
					'copied-successfully-to-the-clipboard'
				),
			});

			setTimeout(() => {
				setCopyTitle(Liferay.Language.get('click-to-copy'));
				setIsUrlCopied(false);
			}, 3000);

			event.clearSelection();
		});

		return () => _clipboard.destroy();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayForm.Group
			className={getCN({
				'has-success': isUrlCopied,
			})}
		>
			{title && <label htmlFor="value">{title}</label>}

			<ClayInput.Group>
				<ClayInput.GroupItem prepend>
					<ClayInput
						disabled={disabled}
						id="value"
						insetAfter
						name="value"
						readOnly={!isUrlCopied}
						type="text"
						value={value ? value : Liferay.Language.get('loading')}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append shrink>
					<ClayButton
						aria-label={copyTitle}
						data-clipboard-text={value}
						disabled={disabled}
						displayType={isUrlCopied ? 'success' : 'secondary'}
						onClick={() => setIsUrlCopied(true)}
						outline
						ref={buttonRef}
						title={copyTitle}
					>
						<ClayIcon symbol={isUrlCopied ? 'check' : 'copy'} />
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
};

export {CopyInputValue};
