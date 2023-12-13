/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './template-list-item.css';

import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import moment from 'moment/moment';
import React, {useState} from 'react';

import {deleteFolderTemplateInformation} from '../../../../services/template-list.service';
import {ApplicationUtil} from '../../../../utils/appUtil';
const spritemap = ApplicationUtil.getDefaultSpriteMap();
const TemplateItem = (props) => {
	const item = props.item;
	const onDeleteSuccess = props.onDelete;
	const openDesigner = props.openDesigner;
	const openCreateFolder = props.openCreateFolder;
	const [isLoading, setIsLoading] = useState(false);

	const deleteTemplateAction = async () => {
		setIsLoading(true);
		try {
			await deleteFolderTemplateInformation(item.id);
			setIsLoading(false);
			onDeleteSuccess();
		}
		catch (error) {
			setIsLoading(false);
		}
	};

	return (
		<ClayList.Item flex>
			<ClayList.ItemField className="justify-center">
				{item.id}
			</ClayList.ItemField>
			<ClayList.ItemField expand>
				<ClayList.ItemTitle>
					<ClayLayout.Row justify="between">
						<ClayLayout.Col expand={true}>
							{item.templateName},{' '}
							{moment(item.dateCreated).format('MMMM D, YYYY')}
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayList.ItemTitle>
				{item.templateDescription &&
					!!item.templateDescription.length && (
						<ClayList.ItemText>
							{item.templateDescription}
						</ClayList.ItemText>
					)}
			</ClayList.ItemField>
			<ClayList.ItemField>
				<ClayList.QuickActionMenu>
					<ClayList.QuickActionMenu.Item
						aria-label="Delete"
						disabled={isLoading}
						onClick={() => {
							openCreateFolder(item);
						}}
						spritemap={spritemap}
						symbol="folder"
						title="Delete"
					/>
					<ClayList.QuickActionMenu.Item
						aria-label="Delete"
						disabled={isLoading}
						onClick={() => {
							openDesigner(item);
						}}
						spritemap={spritemap}
						symbol="diagram"
						title="Delete"
					/>
					<ClayList.QuickActionMenu.Item
						aria-label="Settings"
						disabled={isLoading}
						onClick={deleteTemplateAction}
						spritemap={spritemap}
						symbol="trash"
						title="Settings"
					/>
				</ClayList.QuickActionMenu>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};
export default TemplateItem;
