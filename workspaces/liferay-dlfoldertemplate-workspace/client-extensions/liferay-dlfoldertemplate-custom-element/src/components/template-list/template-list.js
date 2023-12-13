/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayButtonGroup from '@clayui/button/lib/Group';
import {Body, Cell, Head, Row, Table} from '@clayui/core';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Context} from '@clayui/modal';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayToolbar from '@clayui/toolbar';
import moment from 'moment';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {ApplicationUtil} from '../../utils/appUtil';
import FolderStructureDesigner from '../template-diagram/template-diagram';
import TemplateItemCreateFolder from './controls/template-item-create-folder/template-item-create-folder';
import NewTemplateItem from './controls/template-item-create/template-item-create';

import './template-list.css';
import {
	deleteFolderTemplateInformation,
	getAvailableTemplatesPage,
} from '../../services/template-list.service';
const spritemap = ApplicationUtil.getDefaultSpriteMap();
const TemplateList = () => {
	const [data, setData] = useState([]);
	const [totalItems, setTotalItems] = useState(0);
	const [pageIndex, setPageIndex] = useState(1);
	const [delta, setDelta] = useState(5);
	const deltas = [{label: 5}, {label: 10}, {label: 20}, {label: 40}];
	const Headers = [
		{
			expanded: false,
			key: 'id',
			label: 'ID',
			wrap: false,
		},
		{
			expanded: true,
			key: 'templateName',
			label: 'Template Name',
			wrap: false,
		},
		{
			expanded: false,
			key: 'dateCreated',
			label: 'Created Date',
			wrap: false,
		},
		{
			expanded: false,
			key: 'actions',
			label: '',
			wrap: false,
		},
	];
	const [state, dispatch] = useContext(Context);
	const [isDeletingLoading, setIsDeletingLoading] = useState(false);
	const openDesignerModal = (template) => {
		dispatch({
			payload: {
				body: <FolderStructureDesigner templateId={template.id} />,
				footer: [],
				header: 'Design Template',
				size: 'full-screen',
				status: 'info',
			},
			type: 1,
		});
	};
	const openCreateFolderModal = (template) => {
		try {
			dispatch({
				payload: {
					body: <TemplateItemCreateFolder templateID={template.id} />,
					center: 'middle',
					footer: [],
					header: 'Create Folder Structure',
					size: 'lg',
					status: 'info',
				},
				type: 1,
			});
		}
		catch (exp) {
			ApplicationUtil.ShowError(exp);
		}
	};
	const openNewItemModal = () => {
		dispatch({
			payload: {
				body: <NewTemplateItem onClose={closeNewItemModal} />,
				center: 'middle',
				footer: [],
				header: 'Create Folder Template',
				size: 'lg',
				status: 'info',
			},
			type: 1,
		});
	};
	const confirmDeleteItemModal = (template) => {
		const deleteTemplate = async () => {
			setIsDeletingLoading(true);
			await deleteFolderTemplateInformation(template.id);
			setIsDeletingLoading(false);
			reload();
			state.onClose();
		};
		dispatch({
			payload: {
				body:
					'Deleting an Template also removes its entries. This action is permanent and cannot be undone.',
				center: 'middle',
				footer: [
					'',
					'',
					<ClayButton
						disabled={isDeletingLoading}
						displayType="danger"
						key={3}
						onClick={() => {
							deleteTemplate();
						}}
					>
						{isDeletingLoading && (
							<ClayLoadingIndicator
								displayType="danger"
								size="sm"
							/>
						)}
						{'Delete'}
					</ClayButton>,
				],
				header: 'Delete Folder Template',
				size: 'lg',
				status: 'danger',
			},
			type: 1,
		});
	};
	const closeNewItemModal = (closeAndReload) => {
		if (closeAndReload) {
			reload();
		}
		state.onClose(true);
	};
	const reload = () => {
		setTotalItems(0);
		if (pageIndex === 1) {
			loadPage();
		}
		else {
			setPageIndex(1);
		}
	};
	const loadPage = async () => {
		const results = await getAvailableTemplatesPage(pageIndex, delta);
		setData(results.items);
		setTotalItems(results.totalCount);
	};

	const loadPageCallback = useCallback(async () => {
		const results = await getAvailableTemplatesPage(pageIndex, delta);
		setData(results.items);
		setTotalItems(results.totalCount);
	}, [pageIndex, delta]);

	useEffect(() => {
		const fetchData = async () => {
			await loadPageCallback();
		};

		fetchData();
	}, [loadPageCallback]);

	return (
		<>
			<ClayToolbar style={{marginBottom: '1rem'}}>
				<ClayToolbar.Nav>
					<ClayToolbar.Item className="text-left" expand>
						<ClayToolbar.Section>
							<label className="component-title">
								Folder Templates
							</label>
						</ClayToolbar.Section>
					</ClayToolbar.Item>
					<ClayToolbar.Item></ClayToolbar.Item>
					<ClayToolbar.Item>
						<ClayToolbar.Section>
							<ClayButtonGroup spaced={true}>
								<ClayButtonWithIcon
									aria-label="Reload"
									displayType="secondary"
									onClick={() => {
										reload();
									}}
									spritemap={spritemap}
									symbol="reload"
									title="Reload"
								/>
								<ClayButtonWithIcon
									aria-label="Create New"
									displayType="primary"
									onClick={() => {
										openNewItemModal();
									}}
									spritemap={spritemap}
									symbol="plus"
									title="Create New"
								/>
							</ClayButtonGroup>
						</ClayToolbar.Section>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			</ClayToolbar>
			{totalItems > 0 && (
				<>
					<Table>
						<Head items={Headers}>
							{(column) => (
								<Cell
									expanded={column.expanded}
									key={column.key}
									wrap={column.wrap}
								>
									{column.label}
								</Cell>
							)}
						</Head>

						<Body>
							{data &&
								data.map((row) => (
									<Row key={row['id']}>
										<Cell wrap={false}>{row['id']}</Cell>
										<Cell expanded={true} wrap={false}>
											{row['templateName']}
										</Cell>
										<Cell wrap={false}>
											{moment(row['dateCreated']).format(
												'MMMM D, YYYY'
											)}
										</Cell>
										<Cell textAlign="end" wrap={false}>
											<ClayButton.Group
												spaced={false}
												style={{minWidth: '150px'}}
											>
												<ClayButtonWithIcon
													aria-label="Create Folder Structure"
													displayType="default"
													onClick={() => {
														openCreateFolderModal(
															row
														);
													}}
													size="sm"
													spritemap={spritemap}
													symbol="folder"
													title="Create Folder Structure"
													translucent
												>
													Create Folder
												</ClayButtonWithIcon>
												<ClayButtonWithIcon
													aria-label="Design Template"
													displayType="default"
													onClick={() => {
														openDesignerModal(row);
													}}
													size="sm"
													spritemap={spritemap}
													symbol="diagram"
													title="Design Template"
													translucent
												>
													Design Template
												</ClayButtonWithIcon>
												<ClayButtonWithIcon
													aria-label="Delete Template"
													displayType="default"
													onClick={() => {
														confirmDeleteItemModal(
															row
														);
													}}
													size="sm"
													spritemap={spritemap}
													symbol="trash"
													title="Delete Template"
													translucent
												>
													Delete
												</ClayButtonWithIcon>
											</ClayButton.Group>
										</Cell>
									</Row>
								))}
						</Body>
					</Table>
					<ClayPaginationBarWithBasicItems
						activeDelta={delta}
						defaultActive={1}
						deltas={deltas}
						ellipsisBuffer={3}
						ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
						onActiveChange={(page) => {
							setPageIndex(page);
						}}
						onDeltaChange={(delta) => {
							setDelta(delta);
						}}
						spritemap={spritemap}
						totalItems={totalItems}
					/>
				</>
			)}
		</>
	);
};

export default TemplateList;
