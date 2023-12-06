/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {filesize} from 'filesize';
import {uniqueId} from 'lodash';
import {useCallback, useState} from 'react';
import ReactDOMServer from 'react-dom/server';

import cancelIcon from '../../assets/icons/cancel_icon.svg';
import cloudIcon from '../../assets/icons/cloud_fill_icon.svg';
import githubIcon from '../../assets/icons/github_icon.svg';
import taskCheckedIcon from '../../assets/icons/task_checked_icon.svg';
import uploadIcon from '../../assets/icons/upload_fill_icon.svg';
import {DropzoneUpload} from '../../components/DropzoneUpload/DropzoneUpload';
import {FileList} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {PackageVersionModal} from '../../components/PackageVersionModal/PackageVersionModal';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';
import {ProductEditionOption} from '../../enums/ProductEditionOption';
import {ProductSpecification} from '../../enums/ProductSpecification';
import {ProductType} from '../../enums/ProductType';
import {ProductUploadType} from '../../enums/ProductUploadType';
import {ProductVersionOption} from '../../enums/ProductVersionOption';
import {ProductVocabulary} from '../../enums/ProductVocabulary';
import i18n from '../../i18n';
import {getCompanyId} from '../../liferay/constants';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	addExpandoValue,
	createAttachment,
	createProductSpecification,
	getCategories,
	getProductIdCategories,
	getSpecification,
	getVocabularies,
	patchProductIdCategory,
	updateProductSpecification,
} from '../../utils/api';
import {submitBase64EncodedFile} from '../../utils/util';
import OfferingTypeCheckbox from './components/OfferingTypeCheckbox';
import {offeringTypesDescription} from './constants/offeringTypesDescriptions';

import './ProvideAppBuildPage.scss';

type ProvideAppBuildPageProps = {
	onClickBack: () => void;
	onClickContinue: () => void;
};

const acceptFileTypes = {
	[ProductType.CLOUD]: {
		'application/java-archive': ['.zip'],
	},
	[ProductType.DXP]: {
		'application/java-archive': ['.jar'],
		'application/octet-stream': ['.war'],
	},
};

const UPLOAD_MAX_SIZE = 500000000;

const UploadAppPackagesComponent = ({versionName}: {versionName: string}) => {
	const [{appType, buildAppPackages}, dispatch] = useAppContext();

	const handleUploadAppPackages = (files: File[], versionName?: string) => {
		const newUploadedPackage = files.map((file) => ({
			error: false,
			file,
			fileName: file.name,
			id: uniqueId(),
			preview: URL.createObjectURL(file),
			progress: 0,
			readableSize: filesize(file.size),
			uploaded: false,
			versionName,
		}));

		const currentVersionFiles =
			buildAppPackages[versionName as string] ?? [];

		dispatch({
			payload: {
				files: currentVersionFiles.length
					? [
							...buildAppPackages[versionName as string],
							...newUploadedPackage,
					  ]
					: newUploadedPackage,
				versionName,
			},
			type: TYPES.UPLOAD_BUILD_PACKAGE_FILES,
		});
	};

	const handleRemoveAppPackages = (fileId: string, versionName?: string) =>
		dispatch({
			payload: {
				files: buildAppPackages[versionName as string]?.filter(
					(value) => value.id !== fileId
				),
				versionName,
			},
			type: TYPES.UPLOAD_BUILD_PACKAGE_FILES,
		});

	return (
		<>
			<FileList
				onDelete={handleRemoveAppPackages}
				type="document"
				uploadedFiles={
					buildAppPackages ? buildAppPackages[versionName] : []
				}
				versionName={versionName}
			/>
			{!buildAppPackages?.length && (
				<DropzoneUpload
					acceptFileTypes={
						acceptFileTypes[
							appType.value as keyof typeof acceptFileTypes
						]
					}
					buttonText={i18n.translate('select-a-file')}
					description={
						appType.value === ProductType.CLOUD
							? i18n.translate(
									'only-zip-files-are-allowed-max-file-size-is-500-mb'
							  )
							: i18n.translate(
									'only-jar-war-files-are-allowed-max-file-size-is-500mb'
							  )
					}
					maxFiles={10}
					maxSize={UPLOAD_MAX_SIZE}
					multiple={true}
					onHandleUpload={handleUploadAppPackages}
					title={i18n.translate('drag-and-drop-to-upload-or')}
					versionName={versionName}
				/>
			)}
		</>
	);
};

