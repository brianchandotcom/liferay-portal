/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useOutletContext} from 'react-router-dom';

import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {getSiteURL} from '../../../utils/site';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';

type ProductFooter = {
	primaryButtonProps: React.ButtonHTMLAttributes<HTMLButtonElement>;
	secondaryButtonProps?: {
		visible?: boolean;
	};
};

const ProductFooter: React.FC<ProductFooter> = ({
	primaryButtonProps = {},
	secondaryButtonProps = {visible: true},
}) => {
	const [
		{
			stepState: {onNext, onPrevious},
		},
	] = useGetAppContext();

	const {cartUtil} = useOutletContext<GetAppOutletContext>();

	return (
		<div className="mt-5 pt-2 text-black-50">
			<div className="d-flex justify-content-between">
				<ClayButton
					displayType={null}
					onClick={() => {
						if (cartUtil?.cart?.id) {
							cartUtil.removeCart(cartUtil.cart.id);
						}

						Liferay.Util.navigate(getSiteURL() || '/');
					}}
				>
					{i18n.translate('cancel')}
				</ClayButton>

				<div>
					{secondaryButtonProps.visible && (
						<ClayButton
							displayType="secondary"
							onClick={onPrevious}
						>
							{i18n.translate('back')}
						</ClayButton>
					)}

					<ClayButton
						className="ml-5"
						onClick={onNext}
						{...primaryButtonProps}
					/>
				</div>
			</div>
		</div>
	);
};

export default ProductFooter;
