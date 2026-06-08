/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {sub} from '~/shared/util/lang';

class NoResultsDisplayKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<NoResultsDisplay title="data sources" />

				<hr />

				<NoResultsDisplay
					icon={{symbol: 'ac_individual'}}
					title={sub(Liferay.Language.get('there-are-no-x-found'), [
						Liferay.Language.get('individuals'),
					])}
				/>

				<hr />

				<NoResultsDisplay
					description="This is a description"
					icon={{symbol: 'ac_individual'}}
					title="No Results Title"
				/>

				<hr />

				<NoResultsDisplay
					description="This is a description"
					icon={{symbol: 'ac_individual'}}
					primary
					title="No Results Title"
				>
					<ClayButton className="button-root" displayType="secondary">
						Action Button
					</ClayButton>
				</NoResultsDisplay>

				<hr />

				<div className="align-items-center d-flex">
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'sm', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'md', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'lg', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
				</div>
				<div className="align-items-center d-flex">
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'xl', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'xxl', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
					<NoResultsDisplay
						description="This is a description"
						icon={{border: false, size: 'xxxl', symbol: 'home'}}
						title="No Resuls Title"
					>
						<ClayButton
							className="button-root"
							displayType="secondary"
							size="sm"
						>
							click
						</ClayButton>
					</NoResultsDisplay>
				</div>
			</div>
		);
	}
}

export default NoResultsDisplayKit;
