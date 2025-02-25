/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {Navigate} from 'react-router-dom';

import CongratulationsIcon from '../../../assets/icons/congratulations_icon.svg';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import fetcher from '../../../services/fetcher';
import dxpOAuth2Client from '../../../services/oauth/DXP';
import {safeJSONParse} from '../../../utils/util';
import {useOAuth2OutletContext} from '../OAuth2AuthorizeOutlet';

const urlSearchParams = new URLSearchParams(window.location.search);

const POST_MESSAGE_TIMEOUT = 3000;

const Congratulations = () => {
	const {environment, myUserAccount, selectedAccount} =
		useOAuth2OutletContext();

	useEffect(() => {
		const {origin} = safeJSONParse(urlSearchParams.get('state'), {
			origin: null,
		});

		if (!origin) {
			return console.warn('Origin state is not present.');
		}

		fetcher.post('o/c/oauth2dxpauthorizations', {
			connectionSource: origin,
			r_accountToOAuth2DxpAuthorization_accountEntryId:
				selectedAccount?.id,
		});

		const payload = {
			code: urlSearchParams.get('code'),
			serviceURL: dxpOAuth2Client.oAuth2Client.homePageURL,
			settings: {
				account: {
					id: selectedAccount?.id,
					image: selectedAccount?.logoURL,
					name: selectedAccount?.name,
				},
				channelId: Liferay.CommerceContext.commerceChannelId,
				cloudProject: environment?.projectId,
				references: {
					fragmentsFilter: SearchBuilder.lambda(
						'categoryNames',
						'Fragments'
					),
					paymentMethodFilter: SearchBuilder.lambda(
						'categoryNames',
						'Payment Integration'
					),
				},
				siteId: Liferay.ThemeDisplay.getScopeGroupId(),
				userAccount: {
					id: myUserAccount.id,
					image: myUserAccount.image,
					name: myUserAccount.name,
				},
			},
		};

		// eslint-disable-next-line no-console
		console.info(
			`Dispatching post message to ${origin} with the following payload`,
			payload
		);

		setTimeout(() => {
			window?.opener?.postMessage(payload, origin);
		}, POST_MESSAGE_TIMEOUT);
	}, [environment?.projectId, myUserAccount, selectedAccount]);

	if (!selectedAccount) {
		return <Navigate to="/" />;
	}

	return (
		<div className="align-items-center border d-flex flex-column justify-content-center p-5 rounded">
			<div className="align-items-center d-flex justify-content-center">
				<img
					alt="Congratulations Icon"
					className="congratulations-image"
					src={CongratulationsIcon}
				/>
			</div>

			<h1 className="pt-7">{i18n.translate('congratulations')}</h1>

			<p className="align-items-center d-flex mt-4 px-3 secondary-text">
				{i18n.translate(
					'you-are-one-step-away-from-finalizing-your-connection-with-the-marketplace-this-window-will-close-automatically'
				)}
			</p>
		</div>
	);
};

export default Congratulations;
