/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ButtonProps} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Clipboard from 'clipboard';
import React, {useEffect, useState} from 'react';

interface ICopyButtonProps {
	buttonText?: string;
	className?: string;
	displayType?: ButtonProps['displayType'];
	onClick?: (event: React.MouseEvent) => void;
	position?: string;
	text: string;
}

const CopyButton: React.FC<ICopyButtonProps> = ({
	buttonText,
	displayType,
	onClick,
	text,
	...otherProps
}) => {
	const [title, setTitle] = useState(Liferay.Language.get('click-to-copy'));

	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		_clipboard.on('success', (event) => {
			setTitle(Liferay.Language.get('copied'));

			event.clearSelection();
		});

		return () => _clipboard.destroy();
	}, []);

	return (
		<ClayButton
			aria-label={Liferay.Language.get('click-to-copy')}
			className="button-root"
			data-clipboard-text={text}
			displayType={displayType}
			onClick={onClick}
			title={title}
			{...otherProps}
		>
			{buttonText || <ClayIcon className="icon-root" symbol="copy" />}
		</ClayButton>
	);
};

export default CopyButton;
