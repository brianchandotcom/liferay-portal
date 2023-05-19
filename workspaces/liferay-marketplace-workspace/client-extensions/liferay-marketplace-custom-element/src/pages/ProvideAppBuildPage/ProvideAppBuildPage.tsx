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

import {filesize} from 'filesize';
import {uniqueId} from 'lodash';
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
		{
			appBuild,
			appCategories,
			appERC,
			appId,
			appProductId,
			appTags,
			appType,
			buildZIPFiles,
		},
		dispatch,
	] = useAppContext();

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

		if (appType.value === 'cloud') {
			let marketplaceLiferayPlatformOfferingId = 0;
			let marketplaceLiferayVersionId = 0;
			let marketplaceEditionId = 0;

			vocabulariesResponse.items.forEach(
				(vocab: {id: number; name: string}) => {
					if (
						vocab.name === 'Marketplace Liferay Platform Offering'
					) {
						marketplaceLiferayPlatformOfferingId = vocab.id;
					}

					if (vocab.name === 'Marketplace Liferay Version') {
						marketplaceLiferayVersionId = vocab.id;
					}

					if (vocab.name === 'Marketplace Edition') {
						marketplaceEditionId = vocab.id;
					}
				}
			);

			const platformOfferingList = await getCategories({
				vocabId: marketplaceLiferayPlatformOfferingId,
			});

			const fullyManagedOption = platformOfferingList.find(
				(item) => item.name === 'Fully-Managed'
			);

			if (fullyManagedOption) {
				newCategories.push({
					externalReferenceCode:
						fullyManagedOption?.externalReferenceCode,
					id: fullyManagedOption.id,
					name: fullyManagedOption.name,
					vocabulary: 'Marketplace Liferay Platform Offering',
				});
			}

			const liferayVersionList = await getCategories({
				vocabId: marketplaceLiferayVersionId,
			});

			const liferayVersionOption = liferayVersionList.find(
				(item) => item.name === '7.4'
			);

			if (liferayVersionOption) {
				newCategories.push({
					externalReferenceCode:
						liferayVersionOption?.externalReferenceCode,
					id: liferayVersionOption.id,
					name: liferayVersionOption.name,
					vocabulary: 'Marketplace Liferay Version',
				});
			}

			const marketplaceEditionList = await getCategories({
				vocabId: marketplaceEditionId,
			});

			const marketplaceEditionOption = marketplaceEditionList.find(
				(item) => item.name === 'EE'
			);

			if (marketplaceEditionOption) {
				newCategories.push({
					externalReferenceCode:
						marketplaceEditionOption?.externalReferenceCode,
					id: marketplaceEditionOption.id,
					name: marketplaceEditionOption.name,
					vocabulary: 'Marketplace Edition',
				});
			}

			newCategories = [...categories.items, ...newCategories];
		}
		else {
			newCategories = categories.items.filter((el) => {
				if (
					el.vocabulary !== 'marketplace edition' &&
					el.vocabulary !== 'marketplace liferay version' &&
					el.vocabulary !== 'liferay platform offering'
				)
					return el;
			});
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
				description="Use one of the following methods to provide your app builds."
				title="Provide app build"
			/>

			<Section
				label="Cloud Compatible?"
				required
				tooltip={`A Liferay Cloud App is a collection of 1 to N client extension artifacts made available via the Liferay Marketplace. It is installed and managed as a single atomic unit in Liferay Experience Cloud.  

				A DXP App is a JAR based collection meant to run within Liferay DXP.  It is only supported on Self Hosted or Self Managed Liferay Cloud instances.`}
				tooltipText="More Info"
			>
				<div className="provide-app-build-page-cloud-compatible-container">
					<RadioCard
						description="Lorem ipsum dolor sit amet consectetur."
						icon={taskCheckedIcon}
						onChange={() => {
							dispatch({
								payload: {id: appType.id, value: 'cloud'},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						selected={appType.value === 'cloud'}
						title="Yes"
						tooltip={ReactDOMServer.renderToString(
							<span>
								The app submission is compatible with Liferay
								Experience Cloud and{' '}
								<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions#client-extensions">
									Client Extensions
								</a>
								.
							</span>
						)}
					/>

					<RadioCard
						description="Lorem ipsum dolor sit amet consectetur."
						icon={cancelIcon}
						onChange={() => {
							dispatch({
								payload: {id: appType.id, value: 'dxp'},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						selected={appType.value === 'dxp'}
						title="No"
						tooltip="The app submission is integrates with Liferay DXP version 7.4 or later."
					/>
				</div>
			</Section>

			<Section
				label="App Build"
				required
				tooltip="An App Build is your compiled or non-compiled code submitted on behalf of your account to the Marketplace. Once submitted, it will be reviewed and tested by our Marketplace administrators for approval in the Marketplace."
				tooltipText="More Info"
			>
				<div className="provide-app-build-page-app-build-radio-container">
					<RadioCard
						description="Use any build from any available Liferay Experience Cloud account (requires LXC account) "
						disabled
						icon={cloudIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'LXC'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'LXC'}
						title="Via Liferay Experience Cloud Integration"
						tooltip="In the future, you will be able to submit your app directly from Liferay Experience Cloud projects."
					/>

					<RadioCard
						description="Use any build from your computer connecting with a Github provider"
						disabled
						icon={githubIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'GitHub'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'GitHub'}
						title="Via GitHub Repo"
						tooltip="In the future, you will be able to submit your app source code for additional support and partnership opportunities with Liferay."
					/>

					<RadioCard
						description="Use any local ZIP files to upload. Max file size is 500MB"
						icon={uploadIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'upload'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'upload'}
						title="Via ZIP Upload"
						tooltip={ReactDOMServer.renderToString(
							<span>
								ZIP Files must be in universal file format
								archive (UFFA) - the specially structured, ZIP
								encoded archive used to package client extension
								project outputs This format must support the
								following use cases: deliver batch engine data
								files compatible with all deployment targets
								deliver DXP configuration resource compatible
								with all deployment targets deliver static
								resources compatible with all deployment targets
								deliver the infrastructure metadata necessary to
								deploy to LXC-(SM) For more information see:{' '}
								<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions/working-with-client-extensions#working-with-client-extensions">
									Liferay Learn
								</a>
							</span>
						)}
					/>
				</div>
			</Section>

			<Section
				description="Select a local file to upload"
				label="Upload ZIP Files"
				required
				tooltip="You can upload one or many ZIP files. Max total size is 500MB."
				tooltipText="More Info"
			>
				<FileList
					onDelete={handleDelete}
					type="document"
					uploadedFiles={buildZIPFiles ? buildZIPFiles : []}
				/>

				<DropzoneUpload
					acceptFileTypes={acceptFileTypes}
					buttonText="Select a file"
					description="Only ZIP files are allowed. Max file size is 500MB "
					maxFiles={1}
					maxSize={500000000}
					multiple={false}
					onHandleUpload={handleUpload}
					title="Drag and drop to upload or"
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!buildZIPFiles?.length}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitAppBuildType = async () => {
						if (appType.id) {
							updateProductSpecification({
								body: {
									specificationKey: 'type',
									value: {en_US: appType.value},
								},
								id: appType.id,
							});
						}
						else {
							const dataSpecification = await createSpecification(
								{
									body: {
										key: 'type',
										title: {en_US: 'Type'},
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

					buildZIPFiles.forEach((buildZIPFile) => {
						submitBase64EncodedFile({
							appERC,
							file: buildZIPFile.file,
							requestFunction: createAttachment,
							title: buildZIPFile.fileName,
						});
					});

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
