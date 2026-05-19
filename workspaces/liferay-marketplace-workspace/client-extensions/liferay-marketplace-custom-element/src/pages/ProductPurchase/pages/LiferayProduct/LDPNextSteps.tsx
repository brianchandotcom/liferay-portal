/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Loading from '../../../../components/Loading';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderStatus} from '../../../../enums/Order';
import {usePlacedOrder} from '../../../../hooks/data/usePlacedOrder';
import i18n from '../../../../i18n';

import './LDPNextSteps.scss';
import {safeJSONParse} from '../../../../utils/util';

const Content = ({
	description,
	loading,
	title,
}: {
	description: string;
	loading?: boolean;
	title: string;
}) => {
	return (
		<div className="ldp-background">
			<div className="d-flex justify-content-center w-100">
				<div className="align-items-center col-3 d-flex flex-column justify-content-center mt-9">
					<div className="ldp-next-steps loading-overlay">
						<div className="loading-container">
							{loading && <Loading className="mb-6" />}
							<span className="mt-4">
								<h1>{title}</h1>
								<div className="my-5 text-center">
									<span>{description}</span>
								</div>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

function sendRedirect(link: string) {
	window.location.href = link;
}

const LDPNextSteps: React.FC<{
	description: string;
	title: string;
}> = ({description, title}) => {
	const {properties} = useMarketplaceContext();

	const searchParams = new URLSearchParams(window.location.search);

	const orderId = searchParams.get('orderId') as string;

	const {data: order, error} = usePlacedOrder(orderId, {
		refreshInterval: 30000,
	});

	const orderMetadata = safeJSONParse(
		order?.customFields?.ORDER_METADATA || '{}',
		{
			analyticsProject: {groupId: 0},
		}
	);

	const groupId = orderMetadata?.analyticsProject?.groupId;

	if (order?.orderStatusInfo.label === OrderStatus.COMPLETED) {
		sendRedirect(`${properties.analyticsCloudURL}/workspace/${groupId}`);
	}

	if (error || order?.orderStatusInfo.label === OrderStatus.CANCELLED) {
		return (
			<Content
				description={i18n.translate(
					'we-couldnt-set-up-your-environment-please-contact-support'
				)}
				title={i18n.translate('something-went-wrong')}
			/>
		);
	}

	return <Content description={description} loading title={title} />;
};

export default LDPNextSteps;
