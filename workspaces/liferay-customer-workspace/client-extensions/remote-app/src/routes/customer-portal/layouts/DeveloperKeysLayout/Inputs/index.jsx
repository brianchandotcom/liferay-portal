/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import i18n from '../../../../../common/I18n';
import {Button} from '../../../../../common/components';
import {useAppPropertiesContext} from '../../../../../common/contexts/AppPropertiesContext';
import {getListTypeDefinitions} from '../../../../../common/services/liferay/graphql/queries';
import {getDevelopmentLicenseKey} from '../../../../../common/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '../../../../../common/utils/downloadFromBlob';
import {
	ALERT_DOWNLOAD_TYPE,
	AUTO_CLOSE_ALERT_TIME,
	EXTENSION_FILE_TYPES,
	STATUS_CODE,
} from '../../../utils/constants';

const ALERT_DEVELOPER_KEYS_DOWNLOAD_TEXT = {
	[ALERT_DOWNLOAD_TYPE.danger]: 'Unable to download key, please try again.',
	[ALERT_DOWNLOAD_TYPE.success]: 'Developer Key was downloaded successfully.',
};

const DeveloperKeysInputs = ({
	accountKey,
	downloadTextHelper,
	dxpVersion,
	listType,
	productName,
	projectName,
	sessionId,
}) => {
	const {
		articleDeployingActivationKeysURL,
		client,
		provisioningServerAPI,
	} = useAppPropertiesContext();
	const [dxpVersions, setDxpVersions] = useState([]);
	const [selectedVersion, setSelectedVersion] = useState(dxpVersion || '');
	const [
		developerKeysDownloadStatus,
		setDeveloperKeysDownloadStatus,
	] = useState('');

	useEffect(() => {
		const fetchListTypeDefinitions = async () => {
			const {data} = await client.query({
				query: getListTypeDefinitions,
				variables: {
					filter: `name eq '${listType}'`,
				},
			});

			const items = data?.listTypeDefinitions?.items[0]?.listTypeEntries;

			if (items?.length) {
				const sortedItems = [...items].sort();
				setDxpVersions(sortedItems);

				setSelectedVersion(
					sortedItems.find((item) => item.name === dxpVersion)
						?.name || sortedItems[0].name
				);
			}
		};

		fetchListTypeDefinitions();
	}, [client, dxpVersion, listType]);

	const developerKeyDownload = async () => {
		const [selectedVersionSplitted] = selectedVersion.split(' ');
		const license = await getDevelopmentLicenseKey(
			accountKey,
			provisioningServerAPI,
			sessionId,
			encodeURI(selectedVersionSplitted),
			productName
		);

		if (license.status === STATUS_CODE.success) {
			const contentType = license.headers.get('content-type');
			const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
			const licenseBlob = await license.blob();

			setDeveloperKeysDownloadStatus(ALERT_DOWNLOAD_TYPE.success);

			const projectFileName = projectName
				.replaceAll(' ', '')
				.toLowerCase();

			return downloadFromBlob(
				licenseBlob,
				`activation-key-${productName.toLowerCase()}development-${selectedVersionSplitted}-${projectFileName}${extensionFile}`
			);
		}

		setDeveloperKeysDownloadStatus(ALERT_DOWNLOAD_TYPE.danger);
	};

	return (
		<div>
			<p className="text-neutral-7 text-paragraph">
				{downloadTextHelper}
			</p>

			<div className="align-items-baseline d-flex">
				<label className="cp-developer-keys-label mb-3 mr-3">
					<div className="position-relative">
						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>

						<ClaySelect
							className="bg-neutral-1 border-0 font-weight-bold mr-2 pr-6"
							onChange={({target}) => {
								setSelectedVersion(target.value);
							}}
							value={selectedVersion}
						>
							{dxpVersions.map((version) => (
								<ClaySelect.Option
									className="font-weight-bold options"
									key={version.key}
									label={version.name}
								/>
							))}
						</ClaySelect>
					</div>
				</label>

				<Button
					className="btn btn-outline-primary cp-developer-keys-button py-1"
					onClick={developerKeyDownload}
					prependIcon="download"
					type="button"
				>
					{i18n.translate('download-key')}
				</Button>
			</div>

			<p className="text-neutral-7">
				{`${i18n.translate(
					'for-instructions-on-how-to-activate-your-liferay-dxp-or-liferay-portal-instance-please-read-the'
				)} `}

				<a
					href={articleDeployingActivationKeysURL}
					rel="noreferrer noopener"
					target="_blank"
				>
					<u className="font-weight-semi-bold text-neutral-7">
						{i18n.translate('deploying-activation-keys-article')}
					</u>
				</a>
			</p>

			{developerKeysDownloadStatus && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={
							AUTO_CLOSE_ALERT_TIME[developerKeysDownloadStatus]
						}
						className="cp-activation-key-download-alert px-4 py-3 text-paragraph"
						displayType={
							ALERT_DOWNLOAD_TYPE[developerKeysDownloadStatus]
						}
						onClose={() => setDeveloperKeysDownloadStatus('')}
					>
						{
							ALERT_DEVELOPER_KEYS_DOWNLOAD_TEXT[
								developerKeysDownloadStatus
							]
						}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</div>
	);
};

export default DeveloperKeysInputs;
