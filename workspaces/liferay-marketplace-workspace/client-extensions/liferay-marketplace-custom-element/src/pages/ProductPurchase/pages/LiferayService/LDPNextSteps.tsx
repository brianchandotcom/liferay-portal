/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Loading from '../../../../components/Loading';

import {usePlacedOrder} from '../../../../hooks/data/usePlacedOrder';
import {OrderStatus} from '../../../../enums/Order';

import './LDPNextSteps.scss';

const LDPNextSteps: React.FC<{
	title: string;
	description: string;
}> = ({title, description}) => {
	const urlParams = new URLSearchParams(window.location.search);
	const orderId = urlParams.get('orderId');

	const {data: order, error} = usePlacedOrder(orderId!!, {
		refreshInterval: 10000,
	});

	if (order?.orderStatusInfo.label === OrderStatus.COMPLETED) {
		window.location.href = `/liferay-service/launch?orderId=${orderId}`;
	}

	if (error) {
		return (
			<div className="ldp-background">
				<div className="d-flex justify-content-center w-100">
					<div className="align-items-center d-flex flex-column justify-content-center col-3 mt-9">
						<div className="loading-overlay ldp-next-steps">
							<div className="loading-container">
								<span className="mt-4">
									<h1>Something went wrong</h1>
									<div className="my-5 text-center">
										<span>
											We couldn't set up your environment.
											Please contact support.
										</span>
									</div>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}

	return (
		<div className="ldp-background">
			<div className="d-flex justify-content-center w-100">
				<div className="align-items-center d-flex flex-column justify-content-center col-3 mt-9">
					<div className="loading-overlay ldp-next-steps">
						<div className="loading-container">
							<Loading className="mb-6" />
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

export default LDPNextSteps;
