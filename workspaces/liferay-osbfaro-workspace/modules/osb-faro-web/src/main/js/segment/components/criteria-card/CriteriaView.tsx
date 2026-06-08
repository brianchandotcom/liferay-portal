/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Fragment, useContext} from 'react';
import {ReferencedObjectsContext} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {Criteria} from '~/segment/segment-editor/dynamic/utils/types';
import {findPropertyByCriterion} from '~/segment/segment-editor/dynamic/utils/utils';
import {ConjunctionKey, SegmentTypes} from '~/shared/util/constants';

import DisplayComponent from './display-components';

interface ICriteriaViewProps extends React.HTMLAttributes<HTMLDivElement> {
	criteria: Criteria;
	forwardedRef?: React.Ref<any>;
	segmentType: SegmentTypes;
	sequential: boolean;
	timeZoneId: string;
}

const CONJUNCTION_MAP: Record<string, string> = {
	[ConjunctionKey.And]: Liferay.Language.get('and'),
	[ConjunctionKey.Or]: Liferay.Language.get('or'),
	[ConjunctionKey.Then]: Liferay.Language.get('then'),
};

const CriteriaView: React.FC<ICriteriaViewProps> = ({
	criteria,
	forwardedRef,
	segmentType,
	sequential,
	timeZoneId,
}) => {
	const {referencedProperties} = useContext(ReferencedObjectsContext);

	const renderCriteriaRow = (criterion: any) => {
		const property = findPropertyByCriterion(
			criterion,
			referencedProperties
		);

		return (
			<div className="criteria-row">
				{property ? (
					<DisplayComponent
						criterion={criterion}
						property={property}
						segmentType={segmentType}
						timeZoneId={timeZoneId}
					/>
				) : (
					<b className="undefined-property">
						{Liferay.Language.get('attribute-no-longer-exists')}
					</b>
				)}
			</div>
		);
	};

	const renderCriteriaGroup = (criteria: Criteria, depth: number) => {
		const {conjunctionName, criteriaGroupId, items} = criteria as {
			conjunctionName: string;
			criteriaGroupId: string;
			items: any[];
		};

		const isSequentialTopLevel =
			segmentType === SegmentTypes.RealTime && depth === 0 && sequential;

		const conjunction = isSequentialTopLevel
			? CONJUNCTION_MAP[ConjunctionKey.Then]
			: CONJUNCTION_MAP[conjunctionName];

		return (
			<div className="criteria-group" key={criteriaGroupId}>
				{items.map((criterion: any, index: number) => {
					const content = criterion.items
						? renderCriteriaGroup(criterion, depth + 1)
						: renderCriteriaRow(criterion);

					return (
						<Fragment key={index}>
							{index !== 0 && (
								<div className="conjunction">{conjunction}</div>
							)}

							{isSequentialTopLevel ? (
								<div className="criteria-step">
									<span className="criteria-step-number mr-2">
										{index + 1}
									</span>

									{content}
								</div>
							) : (
								content
							)}
						</Fragment>
					);
				})}
			</div>
		);
	};

	return (
		<div className="criteria-view-root pt-2" ref={forwardedRef}>
			{renderCriteriaGroup(criteria, 0)}
		</div>
	);
};

export default React.forwardRef<HTMLDivElement, ICriteriaViewProps>(
	(props, ref) => <CriteriaView forwardedRef={ref} {...props} />
);
