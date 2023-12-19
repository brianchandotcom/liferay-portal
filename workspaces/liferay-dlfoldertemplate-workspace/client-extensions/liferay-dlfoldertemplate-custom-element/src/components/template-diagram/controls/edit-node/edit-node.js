/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	CloseCircleTwoTone,
	LoadingOutlined,
	SaveTwoTone,
} from '@ant-design/icons';
import {ClayInput} from '@clayui/form';
import {Button, Flex, Form, Input} from 'antd';
import React, {useState} from 'react';

import {updateFolderTemplate} from '../../../../services/template-diagram.service';
import {ApplicationUtil} from '../../../../utils/util';

const EditNode = ({
	chart,
	description,
	name,
	nodeId,
	onClose,
	onNodeUpdate,
	parentID,
	root,
	templateID,
}) => {
	const [form] = Form.useForm();

	const [isLoading, setIsLoading] = useState(false);

	const handleSubmit = async (values) => {
		try {
			setIsLoading(true);

			values['root'] = root;

			await updateFolderTemplate(nodeId, values);

			setIsLoading(false);

			onNodeUpdate(chart, values);
		}
		catch (error) {
			setIsLoading(false);

			ApplicationUtil.ShowError(error.message);
		}
	};

	return (
		<>
			<Form
				autoComplete="off"
				form={form}
				layout="vertical"
				onFinish={handleSubmit}
			>
				<Form.Item
					initialValue={name}
					label="Title"
					name="name"
					rules={[
						{
							message: 'Please provide node name.',
							required: true,
						},
					]}
				>
					<ClayInput />
				</Form.Item>
				<Form.Item
					initialValue={description}
					label="Description"
					name="description"
				>
					<ClayInput component="textarea" type="text" />
				</Form.Item>
				<Form.Item
					hidden={true}
					initialValue={parentID}
					label="parentID"
					name="parentID"
					rules={[
						{
							message: 'Please provide node parent id.',
							required: true,
						},
					]}
				>
					<Input />
				</Form.Item>
				<Form.Item
					hidden={true}
					initialValue={templateID}
					label="templateID"
					name="templateID"
					rules={[
						{
							message: 'Please provide a template id.',
							required: true,
						},
					]}
				>
					<Input />
				</Form.Item>
				<Form.Item
					hidden={true}
					initialValue={root}
					label="root"
					name="root"
					rules={[
						{
							required: true,
						},
					]}
				>
					<Input />
				</Form.Item>
				<Form.Item
					hidden={true}
					initialValue={nodeId}
					label="root"
					name="id"
					rules={[
						{
							required: true,
						},
					]}
				>
					<Input />
				</Form.Item>
				<Form.Item>
					<Flex gap={6}>
						<Button
							disabled={isLoading}
							htmlType="submit"
							icon={
								isLoading ? (
									<LoadingOutlined
										spin
										style={{fontSize: 14}}
									/>
								) : (
									<SaveTwoTone />
								)
							}
						>
							{isLoading ? 'Saving' : 'Save'}
						</Button>
						<Button icon={<CloseCircleTwoTone />} onClick={onClose}>
							Cancel
						</Button>
					</Flex>
				</Form.Item>
			</Form>
		</>
	);
};

export default EditNode;
