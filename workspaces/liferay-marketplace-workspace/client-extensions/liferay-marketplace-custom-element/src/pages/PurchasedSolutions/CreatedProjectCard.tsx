/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

import createdProjectIcon from '../../assets/images/created_project.svg';

import './PurchasedSolutions.scss';

import ClayIcon from '@clayui/icon';

type CreatedProjectCardProps = {
	product?: Product;
};

const CreatedProjectCard: React.FC<CreatedProjectCardProps> = ({product}) => (
	<div className="align-items-center d-flex flex-column h-100 justify-content-center purchased-solutions-container w-100">
		<div className="align-items-center d-flex flex-column justify-content-center">
			<div className="mb-6">
				<img
					alt=""
					className="gate-card-image"
					src={createdProjectIcon}
				/>
			</div>

			<div className="col-10 mb-2 mt-5 text-center">
				<h1>
					Your&nbsp;
					<span className="created-project-cart-title">
						{product?.name?.en_US}
					</span>
					&nbsp;project is being created now.
				</h1>
			</div>

			<div className="col-10 text-center">
				<div>
					<span>
						Expect two emails in 10 minutes or less to verify your
						project and extension environments are ready.
					</span>
				</div>
			</div>

			<div className="mt-6 purchased-solutions-button-container">
				<ClayButton
					className="py-3"
					onClick={() => {
						window.location.href =
							'https://www.liferay.com/pt/home';
					}}
				>
					Return to Liferay.com
					<span className="ml-3">
						<ClayIcon symbol="order-arrow-right" />
					</span>
				</ClayButton>
			</div>
		</div>
	</div>
);

export default CreatedProjectCard;
