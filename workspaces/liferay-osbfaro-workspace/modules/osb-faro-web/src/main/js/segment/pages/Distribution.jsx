/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {connect} from 'react-redux';
import Distribution from '~/contacts/components/Distribution';
import {fetchDistribution} from '~/shared/actions/distributions';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {Sizes} from '~/shared/util/constants';
import URLConstants from '~/shared/util/url-constants';

const SegmentDistribution = ({segment, ...otherProps}) => (
	<div className="container-fluid segment-distribution-root">
		<div className="row">
			<div className="col-xl-12">
				<Distribution
					distributionsKey={segment.id}
					knownIndividualCount={segment.knownIndividualCount}
					noResultsRenderer={() => (
						<NoResultsDisplay
							description={
								<>
									{Liferay.Language.get(
										'try-choosing-a-different-breakdown'
									)}

									<ClayLink
										className="d-block"
										href={
											URLConstants.SegmentsDistributionDocumentationLink
										}
										key="DOCUMENTATION"
										target="_blank"
									>
										{Liferay.Language.get(
											'learn-more-about-distribution'
										)}
									</ClayLink>
								</>
							}
							icon={{
								border: false,
								size: Sizes.XXXLarge,
								symbol: 'ac_satellite',
							}}
							title={Liferay.Language.get(
								'there-are-no-results-found'
							)}
						/>
					)}
					pageContainer
					{...otherProps}
				/>
			</div>
		</div>
	</div>
);

export default connect(null, {fetchDistribution})(SegmentDistribution);
