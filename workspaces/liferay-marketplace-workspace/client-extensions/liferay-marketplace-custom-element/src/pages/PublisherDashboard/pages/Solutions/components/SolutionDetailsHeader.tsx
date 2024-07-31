/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useNavigate} from 'react-router-dom';

import {
	PRODUCT_WORKFLOW_STATUS_CODE,
	PRODUCT_WORKFLOW_STATUS_LABEL,
} from '../../../../../enums/Product';
import i18n from '../../../../../i18n';
import {getProductVersionFromSpecifications} from '../../../../../utils/util';

const SolutionsDetailsHeader = ({product}: {product?: Product}) => {
	const navigate = useNavigate();

	if (!product) {
		return null;
	}

	const appVersion = getProductVersionFromSpecifications(
		product.productSpecifications ?? []
	);

	return (
		<>
			<div className="d-flex justify-content-between">
				<ClayButton
					className="align-items-center d-flex"
					displayType="unstyled"
					onClick={() => navigate('..')}
				>
					<ClayIcon className="mr-2" symbol="order-arrow-left" />
					<span className="h5 mt-1">
						{i18n.translate('back-to-solutions')}
					</span>
				</ClayButton>

				<ClayButton
					disabled={
						product.productStatus ===
						PRODUCT_WORKFLOW_STATUS_CODE.PENDING
					}
					displayType="secondary"
					onClick={() =>
						navigate(`../${product.productId}/publisher/profile`)
					}
					size="sm"
				>
					{i18n.translate('edit')}
				</ClayButton>
			</div>

			{product.workflowStatusInfo.code ===
				PRODUCT_WORKFLOW_STATUS_CODE.PENDING && (
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
							src={product.thumbnail}
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
									PRODUCT_WORKFLOW_STATUS_LABEL[
										product.workflowStatusInfo
											.code as keyof typeof PRODUCT_WORKFLOW_STATUS_LABEL
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
