/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

interface ContentReviewSuportLinkProps {
	href: string;
	linkLabel: string;
	symbol: string;
}

export function ContentReviewSuportLink({
	href,
	linkLabel,
	symbol,
}: ContentReviewSuportLinkProps) {
	const hrefType = href.includes('@') ? 'mailto:' : '';

	return (
		<div className="align-items-center d-flex my-5">
			<div className="align-items-center bg-light d-flex justify-content-center mr-5 suport-link">
				<ClayIcon symbol={symbol} />
			</div>
			<div>
				<p className="m-0">{linkLabel}</p>
				<a href={`${hrefType}${href}`} target="_blank">
					<h4 className="bold m-0 text-primary">{href}</h4>
				</a>
			</div>
		</div>
	);
}
