/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ComponentProps} from 'react';

import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {getSiteURL} from '../../../utils/site';

type ProductPurchaseFooterProps = {
	backButtonProps?: ComponentProps<typeof ClayButton>;
	cancelButtonProps?: ComponentProps<typeof ClayButton>;
	continueButtonProps?: ComponentProps<typeof ClayButton>;
};

const ProductPurchaseFooter: React.FC<ProductPurchaseFooterProps> = ({
	backButtonProps,
	cancelButtonProps,
	continueButtonProps,
}) => {
	return (
		<div className="align-items-center d-flex justify-content-between mt-6 w-100">
			<ClayButton
				className="font-weight-bold"
				displayType="unstyled"
				onClick={() => Liferay.Util.navigate(getSiteURL())}
				{...cancelButtonProps}
			>
				{cancelButtonProps?.children || i18n.translate('cancel')}
			</ClayButton>

			<div>
				<ClayButton displayType="secondary" {...backButtonProps}>
					{backButtonProps?.children || i18n.translate('back')}
				</ClayButton>

				<ClayButton className="ml-4" {...continueButtonProps}>
					{continueButtonProps?.children ||
						i18n.translate('continue')}
				</ClayButton>
			</div>
		</div>
	);
};

export default ProductPurchaseFooter;
