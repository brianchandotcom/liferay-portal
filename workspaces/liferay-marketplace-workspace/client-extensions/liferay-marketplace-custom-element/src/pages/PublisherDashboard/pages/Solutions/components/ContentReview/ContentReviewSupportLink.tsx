/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

interface ContentReviewSupportLinkProps {
	href: string;
	linkLabel: string;
	symbol: string;
	type: 'email' | 'phone' | 'url';
}

const PROTOCOLS = ['http://', 'https://'];

const SUPPORT_LINK_TYPE = {
	email: 'mailto:',
	phone: 'tel:',
	url: '',
};

export function ContentReviewSupportLink({
	href,
	linkLabel,
	symbol,
	type = 'url',
}: ContentReviewSupportLinkProps) {
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
		<div className="align-items-center d-flex my-5">
			<div className="align-items-center bg-light d-flex justify-content-center mr-5 suport-link">
				<ClayIcon symbol={symbol} />
			</div>
			<div>
				<p className="m-0">{linkLabel}</p>
				<a href={getPrefixHref(href)} target="_blank">
					<span className="bold h4 m-0 text-primary">{href}</span>
				</a>
			</div>
		</div>
	);
}
