/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

interface IProps {
	leftContent: string;
	rightContent: string;
}

const ResumeCard = ({
	className,
	leftContent,
	rightContent,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div
		className={classNames(
			'bg-neutral-1 rounded d-flex justify-content-between p-3 align-items-center overflow-auto',
			className
		)}
	>
		<div className="font-weight-semi-bold text-paragraph">
			{leftContent}
		</div>

		<div className="font-weight-semi-bold text-paragraph">
			{rightContent}
		</div>
	</div>
);

export default ResumeCard;
