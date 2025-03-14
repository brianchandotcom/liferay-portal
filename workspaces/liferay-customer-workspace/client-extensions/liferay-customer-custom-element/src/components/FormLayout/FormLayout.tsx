/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import FormLayoutFooter from './components/FormLayoutFooter';
import FormLayoutHeader from './components/FormLayoutHeader';

import './FormLayout.css';
interface IProps {
	children: React.ReactNode;
	className?: string;
	footerProps?: React.PropsWithChildren<any>;
	headerProps?: {
		button?: React.ReactNode;
		greetings?: string;
		headerClass?: string;
		helper?: string;
		title: string;
	};
	headerSkeleton?: React.ReactNode;
	layoutType?: string;
}

const FormLayout: React.FC<IProps> = ({
	children,
	className,
	footerProps,
	headerProps,
	headerSkeleton,
	layoutType = 'onboarding',
}) => (
	<div
		className={classNames(
			'border d-flex flex-column mx-auto overflow-auto rounded-lg shadow-lg',
			layoutType
		)}
	>
		{headerProps ? <FormLayoutHeader {...headerProps} /> : headerSkeleton}

		<main className={classNames('flex-grow-1 overflow-auto', className)}>
			{children}
		</main>

		{footerProps && <FormLayoutFooter {...footerProps} />}
	</div>
);
export default FormLayout;
