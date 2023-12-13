/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Flex, Form, TreeSelect} from 'antd';
import React, {useCallback, useEffect, useState} from 'react';

import {
	getDocumentFolderDocumentFoldersPage,
	getSiteDocumentFoldersPage,
} from '../../../../services/folder-selector.service';
import {createFolder} from '../../../../services/template-item-create-folder.service';
import {ApplicationUtil} from '../../../../utils/appUtil';

import './template-item-create-folder.css';

const TemplateItemCreateFolder = ({templateID}) => {
	const [folderTree, setFolderTree] = useState(null);
	const [isLoading, setIsLoading] = useState(false);
	const [isSubmitting, setIsSubmitting] = useState(false);
	const [form] = Form.useForm();

	const loadFolderTree = async () => {
		const loadSubFolder = async (folder) => {
			const subFolders = await getDocumentFolderDocumentFoldersPage(
				folder
			);
			const normalizedFolders = subFolders.items.map((folder) => ({
				childrenCount: folder.numberOfDocumentFolders,
				icon: 'folder',
				id: Number(folder.id),
				isLeaf: folder.numberOfDocumentFolders === 0,
				key: folder.id,
				label: folder.name,
				title: folder.name,
				type:
					folder.numberOfDocumentFolders > 0
						? 'repository'
						: 'folder',
				value: folder.id,
			}));

			return normalizedFolders;
		};
		const root = (
			await getSiteDocumentFoldersPage(
				ApplicationUtil.getLiferay().ThemeDisplay.getScopeGroupId()
			)
		).items.map((folder) => ({
			childrenCount: folder.numberOfDocumentFolders,
			id: Number(folder.id),
			isLeaf: folder.childrenCount <= 0,
			key: folder.id,
			label: folder.name,
			selected: false,
			title: folder.name,
			value: folder.id,
		}));
		const loadFolderRec = async (folder) => {
			const children = await loadSubFolder(folder.key);

			return Promise.all(
				children.map(async (subfolder) => ({
					...subfolder,
					children:
						subfolder.childrenCount > 0
							? await loadFolderRec(subfolder)
							: null,
				}))
			);
		};

		return Promise.all(
			root.map(async (folder) => ({
				...folder,
				children:
					folder.childrenCount > 0
						? await loadFolderRec(folder)
						: null,
			}))
		);
	};

	const prepareComponentCallback = useCallback(async () => {
		try {
			setIsLoading(true);
			setFolderTree(await loadFolderTree());
		}
		catch (error) {
			ApplicationUtil.ShowError(error.message);
		}
		finally {
			setIsLoading(false);
		}
	}, []);

	useEffect(() => {
		const fetchData = async () => {
			await prepareComponentCallback();
		};

		fetchData();
	}, [prepareComponentCallback]);

	const handleSubmit = () => {
		form.validateFields()
			.then(
				async (values) => {
					try {
						setIsSubmitting(true);
						await createFolder(
							templateID,
							values.parentFolder,
							values.name
						);
						ApplicationUtil.ShowSuccess('Folder created!');
					}
					catch (error) {
						ApplicationUtil.ShowError(error.message);
					}
					finally {
						form.resetFields();
						setIsSubmitting(false);
					}
				},
				(error) => {
					ApplicationUtil.ShowError(error);
				}
			)
			.catch((error) => {
				ApplicationUtil.ShowError(error);
			});
	};

	return (
		<>
			<Form autoComplete="off" form={form} layout="vertical">
				<Form.Item
					label="Name"
					name="name"
					rules={[
						{
							message: 'Please provide a folder name',
							required: true,
						},
					]}
				>
					<ClayInput></ClayInput>
				</Form.Item>
				<Form.Item
					label="Parent Folder"
					name="parentFolder"
					rules={[
						{
							message: 'Please provide a folder name',
							required: true,
						},
					]}
				>
					{isLoading && (
						<ClayLoadingIndicator size="sm"></ClayLoadingIndicator>
					)}
					{folderTree && (
						<TreeSelect multiple={false} treeData={folderTree} />
					)}
				</Form.Item>
				<Form.Item>
					<Flex gap={6}>
						{!isSubmitting && (
							<ClayButton
								onClick={() => {
									handleSubmit();
								}}
							>
								Submit
							</ClayButton>
						)}
						{isSubmitting && (
							<ClayLoadingIndicator
								displayType="secondary"
								size="sm"
							/>
						)}
					</Flex>
				</Form.Item>
			</Form>
		</>
	);
};

export default TemplateItemCreateFolder;
