/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useCallback, useState} from 'react';

import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../components/Section/Section';
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

const Content = () => {
	const [selectedCheckboxValue, setSelectedCheckboxValue] = useState<
		string[]
	>([]);

	const [
		{
			build: {cloudCompatible, compatibleOffering, liferayPackages},
			loading,
		},
		dispatch,
	] = useNewAppContext();

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

	return (
		<>
			<Section
				className="d-flex flex-column form-checkbox"
				label={i18n.translate('compatible-offering')}
			>
				<OfferingTypeCheckbox
					handleSelectCheckbox={handleSelectCheckbox}
					offeringTypes={
						offeringTypesDescription[
							cloudCompatible
								? ProductType.CLOUD
								: ProductType.DXP
						] as unknown as OfferingType[]
					}
					selectedValue={compatibleOffering}
				/>
			</Section>

			{cloudCompatible && (
				<Section
					className="d-flex justify-content-between mt-4"
					label={i18n.translate('resource-requirements')}
				>
					<CloudResourceRequirements />
				</Section>
			)}

			<Section
				className="d-flex flex-column form-radio-card"
				label={i18n.translate('app-build')}
			>
				{BUILD_UPLOAD_OPTIONS[
					cloudCompatible ? ProductType.CLOUD : ProductType.DXP
				].map((card, index) => (
					<RadioCard
						description={card.description}
						disabled={card.disabled}
						icon={card.icon}
						key={index}
						onChange={() => {}}
						selected={'upload' === card.value}
						title={card.title}
						tooltip={card.tooltip}
					/>
				))}
			</Section>

			<Section label={i18n.translate('upload-liferay-plugin-packages')}>
				<small>
					{i18n.translate(
						'if-the-app-is-compatible-with-different-updates-of-74-please-upload-multiple-packages-for-each-update-or-update-compatibility-range'
					)}
				</small>

				<hr />

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
											(_, itemIndex) =>
												itemIndex !== index
										);

									dispatch({
										payload: {
											liferayPackages:
												updatedLiferayPackages,
										},
										type: NewAppTypes.SET_BUILD,
									});
								}}
							>
								{i18n.translate('remove-a-version')}
							</ClayButton>
						</div>

						<NewAppUploadAppPackagesComponent
							isProcessing={loading}
							versionName={liferayPackage.version}
						/>
					</div>
				))}

				{!loading && (
					<ClayButton
						className="btn-block provide-app-build-page-add-package-button"
						displayType="secondary"
						onClick={() => setVisibleSelectVersionModal(true)}
					>
						<ClayIcon className="mr-1" symbol="plus" />
						{i18n.translate('add-packages')}
					</ClayButton>
				)}

				{visibleSelectVersionModal && (
					<NewAppPackageVersionModal
						currentVersions={[]}
						handleClose={() => setVisibleSelectVersionModal(false)}
					/>
				)}
			</Section>
		</>
	);
};

const Build = () => {
	const [
		{
			build: {cloudCompatible},
		},
		dispatch,
	] = useNewAppContext();

	return (
		<div className="new-app-form-build">
			<Section label={i18n.translate('cloud-compatible')} required>
				<div className="d-flex form-radio-card justify-content-between">
					{COMPATIBLE_OFFERING_CARDS.map(
						(compatibleOffering, index) => (
							<RadioCard
								description={compatibleOffering.description}
								icon={compatibleOffering.icon}
								key={index}
								onChange={() => {
									dispatch({
										payload: {
											cloudCompatible:
												compatibleOffering.value,
										},
										type: NewAppTypes.SET_BUILD,
									});
								}}
								selected={
									cloudCompatible === compatibleOffering.value
								}
								title={compatibleOffering.title}
								tooltip={compatibleOffering.tooltip}
							/>
						)
					)}
				</div>
			</Section>

			{typeof cloudCompatible === 'boolean' && <Content />}
		</div>
	);
};

export default Build;
