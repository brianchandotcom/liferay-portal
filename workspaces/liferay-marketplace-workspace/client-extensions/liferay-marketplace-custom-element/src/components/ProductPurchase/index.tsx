/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';

import Body from './Body';
import Feedback from './Feedback';
import Footer from './Footer';
import Header, {ProductPurchaseHeaderAccount as HeaderAccount} from './Header';
import Shell from './Shell';
import Steps from './Steps';

import './index.scss';

type ProductPurchaseProps = {
	children: ReactNode;
} & React.HTMLAttributes<HTMLDivElement>;

type ProductPurchaseChildrens = {
	Body: typeof Body;
	Feedback: typeof Feedback;
	Footer: typeof Footer;
	Header: typeof Header;
	HeaderAccount: typeof HeaderAccount;
	Shell: typeof Shell;
	Steps: typeof Steps;
};

const ProductPurchase: React.FC<ProductPurchaseProps> &
	ProductPurchaseChildrens = ({children, className, ...props}) => (
	<div
		{...props}
		className={classNames('container', className)}
		style={{width: 630}}
	>
		{children}
	</div>
);

ProductPurchase.Body = Body;
ProductPurchase.Feedback = Feedback;
ProductPurchase.Footer = Footer;
ProductPurchase.Header = Header;
ProductPurchase.HeaderAccount = HeaderAccount;
ProductPurchase.Shell = Shell;
ProductPurchase.Steps = Steps;

export default ProductPurchase;
