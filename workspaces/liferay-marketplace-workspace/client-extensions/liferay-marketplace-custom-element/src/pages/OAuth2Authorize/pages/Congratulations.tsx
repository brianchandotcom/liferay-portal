/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';

import CongratulationsIcon from '../../../assets/icons/congratulations_icon.svg';
import SearchBuilder from '../../../core/SearchBuilder';
import {Liferay} from '../../../liferay/liferay';
import fetcher from '../../../services/fetcher';
import dxpOAuth2Client from '../../../services/oauth/DXP';
import {safeJSONParse} from '../../../utils/util';
import {CongratulationsStepType} from '../types';

const urlSearchParams = new URLSearchParams(window.location.search);

const Congratulations = ({
	myUserAccount,
	selectedAccount,
}: CongratulationsStepType) => {
	useEffect(() => {
		const state = safeJSONParse(urlSearchParams.get('state'), {origin: ''});

		fetcher.post('o/c/oauth2dxpauthorizations/', {
			connectionSource: state.origin,
			r_accountToOAuth2DxpAuthorization_accountEntryId:
				selectedAccount?.id,
		});

		window?.opener?.postMessage(
			{
				code: urlSearchParams.get('code'),
				myUserAccount,
				selectedAccount,
				serviceURL: dxpOAuth2Client.oAuth2Client.homePageURL,
				settings: {
					accountId: selectedAccount.id,
					channelId: Liferay.CommerceContext.commerceChannelId,
					references: {
						paymentMethodFilter: SearchBuilder.lambda(
							'categoryNames',
							'Payment Integration'
						),
					},
					siteId: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			},
			state.origin
		);
	}, [myUserAccount, selectedAccount]);

	return (
		<div className="align-items-center border d-flex flex-column justify-content-center p-5 rounded">
			<div className="align-items-center d-flex justify-content-center">
				<img
					alt="Congratulations Icon"
					className="congratulations-image"
					src={CongratulationsIcon}
				/>
			</div>

			<h1 className="pt-7">Congratulations</h1>
			<p className="align-items-center d-flex mt-4 px-3 secondary-text">
				You are one step away from finalizing your connection with the
				Marketplace, this window will close automatically.
			</p>
		</div>
	);
};

export default Congratulations;
