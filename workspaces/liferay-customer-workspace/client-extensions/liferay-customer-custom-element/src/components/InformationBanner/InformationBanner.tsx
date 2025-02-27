/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

interface IProps {
	alertType?: 'danger' | 'info' | 'success' | 'warning';
	content: string;
	dismissible?: boolean;
	icon?: string;
	onDismiss?: () => void;
}

const InformationBanner = ({
	alertType = 'info',
	content,
	dismissible = true,
	icon,
	onDismiss,
}: IProps) => {
	return (
		<div
			className={classNames(
				'alert-fluid',
				'alert-' + alertType,
				'align-items-center',
				'd-flex',
				'information-banner',
				'justify-content-between',
				'mb-4',
				'px-4',
				'py-3'
			)}
		>
			<div className="align-items-center d-flex information-banner-content">
				{icon && icon !== 'none' && (
					<div className="alert-indicator information-banner-icon mr-2">
						<ClayIcon symbol={icon} />
					</div>
				)}

				<div dangerouslySetInnerHTML={{__html: content}} />
			</div>

			{dismissible && (
				<button
					aria-label="Close"
					className="close"
					onClick={onDismiss}
					type="button"
				>
					<div>
						<ClayIcon symbol="times" />
					</div>
				</button>
			)}
		</div>
	);
};

export default InformationBanner;
