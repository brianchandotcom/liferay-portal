/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Flex, Form} from 'antd';
import React, {useState} from 'react';

import './template-item-create.css';
import {postFolderTemplateInformation} from '../../../../services/template-list.service';
import {ApplicationUtil} from '../../../../utils/appUtil';

const NewTemplateItem = ({onClose}) => {
	const [isLoading, setIsLoading] = useState(false);
	const [form] = Form.useForm();

	const handleSubmit = async () => {
		form.validateFields()
			.then(
				async (values) => {
					try {
						setIsLoading(true);
						await postFolderTemplateInformation(values);
						ApplicationUtil.ShowSuccess('Template created!');
						onClose(true);
					}
					catch (error) {
						ApplicationUtil.ShowError(error.message);
						onClose(false);
					}
					finally {
						form.resetFields();
						setIsLoading(false);
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
		<Form autoComplete="off" form={form} layout="vertical">
			<Form.Item
				label="Title"
				name="templateName"
				rules={[
					{message: 'Please provide a template name', required: true},
				]}
			>
				<ClayInput />
			</Form.Item>
			<Form.Item label="Description" name="templateDescription">
				<ClayInput component="textarea" type="text" />
			</Form.Item>
			<Form.Item>
				<Flex gap={6}>
					{!isLoading && (
						<ClayButton
							onClick={() => {
								handleSubmit();
							}}
						>
							Submit
						</ClayButton>
					)}
					{isLoading && (
						<ClayLoadingIndicator
							displayType="secondary"
							size="sm"
						/>
					)}
				</Flex>
			</Form.Item>
		</Form>
	);
};

export default NewTemplateItem;
