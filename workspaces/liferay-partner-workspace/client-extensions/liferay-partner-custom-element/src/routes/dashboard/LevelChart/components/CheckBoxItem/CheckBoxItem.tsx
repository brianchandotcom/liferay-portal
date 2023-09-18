/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactNode} from 'react';

interface IPropsCheckBoxItem {
	children?: ReactNode;
	completed: boolean | undefined;
	text?: string;
	title?: string;
}

const CheckBoxItem = ({
	children,
	completed,
	text,
	title,
}: IPropsCheckBoxItem) => {
	const CheckIcon = () => {
		if (completed) {
			return (
				<ClayIcon
					className="m-0 text-brand-primary"
					symbol="check-circle"
				/>
			);
		}

		return <ClayIcon className="m-0 text-danger" symbol="times-circle" />;
	};

	return (
		<div className="d-flex mb-4">
			<div
				className={classNames('d-flex p-0 align-items-center', {
					'col': !children,
					'col-3': children,
				})}
			>
				<CheckIcon />

				<span
					className={classNames(
						'font-weight-bold text-paragraph-sm',
						{
							'col': !text,
							'col-3': text,
						}
					)}
				>
					{title}
				</span>

				{text && <span className="col text-paragraph">{text}</span>}
			</div>

			{children && <div className="col">{children}</div>}
		</div>
	);
};

export default CheckBoxItem;
