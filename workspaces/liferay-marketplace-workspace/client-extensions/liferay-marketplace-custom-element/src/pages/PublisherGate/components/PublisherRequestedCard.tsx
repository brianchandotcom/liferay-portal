/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';

import createdProjectIcon from '../../../assets/images/add_user.svg';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {getSiteURL} from '../../../utils/site';

const PubliserhRequestedCard = () => (
	<div className="d-flex flex-column pb-8 publisher-gate-page-body">
		<div className="border mt-8 p-8 rounded">
			<div className="d-flex justify-content-center mb-6">
				<img
					alt="project icon"
					className="gate-card-image"
					src={createdProjectIcon}
				/>
			</div>

			<div className="d-flex justify-content-center mb-2 mt-5 text-center">
				<h1 className="col-10">
					{i18n.translate('thank-you-for-your')}&nbsp;
					<span className="created-project-cart-title">
						{i18n.translate('publisher-account-request')}
					</span>
				</h1>
			</div>

			<div className="d-flex justify-content-center text-center">
				<div className="col-10">
					<span>
						{i18n.translate(
							'an-administrator-of-the-marketplace-will-review-your-request-shortly-if-you-have-questions-regarding-your-submission-please-email'
						)}
						&nbsp;
						<span>
							<ClayLink
								className="text-weight-bold"
								href="mailto: marketplace-admin@liferay.com"
							>
								marketplace-admin@liferay.com
							</ClayLink>
						</span>
					</span>
				</div>
			</div>

			<div className="d-flex justify-content-center mt-6 purchased-solutions-button-container">
				<ClayButton
					className="py-3"
					onClick={() => {
						window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/home`;
					}}
				>
					{i18n.translate('return-to-marketplace')}
					<span className="ml-3">
						<ClayIcon symbol="order-arrow-right" />
					</span>
				</ClayButton>
			</div>
		</div>
	</div>
);

export default PubliserhRequestedCard;
