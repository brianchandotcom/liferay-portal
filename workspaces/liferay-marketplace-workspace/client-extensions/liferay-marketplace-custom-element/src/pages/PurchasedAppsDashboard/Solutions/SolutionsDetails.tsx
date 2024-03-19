/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useMemo} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import useSWR from 'swr';

import circleFullIcon from '../../../assets/icons/circle_fill_icon.svg';

// import {useNavigate, useParams} from 'react-router-dom';

// import {useMarketplaceContext} from '../../../context/MarketplaceContext';

import i18n from '../../../i18n';
import {useAppContext} from '../../../manage-app-state/AppManageState';
import {TYPES} from '../../../manage-app-state/actionTypes';
import HeadlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../utils/util';
import {ReviewAndSubmitSolutions} from './ReviewAndSubmitSolutions/ReviewAndSubmitSolutions';

const SolutionsDetails = () => {
	const [, dispatch] = useAppContext();

	// const [loading, setLoading] = useState(false);

	const {appId} = useParams();
	const navigate = useNavigate();

	const productId = Number(appId) + 1;

	const {data: selectedApp, isLoading} = useSWR(
		`/published-app/${productId}`,
		() =>
			HeadlessCommerceAdminCatalogImpl.getProduct(
				productId,
				new URLSearchParams({
					nestedFields: 'attachments,images,productSpecifications',
				})
			)
	);

	const appVersion = useMemo(
		() =>
			getProductVersionFromSpecifications(
				selectedApp?.productSpecifications ?? []
			),
		[selectedApp?.productSpecifications]
	);

	useEffect(() => {
		if (!selectedApp) {
			return;
		}

		dispatch({
			payload: {
				value: {
					appERC: selectedApp.externalReferenceCode,
					appProductId: selectedApp.productId,
				},
			},
			type: TYPES.SUBMIT_APP_PROFILE,
		});
	}, [
		dispatch,
		selectedApp,
		selectedApp?.externalReferenceCode,
		selectedApp?.productId,
	]);

	if (!selectedApp || isLoading) {
		return null;
	}

	const status = selectedApp.workflowStatusInfo.label.replace(
		/(^\w|\s\w)/g,
		(m: string) => m.toUpperCase()
	);

	const thumbnail = getThumbnailByProductAttachment(selectedApp?.images);

	return (
		<div className="solutions-details-page-container">
			<ClayButton
				className="align-items-center d-flex"
				displayType="unstyled"
				onClick={() => navigate('../solutions')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				<h5 className="mt-1">{i18n.translate('back-to-solutions')}</h5>
			</ClayButton>

			<div className="app-details-page-app-info-main-container mt-4">
				<div className="app-details-page-app-info-left-container">
					<div>
						<img
							alt="App Logo"
							className="app-details-page-icon"
							src={showAppImage(thumbnail)}
						/>
					</div>

					<div>
						<span className="app-details-page-app-info-title">
							{selectedApp.name?.en_US}
						</span>

						<div className="app-details-page-app-info-subtitle-container">
							{appVersion && (
								<span className="app-details-page-app-info-subtitle-text">
									{appVersion}
								</span>
							)}

							<img
								alt="status icon"
								className={classNames(
									'app-details-page-app-info-subtitle-icon',
									{
										'app-details-page-app-info-subtitle-icon-hidden':
											selectedApp.workflowStatusInfo
												.label === 'draft',
										'app-details-page-app-info-subtitle-icon-pending':
											selectedApp.workflowStatusInfo
												.label === 'pending',
										'app-details-page-app-info-subtitle-icon-published':
											selectedApp.workflowStatusInfo
												.label === 'approved',
									}
								)}
								src={circleFullIcon}
							/>

							<span className="app-details-page-app-info-subtitle-text">
								{selectedApp.workflowStatusInfo.label_i18n}
							</span>
						</div>
					</div>
				</div>
			</div>

			{status === 'Draft' && (
				<ClayAlert
					className="app-details-page-alert-container"
					displayType="info"
				>
					<span className="app-details-page-alert-text">
						This submission is currently under review by Liferay.
						Once the process is complete, you will be able to
						publish it to the marketplace. Meanwhile, any
						information or data from this app submission cannot be
						updated.
					</span>
				</ClayAlert>
			)}

			<ReviewAndSubmitSolutions
				productERC={selectedApp.externalReferenceCode}
				productId={productId}
			/>
		</div>
	);
};

export default SolutionsDetails;
