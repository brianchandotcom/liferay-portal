/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import Body from './Body';
import Feedback from './Feedback';
import Footer from './Footer';
import Header from './Header';
import Shell from './Shell';
import Steps from './Steps';

type ProductPurchaseChildrens = {
	Body: typeof Body;
	Feedback: typeof Feedback;
	Footer: typeof Footer;
	Header: typeof Header;
	Shell: typeof Shell;
	Steps: typeof Steps;
};

const ProductPurchase: React.FC<{children: ReactNode}> &
	ProductPurchaseChildrens = ({children}) => {
	return (
		<div className="container my-7" style={{width: 600}}>
			{children}
		</div>
	);
};

ProductPurchase.Body = Body;
ProductPurchase.Feedback = Feedback;
ProductPurchase.Footer = Footer;
ProductPurchase.Header = Header;
ProductPurchase.Steps = Steps;
ProductPurchase.Shell = Shell;

export default ProductPurchase;
