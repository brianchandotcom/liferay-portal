import ClaySticker from '@clayui/sticker';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import emptyPictureIcon from '../../../assets/icons/empty_picture_icon.svg';

type AccountInfoProps = {
	productCreatorAccount?: {logoURL: undefined; name: string} | Account;
	userAccount?: {[key: string]: string} | UserAccount;
};

const AccountEmailInfo = ({
	productCreatorAccount,
	userAccount,
}: AccountInfoProps) => (
	<div className="align-items-center d-flex">
		<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
			<strong>{productCreatorAccount?.name}</strong>

			<div className="account-banner-email-text">
				{userAccount?.emailAddress}
			</div>
		</div>

		<ClaySticker shape="circle" size="sm">
			<ClaySticker.Image
				alt="placeholder"
				height="24"
				src={productCreatorAccount?.logoURL ?? emptyPictureIcon}
				width="24"
			/>
		</ClaySticker>
	</div>
);

export default AccountEmailInfo;
