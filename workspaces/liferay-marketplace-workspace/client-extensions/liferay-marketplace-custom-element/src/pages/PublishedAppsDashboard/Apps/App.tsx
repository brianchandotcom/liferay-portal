/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useMemo, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import useSWR from 'swr';

import circleFullIcon from '../../../assets/icons/circle_fill_icon.svg';
import {useAppContext} from '../../../manage-app-state/AppManageState';
import {TYPES} from '../../../manage-app-state/actionTypes';
import {ReviewAndSubmitAppPage} from '../../ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';

import './App.scss';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useMarketplaceSpringBootOAuth2 from '../../../hooks/useMarketplaceSpringBootOAuth2';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../utils/util';

const App = () => {
	const [, dispatch] = useAppContext();
	const [loading, setLoading] = useState(false);
	const {appId} = useParams();
	const {myUserAccount} = useMarketplaceContext();
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();
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
		<div className="app-details-page-container">
			<ClayButton
				className="align-items-center d-flex"
				displayType="unstyled"
				onClick={() => navigate('..')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				<h5 className="mt-1">{i18n.translate('back-to-apps')}</h5>
			</ClayButton>

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

			<div className="app-details-page-app-info-main-container mt-4">
				<div className="app-details-page-app-info-left-container">
					<div>
						<img
							alt="App Logo"
							className="app-details-page-app-info-logo"
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

				<div className="app-details-page-app-info-buttons-container">
					{myUserAccount.roleBriefs.some(
						({name}) => name === 'Administrator'
					) && (
						<ClayButton
							className="font-weight-bold mr-5"
							disabled={loading}
							displayType="unstyled"
							onClick={() => {
								setLoading(true);

								marketplaceSpringBootOAuth2
									.syncKoroneikiProduct(productId)
									.then(() =>
										Liferay.Util.openToast({
											message:
												'Koroneiki Sync Successfully',
											title: 'Success',
										})
									)
									.catch((error) => {
										console.error(error);

										Liferay.Util.openToast({
											message: 'Koroneiki Sync Failed',
											title: 'Error',
											type: 'danger',
										});
									})
									.finally(() => setLoading(false));
							}}
						>
							{loading ? 'Synchronizing...' : 'Sync to KR'}
						</ClayButton>
					)}
				</div>
			</div>

			<ReviewAndSubmitAppPage
				onClickBack={() => {}}
				onClickContinue={() => {}}
				productERC={selectedApp.externalReferenceCode}
				productId={selectedApp.productId}
				readonly
			/>
		</div>
	);
};

export default App;
