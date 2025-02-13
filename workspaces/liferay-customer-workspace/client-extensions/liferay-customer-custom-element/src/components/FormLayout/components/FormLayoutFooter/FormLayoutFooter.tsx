/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface IProps {
	footerClass?: string;
	leftButton?: React.ReactNode;
	middleButton?: React.ReactNode;
	rightButton?: React.ReactNode;
}

const FormLayoutFooter: React.FC<IProps> = ({
	footerClass,
	leftButton,
	middleButton,
	rightButton,
}) => {
	const isCornerButton = leftButton || rightButton;

	return (
		<div
			className={classNames('d-flex', 'p-4', footerClass, {
				'justify-content-between': isCornerButton,
				'justify-content-center': !isCornerButton,
			})}
		>
			{leftButton}

			{middleButton}

			{rightButton}
		</div>
	);
};

export default FormLayoutFooter;
