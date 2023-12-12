/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import i18n from '../../../i18n';

type AssignToMeProps = {
	hidden?: boolean;
	onClick?: () => void;
};

const AssignToMe: React.FC<AssignToMeProps> = ({hidden, onClick}) => (
	<div
		className="tr-assign-to-me"
		hidden={hidden}
		onClick={onClick}
		title={i18n.translate('assign-to-me')}
	>
		<ClayIcon symbol="user-plus" />
	</div>
);

export default AssignToMe;
