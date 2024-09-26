/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';

import './index.scss';

type AccountInfoProps = {
	userAccount?: {
		emailAddress?: string;
		image?: string;
		name?: string;
	};
};

const AccountEmailInfo: React.FC<AccountInfoProps> = ({userAccount}) => (
	<div className="align-items-center d-flex">
		<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
			<strong>{userAccount?.name}</strong>

			<div className="account-banner-email-text">
				{userAccount?.emailAddress}
			</div>
		</div>

		<ClaySticker displayType="light" shape="circle" size="sm">
			{userAccount?.image ? (
				<ClaySticker.Image
					alt="placeholder"
					height="24"
					src={userAccount?.image}
					width="24"
				/>
			) : (
				<ClayIcon symbol="picture" />
			)}
		</ClaySticker>
	</div>
);

export default AccountEmailInfo;
