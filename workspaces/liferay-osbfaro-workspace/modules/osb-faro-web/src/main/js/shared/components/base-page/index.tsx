/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import DocumentTitle from '~/shared/components/DocumentTitle';
import MaintenanceAlert from '~/shared/components/MaintenanceAlert';

import Body from './Body';
import Context from './Context';
import Header from './Header';
import Row from './Row';
import SubHeader from './SubHeader';

interface IBasePageProps extends React.HTMLAttributes<HTMLElement> {
	documentTitle: string;
	fluid?: boolean;
}

export const BasePage: React.FC<IBasePageProps> & {
	Body: typeof Body;
	Context: typeof Context;
	Header: typeof Header;
	Row: typeof Row;
	SubHeader: typeof SubHeader;
} = function BasePage({children, className, documentTitle}) {
	return (
		<div className={getCN('index-root', className)}>
			<DocumentTitle title={documentTitle} />
			<MaintenanceAlert stripe />
			{children}
		</div>
	);
};

BasePage.Body = Body;
BasePage.Context = Context;
BasePage.Header = Header;
BasePage.Row = Row;
BasePage.SubHeader = SubHeader;

export default BasePage;
