import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import DownloadPDFReport from 'shared/components/download-report/DownloadPDFReport';
import React from 'react';
import TitleEditor from 'shared/components/TitleEditor';
import {Routes, toRoute} from 'shared/util/router';
import {useChannelContext} from 'shared/context/channel';
import {useDataSources} from 'shared/context/dataSources';
import {useParams} from 'react-router-dom';

interface IEventAnalysisToolbarProps extends React.HTMLAttributes<HTMLElement> {
	isValid: boolean;
}

const EventAnalysisToolbar: React.FC<IEventAnalysisToolbarProps> = ({
	isValid,
}) => {
	const dataSourceStates = useDataSources();

	const {channelId, groupId} = useParams();

	const {selectedChannel} = useChannelContext();

	return (
		<div className="event-analysis-toolbar-root">
			<div className="event-analysis-toolbar-left-content">
				<TitleEditor
					name="name"
					placeholder={Liferay.Language.get('unnamed-analysis')}
				/>
			</div>

			<div className="d-flex event-analysis-toolbar-right-content">
				<div className="event-analysis-download-report mr-2">
					<DownloadPDFReport
						disabled={!!dataSourceStates.empty}
						infoMessage={Liferay.Language.get(
							'the-report-will-be-downloaded-exactly-as-it-is-displayed-on-your-screen.-please-verify-if-the-desired-tabs-and-filters-are-selected-before-proceeding'
						)}
						subtitle={selectedChannel?.name}
						title={Liferay.Language.get('event-analysis-report')}
					/>
				</div>

				<ClayButton
					className="button-root ml-1 mr-2"
					disabled={!isValid}
					displayType="primary"
					size="sm"
					type="submit"
				>
					{Liferay.Language.get('save-analysis')}
				</ClayButton>

				<ClayLink
					button
					className="button-root"
					displayType="secondary"
					href={toRoute(Routes.EVENT_ANALYSIS, {
						channelId,
						groupId,
					})}
					small
				>
					{Liferay.Language.get('cancel')}
				</ClayLink>
			</div>
		</div>
	);
};

export default EventAnalysisToolbar;
