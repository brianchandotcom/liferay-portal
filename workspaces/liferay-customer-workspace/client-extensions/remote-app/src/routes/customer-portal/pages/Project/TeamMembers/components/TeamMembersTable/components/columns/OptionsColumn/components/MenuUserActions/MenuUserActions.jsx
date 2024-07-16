/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import i18n from '../../../../../../../../../../../../common/I18n';

const MenuUserActions = ({onCancel, onSave, saveDisabled}) => (
	<div className="align-items-center d-flex">
		<Button
			className="bg-white mr-2"
			displayType="secondary"
			onClick={onCancel}
			small
		>
			{i18n.translate('cancel')}
		</Button>

		<Button disabled={saveDisabled} onClick={onSave} small>
			{i18n.translate('save')}
		</Button>
	</div>
);

export default MenuUserActions;
