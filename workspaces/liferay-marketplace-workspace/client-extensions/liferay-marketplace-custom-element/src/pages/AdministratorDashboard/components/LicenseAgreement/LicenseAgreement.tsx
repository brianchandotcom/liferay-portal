/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

import i18n from '../../../../i18n';
import {getSiteName} from '../../../../utils/site';

import './LicenseAgreement.scss';

const LicenseAgreement = () => (
	<div className="license-agreement-container">
		<div className="border-details mb-4">
			<div className="align-items-baseline d-flex justify-content-between p-5">
				<div className="align-items-baseline d-flex justify-content-star">
					<div className="align-items-center d-flex icon-background justify-content-center mr-3">
						<ClayIcon symbol="document-text" />
					</div>

					<h3>Liferay Publisher License Agreement</h3>
				</div>

				<ClayButton
					className="border border-dark rounded-lg text-dark"
					displayType="secondary"
					onClick={() =>
						window.open(
							`/documents/d/${getSiteName()}/developer_agreement_marketplace-pdf`
						)
					}
				>
					{i18n.translate('download')}
					<ClayIcon className="ml-2" symbol="download" />
				</ClayButton>
			</div>

			<div className="p-5 text-agreement">
				<strong className="text-agreement-text-primary">
					LIFERAY MARKETPLACE DEVELOPER AGREEMENT
				</strong>

				<div className="mt-4 text-agreement-text-secondary">
					PLEASE READ THIS AGREEMENT CAREFULLY BEFORE USING THE
					MARKETPLACE TO MARKET OR DISTRIBUTE YOUR DEVELOPER PRODUCTS,
					DOWNLOADING AND/OR USING THE LIFERAY MARKETPLACE. IF YOU ARE
					ENTERING INTO THIS AGREEMENT ON BEHALF OF A COMPANY OR OTHER
					LEGAL ENTITY, YOU REPRESENT THAT YOU HAVE THE AUTHORITY TO
					BIND SUCH ENTITY TO THIS AGREEMENT, IN WHICH CASE THE TERMS
					&quot;YOU&quot; OR &quot;YOUR&quot; SHALL REFER TO SUCH
					ENTITY. IF YOU DO NOT HAVE SUCH AUTHORITY, OR IF YOU DO NOT
					UNCONDITIONALLY AGREE TO ALL OF THE TERMS OF THIS AGREEMENT,
					YOU WILL NOT HAVE ANY RIGHT TO USE THE MARKETPLACE AND
					LIFERAY SOFTWARE AND YOU MUST IMMEDIATELY DISCONTINUE
					PARTICIPATION IN THE MARKETPLACE PROGRAM AND USE OF THE
					LIFERAY SOFTWARE.
				</div>
			</div>
		</div>

		<small>
			By clicking on the button &quot;continue&quot; below, I confirm that
			I have read and agree to be bound by the
			<strong>Liferay Publisher License Agreement. </strong>I also confirm
			that I am of the legal age of majority in the jurisdiction where I
			reside (at least 18 years of age in many countries).
		</small>
	</div>
);

export default LicenseAgreement;
