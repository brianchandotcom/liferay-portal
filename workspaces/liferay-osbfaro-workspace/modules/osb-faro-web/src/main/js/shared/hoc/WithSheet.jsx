/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Sheet from '~/shared/components/Sheet';

/**
 * Wraps a component with a Sheet.
 * @param sheetParams - Props to be passed to the Sheet Component.
 * @returns {function} - The Sheet hoc with applied params.
 */
export default function WithSheet(sheetParams = {}) {
	return (WrappedComponent) =>
		class extends React.Component {
			render() {
				return (
					<Sheet
						{...sheetParams}
						className={
							this.props.className
								? ` ${this.props.className}`
								: ''
						}
					>
						<WrappedComponent {...this.props} />
					</Sheet>
				);
			}
		};
}
