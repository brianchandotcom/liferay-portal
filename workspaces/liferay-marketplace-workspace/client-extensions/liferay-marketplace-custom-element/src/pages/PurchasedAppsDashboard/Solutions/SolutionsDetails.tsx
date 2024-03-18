/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// import {useNavigate, useParams} from 'react-router-dom';

import {DetailedCard} from '../../../components/DetailedCard/DetailedCard';
import i18n from '../../../i18n';

const SolutionsDetails = () => {
	return (
		<div className="app-details-page-container mt-6">
			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle={i18n.translate('details')}
					clayIcon="order-form-tag"
				>
					<div className="mb-2 mt-7 row">
						<h5 className="col-6">{i18n.translate('order-id')}</h5>
						<p className="col">O</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">
							{i18n.translate('order-date')}
						</h5>
						<p className="col">Date</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">
							{i18n.translate('customer-account')}
						</h5>
						<p className="col">Acc</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">
							{i18n.translate('customer-roject')}
						</h5>
						<p className="col">Jo</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">
							{i18n.translate('purchased-by')}
						</h5>
						<p className="col">Au</p>
					</div>
					<div className="row">
						<h5 className="col-6">Purchase Order Number</h5>
						<p className="col">osds</p>
					</div>
				</DetailedCard>
			</div>
		</div>
	);
};

export default SolutionsDetails;
