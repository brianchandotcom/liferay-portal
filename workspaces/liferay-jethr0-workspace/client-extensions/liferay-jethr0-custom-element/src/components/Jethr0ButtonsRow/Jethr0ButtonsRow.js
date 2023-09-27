/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {Link} from 'react-router-dom';

import './Jethr0ButtonsRow.css';

function Jethr0ButtonsRow({buttons}) {
	return (
		<ClayLayout.Row className="jethr0-buttons-row" justify="end">
			{buttons.map((button) => {
				let displayType = 'primary';

				if (button.displayType !== null) {
					displayType = button.displayType;
				}

				return (
					<Link
						key={button.title}
						title={button.title}
						to={button.link}
					>
						<ClayButton
							displayType={displayType}
							onClick={button.onClick}
						>
							{button.title}
						</ClayButton>
					</Link>
				);
			})}
		</ClayLayout.Row>
	);
}

export default Jethr0ButtonsRow;
