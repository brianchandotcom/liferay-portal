/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {filesize} from 'filesize';
import {uniqueId} from 'lodash';
import {useState} from 'react';
import ReactDOMServer from 'react-dom/server';

import cancelIcon from '../../assets/icons/cancel_icon.svg';
import cloudIcon from '../../assets/icons/cloud_fill_icon.svg';
import githubIcon from '../../assets/icons/github_icon.svg';
import taskCheckedIcon from '../../assets/icons/task_checked_icon.svg';
import uploadIcon from '../../assets/icons/upload_fill_icon.svg';
import {DropzoneUpload} from '../../components/DropzoneUpload/DropzoneUpload';
import {FileList, UploadedFile} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	addExpandoValue,
	createAttachment,
	createProductSpecification,
	createSpecification,
	getCategories,
	getProductIdCategories,
	getVocabularies,
	patchProductIdCategory,
	updateProductSpecification,
} from '../../utils/api';
import {submitBase64EncodedFile} from '../../utils/util';

import './ProvideAppBuildPage.scss';

import {useEffect, useState} from 'react';

import {ProductEditionOption} from '../../enums/ProductEditionOption';
import {ProductSpecification} from '../../enums/ProductSpecification';
import {ProductType} from '../../enums/ProductType';
import {ProductUploadType} from '../../enums/ProductUploadType';
import {ProductVersionOption} from '../../enums/ProductVersionOption';
import {ProductVocabulary} from '../../enums/ProductVocabulary';
import i18n from '../../i18n';
import {getCompanyId} from '../../liferay/constants';
import OfferingTypeCheckbox from './components/OfferingTypeCheckbox';
import {offeringTypesDescription} from './constants/offeringTypesDescriptions';

interface ProvideAppBuildPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

const acceptFileTypes = {
	'application/zip': ['.zip'],
};

export function ProvideAppBuildPage({
	onClickBack,
	onClickContinue,
}: ProvideAppBuildPageProps) {
	const [
		{appBuild, appERC, appId, appProductId, appType, buildZIPFiles},
		dispatch,
	] = useAppContext();
	const [selectedCheckboxValue, setSelectedCheckboxValue] = useState<
		Array<string>
	>([]);

	const handleSelectCheckbox = (offeringTypelabel: string) => {
		setSelectedCheckboxValue((prevValue) =>
			prevValue.includes(offeringTypelabel)
				? prevValue.filter((value) => value !== offeringTypelabel)
				: [...prevValue, offeringTypelabel]
		);
	};

	useEffect(() => {
		setSelectedCheckboxValue([]);
	}, [appType.value]);
	const [visible, setVisible] = useState(false);

	const handleUpload = (files: File[]) => {
		const newUploadedFiles: UploadedFile[] = files.map((file) => ({
			error: false,
			file,
			fileName: file.name,
			id: uniqueId(),
			preview: URL.createObjectURL(file),
			progress: 0,
			readableSize: filesize(file.size),
			uploaded: true,
		}));

		if (buildZIPFiles?.length) {
			dispatch({
				payload: {
					files: [...buildZIPFiles, ...newUploadedFiles],
				},
				type: TYPES.UPLOAD_BUILD_ZIP_FILES,
			});
		}
		else {
			dispatch({
				payload: {
					files: newUploadedFiles,
				},
				type: TYPES.UPLOAD_BUILD_ZIP_FILES,
			});
		}
	};

	const handleDelete = (fileId: string) => {
		const files = buildZIPFiles.filter((file) => file.id !== fileId);

		dispatch({
			payload: {
				files,
			},
			type: TYPES.UPLOAD_BUILD_ZIP_FILES,
		});
	};

	const updateCloudCompatibility = async () => {
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

		if (fullyManagedOption) {
			fullyManagedOption.map((managedOption) => {
				newCategories.push({
					externalReferenceCode: managedOption?.externalReferenceCode,
					id: managedOption.id,
					name: managedOption.name,
					vocabulary: ProductVocabulary.LIFERAY_PLATFORM_OFFERING,
				});
			});
		}

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

		const body = newCategories.map((item) => {
			return item;
		});

		await patchProductIdCategory({
			appId: appProductId.toString(),
			body,
		});
	};

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
						onChange={() => {
							dispatch({
								payload: {
									id: appType.id,
									value: ProductType.CLOUD,
								},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
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
						onChange={() => {
							dispatch({
								payload: {
									id: appType.id,
									value: ProductType.DXP,
								},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
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
							'use-any-build-from-any-available-liferay-experience-cloud-account-requires-lxc-account'
						)}
						disabled
						icon={cloudIcon}
						onChange={() => {
							dispatch({
								payload: {value: ProductUploadType.LXC},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
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

					<RadioCard
						description={i18n.translate(
							'please-be-sure-to-specify-liferay-compatibility-through-the-appropriate-properties-or-xml-files-in-your-plugin'
						)}
						icon={uploadIcon}
						onChange={() => {
							dispatch({
								payload: {value: ProductUploadType.ZIP_UPLOAD},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === ProductUploadType.ZIP_UPLOAD}
						title={i18n.translate('via-liferay-plugin-packages')}
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
				</div>
			</Section>

			<Section
				description={i18n.translate('if-the-app-is-compatible-with-different-updates-of-74-please-upload-multiple-packages-for-each-update-or-update-compatibility-range')}
				label={i18n.translate('upload-liferay-plugin-packages')}
				required
				tooltip={i18n.translate(
					'only-jar-war-files-are-allowed-max-file-size-is-500mb.'
				)}
				tooltipText={i18n.translate('more-info')}
			>
				<ClayButton className="btn-block provide-app-build-page-add-package-button" displayType="secondary" onClick={() => setVisible(true)}>
					<ClayIcon className="mr-1" symbol="plus" />
					Add Package(s)
				</ClayButton>
				
				{visible && "Shows the Modal"}
				<FileList
					onDelete={handleDelete}
					type="document"
					uploadedFiles={buildZIPFiles ? buildZIPFiles : []}
				/>

				<DropzoneUpload
					acceptFileTypes={acceptFileTypes}
					buttonText={i18n.translate('select-a-file')}
					description={i18n.translate(
						'only-jar-war-files-are-allowed-max-file-size-is-500mb.'
					)}
					maxFiles={1}
					maxSize={500000000}
					multiple={false}
					onHandleUpload={handleUpload}
					title={i18n.translate('drag-and-drop-to-upload-or')}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={
					!buildZIPFiles?.length || !selectedCheckboxValue.length
				}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitAppBuildType = async () => {
						if (appType.id) {
							updateProductSpecification({
								body: {
									specificationKey: ProductSpecification.TYPE.toLowerCase(),
									value: {en_US: appType.value},
								},
								id: appType.id,
							});
						}
						else {
							const dataSpecification = await createSpecification(
								{
									body: {
										key: ProductSpecification.TYPE.toLowerCase(),
										title: {
											en_US: ProductSpecification.TYPE,
										},
									},
								}
							);

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
						}
					};

					updateCloudCompatibility();

					submitAppBuildType();

					buildZIPFiles.forEach(async (buildZIPFile) => {
						const buildZIPFileId = await submitBase64EncodedFile({
							appERC,
							file: buildZIPFile.file,
							requestFunction: createAttachment,
							title: buildZIPFile.fileName,
						});

						addExpandoValue({
							attributeValues: {
								'App Icon': 'No',
							},
							className:
								'com.liferay.commerce.product.model.CPAttachmentFileEntry',
							classPK: buildZIPFileId as number,
							companyId: Number(getCompanyId()),
							tableName: 'CUSTOM_FIELDS',
						});
					});

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
