/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useMemo} from 'react';
import {useNavigate} from 'react-router-dom';

import i18n from '../../../../../../i18n';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../../../utils/util';
import {STATUSES} from '../../../../../PublisherDashboard/pages/Solutions/constants';

const SolutionsDetailsHeader = ({product}: {product?: Product}) => {
	const navigate = useNavigate();

	const appVersion = useMemo(
		() =>
			getProductVersionFromSpecifications(
				product?.productSpecifications ?? []
			),
		[product?.productSpecifications]
	);

	if (!product) {
		return null;
	}

	return (
		<>
			<ClayButton
				className="align-items-center d-flex"
				displayType="unstyled"
				onClick={() => navigate('..')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				<h5 className="mt-1">{i18n.translate('back-to-solutions')}</h5>
			</ClayButton>

			{product.workflowStatusInfo.code === 1 && (
				<ClayAlert className="my-4" displayType="info">
					{i18n.translate(
						'this-submission-is-currently-under-review-by-liferay-once-the-process-is-complete-the-solution-will-be-published-automatically-to-the-marketplace-meanwhile-any-information-or-data-from-this-solution-submission-cannot-be-updated'
					)}
				</ClayAlert>
			)}

			<div className="align-items-center d-flex justify-content-between mb-4 mt-4">
				<div className="align-items-center d-flex solution-details-page-header-left-container">
					<div>
						<img
							alt="App Logo"
							className="solution-details-page-icon"
							src={showAppImage(
								getThumbnailByProductAttachment(product.images)
							)}
						/>
					</div>

					<div>
						<span className="solution-details-page-header-title">
							{product.name?.en_US}
						</span>

						<div className="align-items-center d-flex solution-details-page-header-subtitle-container">
							{appVersion && (
								<span className="solution-details-page-header-subtitle-text">
									{appVersion}
								</span>
							)}
							<ClayIcon
								className={classNames(
									'solution-details-page-header-subtitle-icon',
									{
										'solution-details-page-header-subtitle-icon-hidden':
											product.workflowStatusInfo.label ===
											'draft',
										'solution-details-page-header-subtitle-icon-pending':
											product.workflowStatusInfo.label ===
											'pending',
										'solution-details-page-header-subtitle-icon-published':
											product.workflowStatusInfo.label ===
											'approved',
									}
								)}
								symbol="circle"
							/>

							<span className="solution-details-page-header-subtitle-text">
								{
									STATUSES[
										product.workflowStatusInfo
											.code as keyof typeof STATUSES
									]
								}
							</span>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default SolutionsDetailsHeader;
