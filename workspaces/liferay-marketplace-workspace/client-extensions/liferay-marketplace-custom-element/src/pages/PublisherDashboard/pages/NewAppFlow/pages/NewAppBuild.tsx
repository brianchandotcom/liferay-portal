/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useCallback, useState} from 'react';

import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../context/NewAppContext';
import {ProductType} from '../../../../../enums/ProductType';
import i18n from '../../../../../i18n';
import OfferingTypeCheckbox from '../../Apps/AppCreationFlow/ProvideAppBuildPage/components/OfferingTypeCheckbox';
import {offeringTypesDescription} from '../../Apps/AppCreationFlow/ProvideAppBuildPage/constants/offeringTypesDescriptions';
import CloudResourceRequirements from '../components/CloudResourceRequirements';
import {NewAppPackageVersionModal} from '../components/NewAppPackagesModal';
import NewAppUploadAppPackagesComponent from '../components/NewAppUploadPackage';
import {BUILD_UPLOAD_OPTIONS, COMPATIBLE_OFFERING_CARDS} from '../constants';

const NewAppBuild = () => {
	const [
		{
			build: {appType, compatibleOffering, liferayPackages},
		},
		dispatch,
	] = useNewAppContext();

	const [selectedCheckboxValue, setSelectedCheckboxValue] = useState<
		string[]
	>([]);
	const [isProcessing, setIsProcessing] = useState(false);
	const [visibleSelectVersionModal, setVisibleSelectVersionModal] =
		useState(false);

	const handleSelectCheckbox = useCallback(
		(offeringType: string) => {
			setSelectedCheckboxValue((prevValue) =>
				prevValue.includes(offeringType)
					? prevValue.filter((value) => value !== offeringType)
					: [...prevValue, offeringType]
			);

			dispatch({
				payload: {compatibleOffering: selectedCheckboxValue},
				type: NewAppTypes.SET_BUILD,
			});
		},
		[dispatch, selectedCheckboxValue]
	);

	const BuildAppPackageVersionsComponent = () => (
		<>
			{liferayPackages.map((liferayPackage, index) => (
				<div
					className="mt-4 provide-app-build-page-dropzone-container"
					key={index}
				>
					<div className="align-center d-flex font-weight-bold justify-content-between p-3 provide-app-build-page-dropzone-container-header">
						<span>{liferayPackage.version}</span>
						<ClayButton
							displayType="unstyled"
							onClick={() => {
								const updatedLiferayPackages =
									liferayPackages.filter(
										(_, itemIndex) => itemIndex !== index
									);

								dispatch({
									payload: {
										liferayPackages: updatedLiferayPackages,
									},
									type: NewAppTypes.SET_BUILD,
								});
							}}
						>
							{i18n.translate('remove-a-version')}
						</ClayButton>
					</div>

					<NewAppUploadAppPackagesComponent
						isProcessing={isProcessing}
						versionName={liferayPackage.version}
					/>
				</div>
			))}

			{!isProcessing && (
				<ClayButton
					className="btn-block provide-app-build-page-add-package-button"
					displayType="secondary"
					onClick={() => setVisibleSelectVersionModal(true)}
				>
					<ClayIcon className="mr-1" symbol="plus" />
					{i18n.translate('add-packages')}
				</ClayButton>
			)}
		</>
	);

	return (
		<div className="mb-4 new-app-form-build">
			<h5>{i18n.translate('cloud-compatible')}</h5>
			<hr />
			<div className="d-flex form-radio-card justify-content-between">
				{COMPATIBLE_OFFERING_CARDS.map((card, index) => (
					<RadioCard
						description={card.description}
						icon={card.icon}
						key={index}
						onChange={() =>
							dispatch({
								payload: {
									appType: {
										id: appType.id,
										value: card.value,
									},
								},
								type: NewAppTypes.SET_BUILD,
							})
						}
						selected={appType.value === card.value}
						title={card.title}
						tooltip={card.tooltip}
					/>
				))}
			</div>

			{appType.value && (
				<div className="mt-6">
					<h5>{i18n.translate('compatible-offering')}</h5>
					<hr />

					<div className="d-flex flex-column form-checkbox">
						<OfferingTypeCheckbox
							handleSelectCheckbox={handleSelectCheckbox}
							offeringTypes={
								offeringTypesDescription[
									appType.value as ProductType
								] as unknown as OfferingType[]
							}
							selectedValue={compatibleOffering}
						/>
					</div>
				</div>
			)}

			{appType.value === ProductType.CLOUD && (
				<div className="mt-6">
					<h5>{i18n.translate('resource-requirements')}</h5>
					<hr />

					<div className="d-flex justify-content-between">
						<CloudResourceRequirements />
					</div>
				</div>
			)}

			{appType.value && (
				<>
					<div className="mt-6">
						<h5>{i18n.translate('app-build')}</h5>
						<hr />

						<div className="d-flex flex-column form-radio-card">
							{BUILD_UPLOAD_OPTIONS[
								appType.value === ProductType.CLOUD
									? 'cloud'
									: 'dxp'
							].map((card, index) => (
								<RadioCard
									description={card.description}
									disabled={card.disabled}
									icon={card.icon}
									key={index}
									onChange={
										//To do
										() => {}}
									selected={'upload' === card.value}
									title={card.title}
									tooltip={card.tooltip}
								/>
							))}
						</div>
					</div>

					<div className="mt-6">
						<h5>
							{i18n.translate('upload-liferay-plugin-packages')}
						</h5>
						<small>
							{i18n.translate(
								'if-the-app-is-compatible-with-different-updates-of-74-please-upload-multiple-packages-for-each-update-or-update-compatibility-range'
							)}
						</small>
						<hr />

						<BuildAppPackageVersionsComponent />

						{visibleSelectVersionModal && (
							<NewAppPackageVersionModal
								currentVersions={[]}
								handleClose={() =>
									setVisibleSelectVersionModal(false)
								}
							/>
						)}
					</div>
				</>
			)}
		</div>
	);
};

export default NewAppBuild;
