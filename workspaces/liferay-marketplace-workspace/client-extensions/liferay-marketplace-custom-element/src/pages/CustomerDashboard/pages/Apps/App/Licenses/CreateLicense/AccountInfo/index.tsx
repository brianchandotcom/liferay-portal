/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';

import {Liferay} from '../../../../../../../../liferay/liferay';

import './index.scss';

type AccountInfoProps = {
	image?: string;
	name?: string;
};

const AccountEmailInfo: React.FC<AccountInfoProps> = ({image, name}) => (
	<div className="align-items-center d-flex">
		<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
			<strong>{name}</strong>

			<div className="account-banner-email-text">
				{Liferay.ThemeDisplay.getUserEmailAddress()}
			</div>
		</div>

		<ClaySticker displayType="light" shape="circle" size="sm">
			{image ? (
				<ClaySticker.Image
					alt="placeholder"
					draggable={false}
					height={24}
					src={image}
					width={24}
				/>
			) : (
				<ClayIcon symbol="picture" />
			)}
		</ClaySticker>
	</div>
);

export default AccountEmailInfo;