export function ProvideAppBuildPage({
	onClickBack,
	onClickContinue,
}: ProvideAppBuildPageProps) {
	const [isProcessing, setProcessing] = useState(false);

	const [
		{appBuild, appERC, appId, appProductId, appType, buildAppPackages},
		dispatch,
	] = useAppContext();
	const [selectedCheckboxValue, setSelectedCheckboxValue] = useState<
		string[]
	>([]);
	const [visibleSelectVersionModal, setVisibleSelectVersionModal] = useState(
		false
	);

	const handleSelectCheckbox = (offeringTypelabel: string) =>
		setSelectedCheckboxValue((prevValue) =>
			prevValue.includes(offeringTypelabel)
				? prevValue.filter((value) => value !== offeringTypelabel)
				: [...prevValue, offeringTypelabel]
		);

	const handleResetAppPackages = useCallback(
		() =>
			dispatch({
				type: TYPES.RESET_APP_PACKAGES,
			}),
		[dispatch]
	);

	const handleRemovePackageVersion = (removedVersion: string) => {
		dispatch({
			payload: {
				isRemoved: true,
				versionName: removedVersion,
			},
			type: TYPES.UPLOAD_BUILD_PACKAGE_FILES,
		});
	};

	const submitAppBuildCategories = async () => {
		const vocabulariesResponse = await getVocabularies();

		const categories = await getProductIdCategories({
			appId: appProductId.toString(),
		});

		let newCategories: Categories[] = [];

		let marketplaceLiferayPlatformOfferingId = 0;
		let marketplaceLiferayVersionId = 0;
		let marketplaceEditionId = 0;

		vocabulariesResponse.items.forEach(
			(vocab: {id: number; name: string}) => {
				if (
					vocab.name === ProductVocabulary.LIFERAY_PLATFORM_OFFERING
				) {
					marketplaceLiferayPlatformOfferingId = vocab.id;
				}

				if (vocab.name === ProductVocabulary.LIFERAY_VERSION) {
					marketplaceLiferayVersionId = vocab.id;
				}

				if (vocab.name === ProductVocabulary.EDITION) {
					marketplaceEditionId = vocab.id;
				}
			}
		);

		const platformOfferingList = await getCategories({
			vocabId: marketplaceLiferayPlatformOfferingId,
		});

		const fullyManagedOption = platformOfferingList.filter(
			(platformOffering) =>
				selectedCheckboxValue.includes(platformOffering.name)
		);

		fullyManagedOption.forEach((managedOption) => {
			newCategories.push({
				externalReferenceCode: managedOption?.externalReferenceCode,
				id: managedOption.id,
				name: managedOption.name,
				vocabulary: ProductVocabulary.LIFERAY_PLATFORM_OFFERING,
			});
		});

		if (appType.value === ProductType.CLOUD) {
			const liferayVersionList = await getCategories({
				vocabId: marketplaceLiferayVersionId,
			});

			const liferayVersionOption = liferayVersionList.find(
				(item) => item.name === ProductVersionOption['7.4x']
			);

			if (liferayVersionOption) {
				newCategories.push({
					externalReferenceCode:
						liferayVersionOption?.externalReferenceCode,
					id: liferayVersionOption.id,
					name: liferayVersionOption.name,
					vocabulary: ProductVocabulary.LIFERAY_VERSION,
				});
			}

			const marketplaceEditionList = await getCategories({
				vocabId: marketplaceEditionId,
			});

			const marketplaceEditionOption = marketplaceEditionList.find(
				(item) => item.name === ProductEditionOption.EE
			);

			if (marketplaceEditionOption) {
				newCategories.push({
					externalReferenceCode:
						marketplaceEditionOption?.externalReferenceCode,
					id: marketplaceEditionOption.id,
					name: marketplaceEditionOption.name,
					vocabulary: ProductVocabulary.EDITION,
				});
			}

			newCategories = [...categories.items, ...newCategories];
		}
		else {
			newCategories = [
				...categories.items.filter((category) => {
					if (
						category.vocabulary !==
							ProductVocabulary.EDITION.toLowerCase() &&
						category.vocabulary !==
							ProductVocabulary.LIFERAY_VERSION.toLowerCase()
					) {
						return category;
					}
				}),
				...newCategories,
			];
		}

		await patchProductIdCategory({
			appId: appProductId.toString(),
			body: newCategories,
		});
	};

	const submitAppBuildPackages = async () => {
		for (const versionKey in buildAppPackages) {
			const appPackagesByVersion = buildAppPackages[versionKey];

			try {
				for (const appPackage of appPackagesByVersion) {
					if (appPackage.uploaded) {
						// eslint-disable-next-line no-console
						console.info('File already uploaded', appPackage);

						continue;
					}

					const buildAppPackageId = await submitBase64EncodedFile({
						appERC,
						isAppIcon: false,
						file: appPackage.file,
						requestFunction: createAttachment,
						title: appPackage.fileName,
					});

					appPackage.uploaded = true;

					await addExpandoValue({
						attributeValues: {
							'Liferay Version': versionKey,
						},
						className:
							'com.liferay.commerce.product.model.CPAttachmentFileEntry',
						classPK: buildAppPackageId as number,
						companyId: getCompanyId(),
						tableName: 'CUSTOM_FIELDS',
					});
				}
			}
			catch (error) {
				console.error(
					'Failed during the submitAppBuildPackages',
					error
				);
			}
		}

		dispatch({
			payload: buildAppPackages,
			type: TYPES.UPDATE_BUILD_PACKAGE_FILES,
		});
	};

	const submitAppBuildTypeSpecification = async () => {
		if (appType.id) {
			return updateProductSpecification({
				body: {
					specificationKey: ProductSpecification.TYPE.toLowerCase(),
					value: {en_US: appType.value},
				},
				id: appType.id,
			});
		}

		const dataSpecification = await getSpecification('type');

		const {id} = await createProductSpecification({
			appId,
			body: {
				productId: appProductId,
				specificationId: dataSpecification.id,
				specificationKey: dataSpecification.key,
				value: {en_US: appType.value},
			},
		});

		dispatch({
			payload: {id, value: appType.value},
			type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
		});
	};

	const handleAppTypeChange = (value: ProductType) => {
		dispatch({
			payload: {
				id: appType.id,
				value,
			},
			type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
		});

		setSelectedCheckboxValue([]);
		handleResetAppPackages();
	};

	const buildAppPackageVersions = Object.keys(buildAppPackages ?? {});
	const buildAppPackageValues = Object.values(buildAppPackages ?? {}) ?? [];

	const disableContinueButton =
		isProcessing ||
		!selectedCheckboxValue.length ||
		!buildAppPackageValues.length ||
		buildAppPackageValues.some((versionEntry) => !versionEntry?.length);

	return (
		<div className="provide-app-build-page-container">
			<Header
				description={i18n.translate(
					'use-one-of-the-following-methods-to-provide-your-app-builds'
				)}
				title={i18n.translate('provide-app-build')}
			/>

			<Section
				label={i18n.translate('cloud-compatible-?')}
				required
				tooltip={i18n.translate(
					'a-liferay-cloud-app-is-a-collection-of-1-to-n-client-extension-artifacts-made-available-via-the-liferay-marketplace-it-is-installed-and-managed-as-a-single-atomic-unit-in-liferay-experience-cloud-a-dxp-app-is-a-jar-based-collection-meant-to-run-within-liferay-dxp-it-is-only-supported-on-self-hosted-or-self-managed-liferay-cloud-instances'
				)}
				tooltipText={i18n.translate('more-info')}
			>
				<div className="provide-app-build-page-cloud-compatible-container">
					<RadioCard
						description={i18n.translate(
							'lorem-ipsum-dolor-sit-amet-consectetur'
						)}
						icon={taskCheckedIcon}
						onChange={() => handleAppTypeChange(ProductType.CLOUD)}
						selected={appType.value === ProductType.CLOUD}
						title={i18n.translate('yes')}
						tooltip={ReactDOMServer.renderToString(
							<span>
								{i18n.translate(
									'the-app-submission-is-compatible-with-liferay-experience-cloud-and'
								)}
								<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions#client-extensions">
									{i18n.translate('client-extensions')}
								</a>
								.
							</span>
						)}
					/>

					<RadioCard
						description={i18n.translate(
							'lorem-ipsum-dolor-sit-amet-consectetur'
						)}
						icon={cancelIcon}
						onChange={() => handleAppTypeChange(ProductType.DXP)}
						selected={appType.value === ProductType.DXP}
						title={i18n.translate('no')}
						tooltip={i18n.translate(
							'the-app-submission-is-integrates-with-liferay-dxp-version-7-4-or-later'
						)}
					/>
				</div>
			</Section>

			<Section
				label={i18n.translate('compatible-offering')}
				required
				tooltip={i18n.translate(
					'select-the-offering-of-liferay-your-app-is-compatible-with-the-compatibility-selections-will-determine-on-what-platforms-your-app-is-tested'
				)}
				tooltipText={i18n.translate('more-info')}
			>
				<div className="provide-app-build-page-app-build-checkbox-container">
					<OfferingTypeCheckbox
						handleSelectCheckbox={handleSelectCheckbox}
						offeringTypes={
							(offeringTypesDescription[
								appType.value as ProductType
							] as unknown) as OfferingType[]
						}
						selectedValue={selectedCheckboxValue}
					/>
				</div>
			</Section>

			<Section
				label={i18n.translate('app-build')}
				required
				tooltip={i18n.translate(
					'an-app-build-is-your-compiled-or-non-compiled-code-submitted-on-behalf-of-your-account-to-the-marketplace-once-submitted-it-will-be-reviewed-and-tested-by-our-marketplace-administrators-for-approval-in-the-marketplace'
				)}
				tooltipText={i18n.translate('more-info')}
			>
				<div className="provide-app-build-page-app-build-radio-container">
					<RadioCard
						description={i18n.translate(
							appType.value === ProductType.CLOUD
								? 'use-any-local-zip-files-to-upload-max-file-size-is-500-mb'
								: 'please-be-sure-to-specify-liferay-compatibility-through-the-appropriate-properties-or-xml-files-in-your-plugin'
						)}
						icon={uploadIcon}
						onChange={() =>
							dispatch({
								payload: {value: ProductUploadType.ZIP_UPLOAD},
								type: TYPES.UPDATE_APP_BUILD,
							})
						}
						selected={appBuild === ProductUploadType.ZIP_UPLOAD}
						title={
							appType.value === ProductType.CLOUD
								? i18n.translate('via-zip-upload')
								: i18n.translate('via-liferay-plugin-packages')
						}
						tooltip={ReactDOMServer.renderToString(
							<span>
								{i18n.translate(
									'zip-files-must-be-in-universal-file-format-archive-uffa-the-specially-structured-zip-encoded-archive-used-to-package-client-extension-project-outputs-this-format-must-support-the-following-use-cases-deliver-batch-engine-data-files-compatible-with-all-deployment-targets-deliver-dxp-configuration-resource-compatible-with-all-deployment-targets-deliver-static-resources-compatible-with-all-deployment-targets-deliver-the-infrastructure-metadata-necessary-to-deploy-to-lxc-sm-for-more-information-see'
								)}

								<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions/working-with-client-extensions#working-with-client-extensions">
									{i18n.translate('liferay-learn')}
								</a>
							</span>
						)}
					/>

					<RadioCard
						description={i18n.translate(
							'use-any-build-from-any-available-liferay-experience-cloud-account-requires-lxc-account'
						)}
						disabled
						icon={cloudIcon}
						onChange={() =>
							dispatch({
								payload: {value: ProductUploadType.LXC},
								type: TYPES.UPDATE_APP_BUILD,
							})
						}
						selected={appBuild === ProductUploadType.LXC}
						title={i18n.translate(
							'via-liferay-experience-cloud-integration'
						)}
						tooltip={i18n.translate(
							'in-the-future-you-will-be-able-to-submit-your-app-directly-from-liferay-experience-cloud-projects'
						)}
					/>

					<RadioCard
						description={i18n.translate(
							'use-any-build-from-your-computer-connecting-with-a-github-provider'
						)}
						disabled
						icon={githubIcon}
						onChange={() => {
							dispatch({
								payload: {value: ProductUploadType.GITHUB},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === ProductUploadType.GITHUB}
						title={i18n.translate('via-github-repo')}
						tooltip={i18n.translate(
							'in-the-future-you-will-be-able-to-submit-your-app-source-code-for-additional-support-and-partnership-opportunities-with-liferay'
						)}
					/>
				</div>
			</Section>

			<Section
				description={i18n.translate(
					appType.value === ProductType.CLOUD
						? 'select-a-local-file-to-upload'
						: 'if-the-app-is-compatible-with-different-updates-of-74-please-upload-multiple-packages-for-each-update-or-update-compatibility-range'
				)}
				label={i18n.translate(
					appType.value === ProductType.CLOUD
						? 'upload-zip-files'
						: 'upload-liferay-plugin-packages'
				)}
				required
				tooltip={i18n.translate(
					appType.value === ProductType.CLOUD
						? 'you-can-upload-one-or-many-zip-files-max-total-size-is-500-mb'
						: 'only-jar-war-files-are-allowed-max-file-size-is-500mb.'
				)}
				tooltipText={i18n.translate('more-info')}
			>
				{appType.value === ProductType.CLOUD && (
					<UploadAppPackagesComponent
						versionName={ProductType.CLOUD}
					/>
				)}

				{appType.value === ProductType.DXP && (
					<>
						{buildAppPackageVersions.map((version, index) => (
							<div
								className="mt-4 provide-app-build-page-dropzone-container"
								key={index}
							>
								<div className="align-center d-flex font-weight-bold justify-content-between p-3 provide-app-build-page-dropzone-container-header">
									<span>{version}</span>

									<ClayButton
										displayType="unstyled"
										onClick={() =>
											handleRemovePackageVersion(version)
										}
									>
										{i18n.translate('remove-a-version')}
									</ClayButton>
								</div>

								<UploadAppPackagesComponent
									versionName={version}
								/>
							</div>
						))}

						<ClayButton
							className="btn-block provide-app-build-page-add-package-button"
							displayType="secondary"
							onClick={() => setVisibleSelectVersionModal(true)}
						>
							<ClayIcon className="mr-1" symbol="plus" />
							{i18n.translate('add-packages')}
						</ClayButton>
					</>
				)}

				{visibleSelectVersionModal && (
					<PackageVersionModal
						appProductId={appProductId}
						currentVersions={buildAppPackageVersions}
						handleClose={() => setVisibleSelectVersionModal(false)}
					/>
				)}
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={disableContinueButton}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					setProcessing(true);

					try {
						await submitAppBuildCategories();
						await submitAppBuildTypeSpecification();
						await submitAppBuildPackages();
					}
					catch (error) {
						console.error(
							'Something went wrong to buildCategores | buildTypeSpecifications | buildPackages'
						);
					}

					setProcessing(false);

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
