/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayNavigationBar from '@clayui/navigation-bar';
import classNames from 'classnames';
import {useEffect, useMemo, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import useSWR from 'swr';

import arrowDown from '../../../assets/icons/arrow_down_icon.svg';
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
	const navigate = useNavigate();
	const {syncKoroneikiProduct} = useMarketplaceSpringBootOAuth2();

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

			<div className="app-details-page-app-info-main-container">
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
							<span className="app-details-page-app-info-subtitle-text">
								{appVersion}
							</span>

							<img
								alt="status icon"
								className={classNames(
									'app-details-page-app-info-subtitle-icon',
									{
										'app-details-page-app-info-subtitle-icon-hidden':
											selectedApp.status === 'Draft',
										'app-details-page-app-info-subtitle-icon-pending':
											selectedApp.status === 'Pending',
										'app-details-page-app-info-subtitle-icon-published':
											selectedApp.status === 'Approved',
									}
								)}
								src={circleFullIcon}
							/>

							<span className="app-details-page-app-info-subtitle-text">
								{selectedApp.status}
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

								syncKoroneikiProduct(productId)
									.then(() =>
										Liferay.Util.openToast({
											message:
												'Koroneiki Sync Successfully',
											title: 'Success',
										})
									)
									.catch(() =>
										Liferay.Util.openToast({
											message: 'Koroneiki Sync Failed',
											title: 'Error',
											type: 'danger',
										})
									)
									.finally(() => setLoading(false));
							}}
						>
							{loading ? 'Synchronizing...' : 'Sync to KR'}
						</ClayButton>
					)}

					<button className="app-details-page-app-info-button-preview-app-page">
						Preview App Page
					</button>

					<button className="app-details-page-app-info-button-manage">
						Manage
						<img
							alt="Arrow Down"
							className="app-details-page-app-info-button-manage-icon"
							src={arrowDown}
						/>
					</button>
				</div>
			</div>

			<div>
				<ClayNavigationBar
					className="app-details-page-navigation-bar"
					triggerLabel="App Detatils"
				>
					<ClayNavigationBar.Item active>
						<ClayButton>App Details</ClayButton>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>

				<ReviewAndSubmitAppPage
					onClickBack={() => {}}
					onClickContinue={() => {}}
					productERC={selectedApp.externalReferenceCode}
					productId={selectedApp.productId}
					readonly
				/>
			</div>
		</div>
	);
};

export default App;
