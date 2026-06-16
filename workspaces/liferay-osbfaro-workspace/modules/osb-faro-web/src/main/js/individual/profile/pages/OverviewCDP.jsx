import ContextualInformation from '../components/ContextualInformation';
import ProfileCardCDP from '../hoc/ProfileCardCDP';
import React from 'react';
import {connect} from 'react-redux';

const Overview = ({channelId, groupId, individual, tabId, timeZoneId}) => (
	<div className='overview-column-main'>
		<ContextualInformation
			contactId={individual.get('id')}
			contextData={individual.get('context')}
			email={individual.getIn(['properties', 'email'])}
			userId={individual.getIn(['properties', 'userId'])}
			uuid={individual.getIn(['properties', 'uuid'])}
		/>

		<ProfileCardCDP
			channelId={channelId}
			entity={individual}
			groupId={groupId}
			tabId={tabId}
			timeZoneId={timeZoneId}
		/>
	</div>
);

export default connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}))(Overview);
