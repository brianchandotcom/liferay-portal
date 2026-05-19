/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import QATable from '../../../../../components/QATable';
import {OrderCustomFields} from '../../../../../enums/Order';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import analyticsOAuth2 from '../../../../../services/oauth/Analytics';
import {copyToClipboard} from '../../../../../utils/browser';
import {safeJSONParse} from '../../../../../utils/util';

import './DSRWorkspace.scss';

type OutletContext = NonNullable<
	ReturnType<typeof useGetProductByOrderId>['data']
>;

const DSRWorkspace = () => {
	const {placedOrder} = useOutletContext<OutletContext>();

	const orderMetadata = safeJSONParse<any>(
		placedOrder.customFields[OrderCustomFields.ORDER_METADATA],
		{analyticsProject: {groupId: ''}}
	);
	const groupId = orderMetadata?.analyticsProject?.groupId
		? String(orderMetadata.analyticsProject.groupId)
		: '';

	const {data: token = '', isLoading} = useSWR(
		groupId ? `/analytics/project/${groupId}/data-source/token` : null,
		() => analyticsOAuth2.getProjectDataSourceToken(groupId)
	);

	return (
		<div className="d-flex dsr-workspace gap-4 mt-5">
			<div className="dsr-workspace-card">
				<div className="dsr-workspace-card-header">
					<h3 className="dsr-workspace-card-title m-0">
						{i18n.translate('connect-your-liferay-dsr')}
					</h3>

					<div className="dsr-workspace-card-icon">
						<ClayIcon symbol="diagram" />
					</div>
				</div>

				<span className="dsr-workspace-label">
					{i18n.translate(
						'copy-this-token-to-your-liferay-dxp-instance'
					)}

					<span className="dsr-workspace-required">*</span>
				</span>

				{isLoading ? (
					<ClayLoadingIndicator />
				) : (
					<div className="dsr-workspace-field">
						<ClayInput
							className="dsr-workspace-input"
							readOnly
							value={token}
						/>

						<button
							aria-label={i18n.translate('copy')}
							className="dsr-workspace-copy"
							onClick={() => {
								copyToClipboard(token);

								Liferay.Util.openToast({
									message: i18n.sub(
										'copied-x-to-the-clipboard',
										'token'
									),
								});
							}}
							type="button"
						>
							<ClayIcon symbol="copy" />
						</button>
					</div>
				)}
			</div>

			<DetailedCard
				cardIconAltText="Summary Icon"
				cardTitle={i18n.translate('workspace-info')}
				className="dsr-workspace-info-card"
				clayIcon="document"
			>
				<QATable
					items={[
						{
							title: i18n.translate('workspace-name'),
							value: orderMetadata?.analyticsProject
								?.corpProjectName,
						},
						{
							title: i18n.translate('workspace-owner-email'),
							value: orderMetadata?.analyticsProject
								?.ownerEmailAddress,
						},
						{
							title: i18n.translate('data-center-location'),
							value: orderMetadata?.analyticsProject
								?.serverLocation,
						},
					]}
				/>
			</DetailedCard>
		</div>
	);
};

export default DSRWorkspace;
