/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithHorizontal} from '@clayui/card';
import ClayLayout from '@clayui/layout';
import {forwardRef, useEffect, useState} from 'react';

import {
	getFolderFiles,
	getFolderSubfolders,
	getRootFiles,
	getRootFolders,
} from '../../services/delivery/document';

// eslint-disable-next-line no-unused-vars
const AssetSelector = forwardRef((props, ref) => {
	const {handleSendAttachment} = props;

	const [files, setFiles] = useState([]);
	const [folders, setFolders] = useState([]);
	const [path, setPath] = useState([]);

	const handleOpenFolder = (folder) => {
		setPath((prevPath) => [...prevPath, folder]);
	};

	const handleGoToFolder = (folderIndex) => {
		setPath((prevPath) => [...prevPath.splice(0, folderIndex + 1)]);
	};

	useEffect(() => {
		const loadFolder = async () => {
			if (path.length) {
				const currentFolder = path[path.length - 1];

				if (currentFolder.id === 0) {
					const currentFolders = await getRootFolders();
					const currentFiles = await getRootFiles();

					setFolders(currentFolders.items);
					setFiles(currentFiles.items);
				}
				else {
					const currentFolder = path[path.length - 1];
					const currentFolders = await getFolderSubfolders(
						currentFolder.id
					);
					const currentFiles = await getFolderFiles(currentFolder.id);

					setFolders(currentFolders.items);
					setFiles(currentFiles.items);
				}
			}
		};

		loadFolder();
	}, [path]);

	useEffect(() => {
		setPath([
			{
				id: 0,
				name: 'Root',
			},
		]);
	}, []);

	return (
		<div className="asset-selector">
			<ClayLayout.ContainerFluid>
				<ClayLayout.Row justify="start">
					<ClayLayout.Col size={12}>
						<ol className="breadcrumb">
							{path &&
								!!path.length &&
								path.map((item, index) => (
									<li
										className={`breadcrumb-item breadcrumb-link ${index === path.length - 1 ? 'active' : ''}`}
										key={`folder_${index}`}
										onClick={() => handleGoToFolder(index)}
										role="button"
									>
										<span
											className="breadcrumb-text-truncate"
											title={item.name}
										>
											{item.name}
										</span>
									</li>
								))}
						</ol>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<ClayLayout.Row justify="start">
					{folders &&
						!!folders.length &&
						folders.map((folder, index) => (
							<ClayLayout.Col key={`item_${index}`} size={4}>
								<ClayCardWithHorizontal
									onClick={() => handleOpenFolder(folder)}
									title={folder.name}
								/>
							</ClayLayout.Col>
						))}

					{files &&
						!!files.length &&
						files.map((file, index) => (
							<ClayLayout.Col key={`item_${index}`} size={4}>
								<ClayCardWithHorizontal
									onClick={() => handleSendAttachment(file)}
									symbol="document-default"
									title={file.title}
								/>
							</ClayLayout.Col>
						))}
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</div>
	);
});

export default AssetSelector;
