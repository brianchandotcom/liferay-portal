/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Form, TreeSelect} from 'antd';
import React, {useEffect, useState} from 'react';

import {
	getDocumentFolderDocumentFoldersPage,
	getSiteDocumentFoldersPage,
} from '../../services/FolderSelectorService';
import {createFolder} from '../../services/TemplateItemCreateFolderService';
import {showError, showSuccess} from '../../utils/util';

const loadFolderTree = async () => {
	const loadSubfolder = async (folder) => {
		const subfolders = await getDocumentFolderDocumentFoldersPage(folder);

		const normalizedFolders = subfolders.items.map((folder) => ({
			childrenCount: folder.numberOfDocumentFolders,
			icon: 'folder',
			id: Number(folder.id),
			isLeaf: folder.numberOfDocumentFolders === 0,
			key: folder.id,
			label: folder.name,
			title: folder.name,
			type: folder.numberOfDocumentFolders > 0 ? 'repository' : 'folder',
			value: folder.id,
		}));

		return normalizedFolders;
	};

	const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();

	const root = (await getSiteDocumentFoldersPage(scopeGroupId)).items.map(
		(folder) => ({
			childrenCount: folder.numberOfDocumentFolders,
			id: Number(folder.id),
			isLeaf: folder.childrenCount <= 0,
			key: folder.id,
			label: folder.name,
			selected: false,
			title: folder.name,
			value: folder.id,
		})
	);

	const loadFolderRecursively = async (folder) => {
		const children = await loadSubfolder(folder.key);

		return Promise.all(
			children.map(async (subfolder) => ({
				...subfolder,
				children:
					subfolder.childrenCount > 0
						? await loadFolderRecursively(subfolder)
						: null,
			}))
		);
	};

	return Promise.all(
		root.map(async (folder) => ({
			...folder,
			children:
				folder.childrenCount > 0
					? await loadFolderRecursively(folder)
					: null,
		}))
	);
};

const GenerateFolders = ({templateId}) => {
	const [folderTree, setFolderTree] = useState(null);
	const [isLoading, setIsLoading] = useState(false);
	const [isSubmitting, setIsSubmitting] = useState(false);

	const [form] = Form.useForm();

	useEffect(() => {
		setIsLoading(true);

		loadFolderTree()
			.then((folderTree) => setFolderTree(folderTree))
			.catch((error) => showError('Error', error.message))
			.finally(() => setIsLoading(false));
	}, []);

	const handleSubmit = () => {
		form.validateFields()
			.then((values) => {
				setIsSubmitting(true);

				createFolder(templateId, values.parentFolder, values.name)
					.then(() => showSuccess('Folder Created'))
					.catch((error) => showError('Error', error.message))
					.finally(() => {
						form.resetFields();

						setIsSubmitting(false);
					});
			})
			.catch((error) => showError('Error', error));
	};

	return (
		<Form autoComplete="off" form={form} layout="vertical">
			<Form.Item
				label="Name"
				name="name"
				rules={[
					{
						message: 'Please provide a folder name.',
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
						message: 'Please select a folder parent.',
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
			<Form.Item className="mb-0">
				<ClayButton
					className="align-items-center d-flex"
					disabled={isSubmitting}
					onClick={handleSubmit}
				>
					Submit
					{isSubmitting && (
						<ClayLoadingIndicator
							className="ml-3 my-0"
							displayType="light"
							size="sm"
						/>
					)}
				</ClayButton>
			</Form.Item>
		</Form>
	);
};

export default GenerateFolders;
