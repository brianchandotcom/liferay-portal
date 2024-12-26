/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import PopoverIcon from '../DXPCloud/components/PopoverIcon';

interface IProps {
	linkText?: string;
	url?: string;
}

const ActivationCardLink: React.FC<IProps> = ({linkText, url}) => {
	return (
		<div>
			<PopoverIcon
				symbol="question-circle-full"
				title="link-only-accessible-to-current-product-users-permissions-and-roles-are-managed-separately-within-each-product"
			/>

			<a
				className="font-weight-semi-bold m-0 p-0 text-brand-primary text-paragraph"
				href={url}
				rel="noopener noreferrer"
				target="_blank"
			>
				{linkText}

				<ClayIcon className="ml-1" symbol="order-arrow-right" />
			</a>
		</div>
	);
};

export default ActivationCardLink;
