/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './CardLink.scss';

interface CardLinkProps {
	description: string;
	icon?: string;
	title: string;
}

const PROTOCOLS = ['http://', 'https://'];

const SUPPORT_LINK_TYPE = {
	email: 'mailto:',
	url: '',
};

export function CardLink({description, icon, title}: CardLinkProps) {
	const type = description?.includes('@') ? 'email' : 'url';

	const getPrefixHref = (href: string) => {
		const hasProtocol = PROTOCOLS.some((protocol) =>
			href.includes(protocol)
		);

		if (type === 'url') {
			if (hasProtocol) {
				return href;
			}

			return `https://${href}`;
		}

		return `${SUPPORT_LINK_TYPE[type]}${href}`;
	};

	return (
		<div className="card-link-container">
			<div className="card-link-main-info">
				<div className="card-link-icon">
					<ClayIcon
						aria-label="Icon"
						className="card-link-icon-image"
						symbol={icon as string}
					/>
				</div>

				<div className="card-link-info">
					<span className="card-link-info-text">{title}</span>
					{description && (
						<a
							className="card-link-info-description"
							href={getPrefixHref(description)}
							target="_blank"
						>
							{description}
						</a>
					)}
				</div>
			</div>
		</div>
	);
}
