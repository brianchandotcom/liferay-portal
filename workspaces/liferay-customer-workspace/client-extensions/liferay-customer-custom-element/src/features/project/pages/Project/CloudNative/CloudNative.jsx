/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ButtonWithIcon} from '@clayui/core';
import ClayTable from '@clayui/table';
import {useCallback, useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import DownloadAlert from '~/features/project/containers/ActivationKeysTable/components/DownloadAlert';
import {ALERT_CLOUD_LICENSE_KEY_DOWNLOAD_TEXT} from '~/features/project/containers/ActivationKeysTable/utils/constants/alertCloudLicenseKeyDownloadText';
import {getCloudLicenseKeyDownload} from '~/features/project/containers/ActivationKeysTable/utils/getCloudLicenseKeyDownload';
import PopoverIcon from '~/features/project/containers/ActivationStatus/DXPCloud/components/PopoverIcon';
import {useAppContext} from '~/features/project/context';
import DeveloperKeysLayouts from '~/features/project/layouts/DeveloperKeysLayout';
import {
	ALERT_DOWNLOAD_TYPE,
	LIST_TYPES,
} from '~/features/project/utils/constants';
import {useGetCloudNativeEnvironments} from '~/services/liferay/graphql/cloud-native-environments';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';
import i18n from '~/utils/I18n';

const CloudNative = () => {
	const [{project, subscriptionGroups, userAccount}, dispatch] =
		useAppContext();

	const [oAuthToken, setOAuthToken] = useState();
	const {setHasSideMenu} = useOutletContext();
	const {featureFlags, provisioningServerAPI} = useAppPropertiesContext();
	const [downloadStatus, setDownloadStatus] = useState('');

	const handleAlertStatus = useCallback((hasSuccessfullyDownloadedKeys) => {
		setDownloadStatus(
			hasSuccessfullyDownloadedKeys
				? ALERT_DOWNLOAD_TYPE.success
				: ALERT_DOWNLOAD_TYPE.danger
		);
	}, []);

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	const {data} = useGetCloudNativeEnvironments({
		filter: `accountKey eq '${project?.accountKey}'`,
	});

	const cloudNativeEnvironments = data?.c?.cloudNativeEnvironments?.items;

	if (!project || !subscriptionGroups) {
		return <span> {i18n.translate('loading')}...</span>;
	}

	const headerClass = 'bg-neutral-1 font-weight-bold text-neutral-10';

	const EnvironmentRow = ({
		featureFlags,
		handleAlertStatus,
		label,
		nodes,
		oAuthToken,
		projectName,
		provisioningServerAPI,
		uuid,
	}) => (
		<ClayTable.Row>
			<ClayTable.Cell>{i18n.translate(label)}</ClayTable.Cell>

			{featureFlags.includes('LRSD-12057') && (
				<ClayTable.Cell>{uuid}</ClayTable.Cell>
			)}

			<ClayTable.Cell>{nodes}</ClayTable.Cell>

			<ClayTable.Cell className="text-center">
				<ButtonWithIcon
					aria-label={i18n.translate('download-key')}
					className="text-dark"
					displayType="unstyled"
					onClick={() =>
						getCloudLicenseKeyDownload(
							oAuthToken,
							provisioningServerAPI,
							handleAlertStatus,
							uuid,
							projectName
						)
					}
					small
					spritemap={Liferay.Icons.spritemap}
					symbol="download"
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);

	return (
		<>
			<h1>{i18n.translate('cloud-native-environments')}</h1>

			<div className="mt-4">
				{cloudNativeEnvironments?.map(
					(cloudNativeEnvironment, index) => (
						<ClayTable key={index} striped={false}>
							<ClayTable.Head>
								<ClayTable.Row>
									<ClayTable.Cell className={headerClass}>
										{i18n.translate('environment')}
									</ClayTable.Cell>

									{featureFlags.includes('LRSD-12057') && (
										<ClayTable.Cell className={headerClass}>
											{i18n.translate('subscription-id')}

											<PopoverIcon
												symbol="question-circle-full"
												title="please-copy-and-paste-this-subscription-id-to-your-cloud-native-instance"
											/>
										</ClayTable.Cell>
									)}

									<ClayTable.Cell className={headerClass}>
										{i18n.translate(
											'maximum-cluster-nodes'
										)}

										<PopoverIcon
											symbol="question-circle-full"
											title="maximum-number-of-active-nodes-available-for-this-environment-this-does-not-include-expired-or-future-nodes"
										/>
									</ClayTable.Cell>

									<ClayTable.Cell className={`${headerClass} text-center`}>
										{i18n.translate('download')}
									</ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								<EnvironmentRow
									featureFlags={featureFlags}
									handleAlertStatus={handleAlertStatus}
									label="production"
									nodes={
										cloudNativeEnvironment.maxClusterNodes
									}
									oAuthToken={oAuthToken}
									projectName={project.name}
									provisioningServerAPI={
										provisioningServerAPI
									}
									uuid={
										cloudNativeEnvironment.productionSubscriptionUuid
									}
								/>

								<EnvironmentRow
									featureFlags={featureFlags}
									handleAlertStatus={handleAlertStatus}
									label="non-production"
									nodes={1}
									oAuthToken={oAuthToken}
									projectName={project.name}
									provisioningServerAPI={
										provisioningServerAPI
									}
									uuid={
										cloudNativeEnvironment.nonProductionSubscriptionUuid
									}
								/>
							</ClayTable.Body>
						</ClayTable>
					)
				)}

				{!cloudNativeEnvironments?.length && (
					<div className="p-3">
						{i18n.translate(
							'no-cloud-native-environments-were-found'
						)}
					</div>
				)}

				{!!downloadStatus && (
					<DownloadAlert
						downloadStatus={downloadStatus}
						message={
							ALERT_CLOUD_LICENSE_KEY_DOWNLOAD_TEXT[
								downloadStatus
							]
						}
						setDownloadStatus={setDownloadStatus}
					/>
				)}
			</div>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'to-activate-a-local-instance-of-liferay-dxp-download-a-developer-key-for-your-liferay-dxp-version'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.dxpMajorVersion}
					oAuthToken={oAuthToken}
					productName="DXP"
					projectName={project.name}
				/>
			</DeveloperKeysLayouts>
		</>
	);
};

export default CloudNative;
