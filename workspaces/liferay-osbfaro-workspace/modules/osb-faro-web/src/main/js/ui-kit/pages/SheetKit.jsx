/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import Sheet from '~/shared/components/Sheet';

class SheetKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Sheet>
					<Sheet.Header divider>
						<Sheet.Title>Sheet Header</Sheet.Title>

						<Sheet.Text>
							Sheet text should be used for any kind of
							explanatory text. The Sheet Title and Sheet Text are
							contained inside a sheet-header.
						</Sheet.Text>
					</Sheet.Header>

					<Sheet.Body>
						<Sheet.Section divider>
							<Sheet.Subtitle>Sheet subtitle</Sheet.Subtitle>

							<Sheet.TertiaryTitle>
								Sheet tertiary title
							</Sheet.TertiaryTitle>

							<Sheet.Text>
								Sheet Subtitle and Sheet Tertiary Title are
								contained inside a sheet-section.
							</Sheet.Text>
						</Sheet.Section>

						<Sheet.Section>
							<Sheet.Subtitle>Sheet subtitle</Sheet.Subtitle>

							<Sheet.TertiaryTitle>
								Sheet tertiary title
							</Sheet.TertiaryTitle>

							<Sheet.Text>
								Sheet Subtitle and Sheet Tertiary Title are
								contained inside a sheet-section.
							</Sheet.Text>
						</Sheet.Section>
					</Sheet.Body>

					<Sheet.Footer>
						<ClayButton displayType="primary">Primary</ClayButton>

						<ClayButton
							className="button-root"
							displayType="secondary"
						>
							Secondary
						</ClayButton>
					</Sheet.Footer>
				</Sheet>
			</div>
		);
	}
}

export default SheetKit;
