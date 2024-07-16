/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../common/I18n';
import ActivationKeysInputs from './Inputs';
import ActivationKeysSkeleton from './Skeleton';

const ActivationKeysLayout = ({children}) => {
	return (
		<div className="mr-4">
			<h1 className="m-0 py-4">{i18n.translate('activation-keys')}</h1>

			{children}
		</div>
	);
};

ActivationKeysLayout.Inputs = ActivationKeysInputs;
ActivationKeysLayout.Skeleton = ActivationKeysSkeleton;

export default ActivationKeysLayout;
