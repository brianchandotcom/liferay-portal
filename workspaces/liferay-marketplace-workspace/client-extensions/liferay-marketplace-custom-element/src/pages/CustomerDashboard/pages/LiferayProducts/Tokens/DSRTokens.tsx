/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {OrderCustomFields} from '../../../../../enums/Order';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import analyticsOAuth2 from '../../../../../services/oauth/Analytics';
import {copyToClipboard} from '../../../../../utils/browser';
import {safeJSONParse} from '../../../../../utils/util';

import './DSRTokens.scss';

type OutletContext = NonNullable<
	ReturnType<typeof useGetProductByOrderId>['data']
>;

const DSRTokens = () => {
	const {placedOrder} = useOutletContext<OutletContext>();

	const orderMetadata = safeJSONParse(
		placedOrder.customFields[OrderCustomFields.ORDER_METADATA],
		{analyticsProject: {groupId: ''}}
	);

	const groupId = orderMetadata?.analyticsProject?.groupId
		? String(orderMetadata.analyticsProject.groupId)
		: '';

	const {data: fetchedToken, isLoading} = useSWR(
		groupId ? `/analytics/project/${groupId}/data-source/token` : null,
		() => analyticsOAuth2.getProjectDataSourceToken(groupId)
	);

	const token = fetchedToken || i18n.translate('token-unavailable');

	return (
		<div className="dsr-tokens mt-5">
			<div className="dsr-token-card">
				<div className="dsr-token-card-header">
					<div className="dsr-token-card-icon">
						<ClayIcon symbol="diagram" />
					</div>

					<h3 className="dsr-token-card-title m-0">
						{i18n.translate('connect-your-liferay-dsr')}
					</h3>
				</div>

				<span className="dsr-token-label">
					{i18n.translate(
						'copy-this-token-to-your-liferay-dxp-instance'
					)}

					<span className="dsr-token-required">*</span>
				</span>

				{isLoading ? (
					<ClayLoadingIndicator />
				) : (
					<div className="dsr-token-field">
						<span className="dsr-token-input">{token}</span>

						<button
							aria-label={i18n.translate('copy')}
							className="dsr-token-copy"
							onClick={() => copyToClipboard(token)}
							type="button"
						>
							<ClayIcon symbol="copy" />
						</button>
					</div>
				)}
			</div>
		</div>
	);
};

export default DSRTokens;
