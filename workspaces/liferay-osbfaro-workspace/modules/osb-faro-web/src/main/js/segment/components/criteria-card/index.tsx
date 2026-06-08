/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Panel from '@clayui/panel';
import React, {useContext, useEffect, useMemo} from 'react';
import {ReferencedObjectsContext} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {extractRemoteCriterionEntries} from '~/segment/segment-editor/dynamic/criterion-types/extract';
import {translateQueryToCriteria} from '~/segment/segment-editor/dynamic/utils/odata';
import Label from '~/shared/components/Label';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {useDownloadReportContext} from '~/shared/components/download-report/DownloadReportContext';
import {SegmentTypes} from '~/shared/util/constants';

import CriteriaView from './CriteriaView';

interface ICriteriaCardProps {
	channelId?: string;
	criteriaString: string;
	groupId?: string;
	includeAnonymousUsers: boolean;
	segmentType: SegmentTypes;
	sequential: boolean;
	timeZoneId: string;
}

const CriteriaCard: React.FC<ICriteriaCardProps> = ({
	channelId,
	criteriaString,
	groupId,
	includeAnonymousUsers,
	segmentType,
	sequential,
	timeZoneId,
}) => {
	const _criteriaViewRef = React.createRef<HTMLDivElement>();

	const {clearReportContainers, setReportContainer} =
		useDownloadReportContext();

	const {addProperty} = useContext(ReferencedObjectsContext);

	const criteria = useMemo(
		() => translateQueryToCriteria(criteriaString),
		[criteriaString]
	);

	useEffect(() => {
		setReportContainer(ReportContainer.SegmentCriteriaCard);

		return clearReportContainers;

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (!channelId || !groupId || !addProperty) {
			return;
		}

		extractRemoteCriterionEntries(criteria).forEach(
			({criterionType, id, name}) => {
				addProperty(criterionType.createProperty({id, name}));
			}
		);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [channelId, groupId, criteria]);

	return (
		<Panel
			className="card-root"
			collapsable
			defaultExpanded
			displayTitle={
				<Panel.Title className="card-title">
					{Liferay.Language.get('segment-criteria')}
				</Panel.Title>
			}
			id={ReportContainer.SegmentCriteriaCard}
		>
			<Panel.Body className="criteria-card-root">
				{includeAnonymousUsers && (
					<Label display="info" size="lg" uppercase>
						{Liferay.Language.get('includes-anonymous-individuals')}
					</Label>
				)}

				{segmentType === SegmentTypes.RealTime && sequential && (
					<Label display="info" size="lg" uppercase>
						{Liferay.Language.get('sequential-events')}
					</Label>
				)}

				{criteria && (
					<CriteriaView
						criteria={criteria}
						ref={_criteriaViewRef}
						segmentType={segmentType}
						sequential={sequential}
						timeZoneId={timeZoneId}
					/>
				)}
			</Panel.Body>
		</Panel>
	);
};

export default CriteriaCard;
