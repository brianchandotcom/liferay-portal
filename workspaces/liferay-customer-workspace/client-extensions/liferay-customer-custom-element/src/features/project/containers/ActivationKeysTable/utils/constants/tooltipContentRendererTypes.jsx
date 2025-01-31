/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TOOLTIP_CLASSNAMES_TYPES} from './tooltipClassnamesTypes';
import i18n from '~/utils/I18n';

export const TOOLTIP_CONTENT_RENDERER_TYPES = {
	[TOOLTIP_CLASSNAMES_TYPES.dropDownItem]: (
		<p className="m-0"
			dangerouslySetInnerHTML={{
				__html: i18n.sub(
					'to-download-an-aggregate-key-select-keys-for-a-valid-liferay-version-with-identical-type-start-date-end-date-and-instance-size-to-learn-more-click-x-here-x',
					['<a href="https://help.liferay.com/hc/articles/8835990270989-How-do-I-download-my-Liferay-DXP-Portal-Activation-Keys" target="_blank">', '</a>']
				)
			}}
		/>
	),
};
