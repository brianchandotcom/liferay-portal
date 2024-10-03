/* eslint-disable @liferay/portal/no-react-dom-create-portal */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import {useEffect, useState} from 'react';

import {useAppPropertiesContext} from '../../contexts/AppPropertiesContext';
import {Liferay} from '../../services/liferay';
import {getOrRequestToken} from '../../services/liferay/security/auth/getOrRequestToken';
import OAuthTokenModal from './OAuthTokenModal';

const OAuthToken = () => {
	const {oAuthTokenAPI} = useAppPropertiesContext();
	const [status, setStatus] = useState<'active' | 'idle' | 'inactive'>(
		'idle'
	);

	const oAuthTokenURL = new URL(oAuthTokenAPI);
	const oAuthTokenDomain = `${oAuthTokenURL.protocol}/${oAuthTokenURL.host}`;

	const {observer, onOpenChange, open} = useModal();

	useEffect(() => {
		getOrRequestToken(oAuthTokenAPI);
	}, [oAuthTokenAPI]);

	useEffect(() => {
		const handler = ({
			details,
		}: {
			details: [{status: number; success: boolean}];
		}) => {
			const successfull = details[0].success;

			if (!successfull) {
				onOpenChange(true);
			}

			setStatus(successfull ? 'active' : 'inactive');
		};

		Liferay.on('oauth-token-status-changed', handler);

		return () => Liferay.detach('oauth-token-status-changed', handler);
	}, [onOpenChange]);

	const onCancel = () => {
		window.location.href = 'https://liferay.com';
	};

	const onClickSignIn = () => {
		if (window.location.protocol === 'http:') {
			window.location.href = oAuthTokenDomain;

			return;
		}

		window.location.href = '/c/portal/logout';
	};

	if (open && status === 'inactive') {
		return (
			<OAuthTokenModal
				observer={observer}
				onClick={onClickSignIn}
				onClose={onCancel}
			/>
		);
	}

	return null;
};

export default OAuthToken;
