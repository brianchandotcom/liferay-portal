/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';

interface ContentReviewParagraphProps {
	children: ReactNode;
	className?: string;
	title?: string;
}

export function ContentReviewParagraph({
	children,
	className,
	title,
}: ContentReviewParagraphProps) {
	return (
		<div className={classNames('mb-4', className)}>
			{title && (
				<span className="d-flex mb-2">
					<span className="h5 mb-0">{title}</span>
				</span>
			)}
			<div className="solution-preview-profile-description-text">
				{children}
			</div>
		</div>
	);
}
