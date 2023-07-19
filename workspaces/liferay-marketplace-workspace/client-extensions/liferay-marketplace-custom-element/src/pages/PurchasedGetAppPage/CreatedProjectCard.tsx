/* eslint-disable no-return-assign */
/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';

import createdProjectIcon from '../../assets/images/created_project.svg';

import './PurchasedGetAppPage.scss';

import ClayIcon from '@clayui/icon';

import {getSiteURL} from '../../components/InviteMemberModal/services';
import {Liferay} from '../../liferay/liferay';

type Steps = {
	page: 'accountCreation' | 'accountSelection' | 'projectCreated';
};

type CreatedProjectCardProps = {
	product?: Product;
	setStep: React.Dispatch<Steps>;
};

const CreatedProjectCard: React.FC<CreatedProjectCardProps> = ({
	product,
	setStep,
}) => {
	return (
		<div className="align-items-center d-flex flex-column h-100 justify-content-center purchased-get-app-page-container w-100">
			<div className="border p-8 purchased-get-app-page-body rounded">
				<div className="align-items-center d-flex flex-column justify-content-center">
					<div className="mb-6">
						<img
							className="gate-card-image"
							src={createdProjectIcon}
						/>
					</div>

					<div className="col-10 mb-2 mt-5 text-center">
						<h1>
							Your &nbsp;
							<span className="created-project-cart-title">
								{product?.name?.en_US}
							</span>
							&nbsp; project is being created now.
						</h1>
					</div>

					<div className="col-8 text-center">
						<div>
							<span>
								Expect an email in 10 minutes or less to verify
								your project is ready.
							</span>
						</div>
					</div>

					<div className="mt-6 purchased-get-app-page-button-container">
						<ClayButton
							className="py-3"
							onClick={() =>
								(window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`)
							}
						>
							Return to Dashboard
							<span className="ml-3">
								<ClayIcon symbol="order-arrow-right" />
							</span>
						</ClayButton>
					</div>
				</div>
			</div>
		</div>
	);
};

export default CreatedProjectCard;
