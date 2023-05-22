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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayNavigationBar from '@clayui/navigation-bar';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import arrowDown from '../../assets/icons/arrow_down_icon.svg';
import arrowLeft from '../../assets/icons/arrow_left_icon.svg';
import circleFullIcon from '../../assets/icons/circle_fill_icon.svg';
import {DashboardListItems} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {ReviewAndSubmitAppPage} from '../ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';

import './AppDetailsPage.scss';
import {getProductSpecifications} from '../../utils/api';
import {getProductVersionFromSpecifications} from '../../utils/util';

interface AppDetailsPageProps {
	dashboardNavigationItems: DashboardListItems[];
	selectedApp: AppProps;
	setSelectedApp?: (value: AppProps | undefined) => void;
}

export function AppDetailsPage({
	dashboardNavigationItems,
	selectedApp,
	setSelectedApp,
}: AppDetailsPageProps) {
	const [appVersion, setAppVersion] = useState('0');
	const [navigationBarActive, setNavigationBarActive] = useState(
		'App Details'
	);

	const [_, dispatch] = useAppContext();

	useEffect(() => {
		dispatch({
			payload: {
				value: {
					appERC: selectedApp.externalReferenceCode,
					appProductId: selectedApp.productId,
				},
			},
			type: TYPES.SUBMIT_APP_PROFILE,
		});

		const fetchProductSpecifications = async () => {
			const productSpecifications = await getProductSpecifications({
				appProductId: selectedApp.productId,
			});

			const appVersion = getProductVersionFromSpecifications(
				productSpecifications
			);

			setAppVersion(appVersion);
		};

		fetchProductSpecifications();
	}, [dispatch, selectedApp]);

	return (
		<div className="app-details-page-container">
			<button
				className="app-details-page-back-button"
				onClick={() => {
					dashboardNavigationItems.forEach(({itemName, items}) => {
						if (itemName === 'apps') {
							items?.forEach((item) => {
								if (item.name === selectedApp.name) {
									item.selected = false;
								}
							});
						}
					});

					if (setSelectedApp) {
						setSelectedApp(undefined);
					}
				}}
			>
				<div>
					<img
						alt="arrow left"
						className="app-details-page-back-button-icon"
						src={arrowLeft}
					/>
					Back to Apps
				</div>
			</button>

			{selectedApp.status === 'Draft' && (
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
							src={selectedApp.thumbnail}
						/>
					</div>

					<div>
						<span className="app-details-page-app-info-title">
							{selectedApp.name}
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
					triggerLabel={navigationBarActive}
				>
					<ClayNavigationBar.Item
						active={navigationBarActive === 'App Details'}
					>
						<ClayButton
							onClick={() =>
								setNavigationBarActive('App Details')
							}
						>
							<span>App Details</span>
						</ClayButton>
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
}
