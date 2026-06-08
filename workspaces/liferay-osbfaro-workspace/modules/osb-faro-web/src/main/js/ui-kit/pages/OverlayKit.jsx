/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import Overlay from '~/shared/components/Overlay';
import DatePicker from '~/shared/components/date-picker';

import Row from '../components/Row';

class OverlayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Overlay>
						<ClayButton
							className="button-root"
							displayType="secondary"
						>
							Hover Me
						</ClayButton>

						<DatePicker />
					</Overlay>
				</Row>

				<Row>
					<Overlay>
						<ClayButton
							className="button-root"
							displayType="secondary"
						>
							Nested
						</ClayButton>

						<Overlay>
							<ClayButton
								className="button-root"
								displayType="secondary"
							>
								Nested 2
							</ClayButton>

							<Overlay>
								<ClayButton
									className="button-root"
									displayType="secondary"
								>
									Hover Me
								</ClayButton>

								<DatePicker />
							</Overlay>
						</Overlay>
					</Overlay>
				</Row>
			</div>
		);
	}
}

export default OverlayKit;
