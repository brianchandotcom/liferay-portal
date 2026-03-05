/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Loading from '../../../../components/Loading';
import {OrderStatus} from '../../../../enums/Order';
import {usePlacedOrder} from '../../../../hooks/data/usePlacedOrder';

import i18n from '../../../../i18n';
import './LDPNextSteps.scss';

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
	const urlParams = new URLSearchParams(window.location.search);
	const orderId = urlParams.get('orderId');

	const {data: order, error} = usePlacedOrder(orderId!!, {
		refreshInterval: 10000,
	});

	if (order?.orderStatusInfo.label === OrderStatus.COMPLETED) {
		sendRedirect(`/liferay-service/launch?orderId=${orderId}`);
	}

	if (error) {
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
