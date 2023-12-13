/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './template-list-item-header.css';

import {Col, Row} from 'antd';
import React from 'react';

const TemplateItemHeader = () => {
	return (
		<Row className="header template-item">
			<Col span={2}>ID</Col>
			<Col span={10}>Title</Col>
			<Col span={6}>Created Date</Col>
			<Col className="justify-end" span={6}>
				Actions
			</Col>
		</Row>
	);
};

export default TemplateItemHeader;
