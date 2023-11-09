/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';

type TitleSubtitleHeaderProps = {
	bold?: boolean;
	subtitle?: string;
	title: ReactNode;
};

const TitleSubtitleHeader: React.FC<TitleSubtitleHeaderProps> = ({
	bold = true,
	subtitle,
	title,
}) => (
	<>
		<p
			className={classNames('description m-1', {
				'description-title font-weight-bold': bold,
			})}
		>
			{title}
		</p>

		{subtitle && <p className="description m-1">{subtitle}</p>}
	</>
);

export default TitleSubtitleHeader;
