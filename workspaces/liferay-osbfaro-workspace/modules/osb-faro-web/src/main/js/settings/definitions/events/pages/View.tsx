/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import React from 'react';
import {connect} from 'react-redux';
import EVENT_DEFINITION_QUERY, {
	EventDefinitionData,
	EventDefinitionVariables,
	UPDATE_EVENT_DEFINITION,
} from '~/event-analysis/queries/EventDefinitionQuery';
import {Event} from '~/event-analysis/utils/types';
import BasePage from '~/settings/components/base-page/BasePage';
import {close, modalTypes, open} from '~/shared/actions/modals';
import {SafeResults} from '~/shared/hoc/util';
import {HasModal, Modal} from '~/shared/types';
import {getDefinitions, getEvents} from '~/shared/util/breadcrumbs';

import EventDetailsCard from '../components/EventDetailsCard';

interface IViewProps extends React.HTMLAttributes<HTMLElement>, HasModal {
	close: Modal.close;
	eventId: string;
	groupId: string;
	open: Modal.open;
}

const View: React.FC<IViewProps> = ({close, eventId, groupId, open}) => {
	const result = useQuery<EventDefinitionData, EventDefinitionVariables>(
		EVENT_DEFINITION_QUERY,
		{
			variables: {id: eventId},
		}
	);

	const viewEventPageActions = [
		{
			label: Liferay.Language.get('edit'),
			onClick: () =>
				open(modalTypes.EDIT_ATTRIBUTE_EVENT_MODAL, {
					id: eventId,
					mutation: UPDATE_EVENT_DEFINITION,
					onClose: close,
					query: EVENT_DEFINITION_QUERY,
				}),
		},
	];

	return (
		<SafeResults {...result}>
			{({
				eventDefinition: {
					description,
					displayName,
					eventAttributeDefinitions,
					name,
				},
			}: {
				eventDefinition: Event;
			}) => (
				<BasePage
					breadcrumbItems={[
						getDefinitions({groupId}),
						getEvents({groupId}),
						{active: true, label: displayName || name},
					]}
					pageActions={viewEventPageActions}
					pageDescription={
						description || Liferay.Language.get('no-description')
					}
					pageTitle={name}
					subTitle={displayName}
				>
					<EventDetailsCard
						eventAttributes={eventAttributeDefinitions ?? []}
						eventName={name}
						groupId={groupId}
					/>
				</BasePage>
			)}
		</SafeResults>
	);
};

export default connect(null, {close, open})(View);
