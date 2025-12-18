/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';

import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderTypes} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {safeJSONParse} from '../../../../utils/util';
import {BaseOutlet} from '../Apps/App/AppOutlet';

const SolutionOutlet = () => {
	const {properties} = useMarketplaceContext();

	return (
		<BaseOutlet
			actionButtons={(payload) => {
				const isAddOn =
					payload?.placedOrder?.orderTypeExternalReferenceCode ===
					OrderTypes.ADDONS;

				if (!isAddOn || !payload.placedOrder) {
					return;
				}

				const orderMetadata = safeJSONParse(
					payload.marketplaceDeliveryOrder.customFields
						?.ORDER_METADATA,
					{analyticsProject: {groupId: 0}}
				);

				const groupId = orderMetadata?.analyticsProject?.groupId;

				if (!groupId) {
					return;
				}

				return (
					<Button
						className="mt-6"
						displayType="primary"
						onClick={() => {
							window.open(
								`${properties.analyticsCloudURL}/workspace/${groupId}`
							);
						}}
						outline
						size="sm"
					>
						{i18n.translate('go-to-analytics-cloud')}
						<ClayIcon className="ml-2" symbol="shortcut" />
					</Button>
				);
			}}
			backTitle={i18n.translate('back-to-my-solutions')}
			backURL="../solutions"
			routes={[{name: i18n.translate('details'), path: ''}]}
			showActions={false}
		/>
	);
};

export default SolutionOutlet;
