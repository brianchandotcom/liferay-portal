/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import DataTransformation, {
	processFieldMappings,
} from '~/settings/components/DataTransformation';
import BasePage from '~/settings/components/base-page/BasePage';
import {addAlert} from '~/shared/actions/alerts';
import {close, open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import Sheet from '~/shared/components/Sheet';
import TextTruncate from '~/shared/components/TextTruncate';
import {compose, withAdminPermission} from '~/shared/hoc';
import {Alert} from '~/shared/types';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {sub} from '~/shared/util/lang';
import {hasChanges} from '~/shared/util/react';
import {DataSource} from '~/shared/util/records';
import {UNAUTHORIZED_ACCESS} from '~/shared/util/request';
import {Routes, toRoute} from '~/shared/util/router';

class ConfigureCSV extends React.Component {
	static propTypes = {
		addAlert: PropTypes.func.isRequired,
		close: PropTypes.func.isRequired,
		dataSource: PropTypes.instanceOf(DataSource),
		fileVersionId: PropTypes.oneOfType([
			PropTypes.string,
			PropTypes.number,
		]),
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired,
		id: PropTypes.string,
		name: PropTypes.string,
		open: PropTypes.func.isRequired,
	};

	state = {
		name: '',
		submitting: false,
	};

	constructor(props) {
		super(props);

		const {dataSource, name} = this.props;

		this.state = {
			...this.state,
			name: dataSource ? dataSource.name : name,
		};
	}

	componentDidUpdate(prevProps) {
		if (hasChanges(prevProps, this.props, 'dataSource')) {
			const {name} = this.props.dataSource;

			this.setState({name});
		}
	}

	@autobind
	handleCreateCSVSource(fieldMappings) {
		const {
			props: {addAlert, fileVersionId, groupId, history, id},
			state: {name},
		} = this;

		this.setState({
			submitting: true,
		});

		const identifier = fileVersionId ? {fileVersionId} : {id};

		const data = {
			fieldMappingMaps: processFieldMappings(fieldMappings),
			groupId,
			name,
			...identifier,
		};

		const request = id
			? API.dataSource.updateCSV
			: API.dataSource.createCSV;

		request(data)
			.then((response) => {
				history.push(
					toRoute(Routes.SETTINGS_DATA_SOURCE, {
						groupId,
						id: response.id,
					})
				);
			})
			.catch((error) => {
				addAlert({
					alertType: Alert.Types.Error,
					message:
						error.message === UNAUTHORIZED_ACCESS
							? Liferay.Language.get('unauthorized-access')
							: Liferay.Language.get('error'),
				});

				this.setState({
					submitting: false,
				});
			});
	}

	render() {
		const {
			props: {className, fileVersionId, groupId, id},
			state: {name, submitting},
		} = this;

		const breadcrumbItems = id
			? [
					breadcrumbs.getEntityName({
						active: false,
						href: toRoute(Routes.SETTINGS_DATA_SOURCE, {
							groupId,
							id,
						}),
						label: name,
					}),
				]
			: [];

		return (
			<BasePage
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					...breadcrumbItems,
					{
						active: true,
						label: Liferay.Language.get('configure-csv'),
					},
				]}
				className={getCN('csv-root', className)}
				documentTitle={name || Liferay.Language.get('csv-data-source')}
				groupId={groupId}
			>
				<Sheet className="wizard">
					<DataTransformation
						cancelHref={
							id
								? toRoute(Routes.SETTINGS_DATA_SOURCE, {
										groupId,
										id,
									})
								: ''
						}
						fileVersionId={fileVersionId}
						groupId={groupId}
						id={id}
						key="DATA_TRANSFORMATION"
						name={name}
						navigationWarning
						onSubmit={this.handleCreateCSVSource}
						sourceFieldPlaceholder={Liferay.Language.get(
							'select-field-from-csv-file'
						)}
						sourceTitle={Liferay.Language.get('csv-data-model')}
						submitting={submitting}
						title={sub(
							Liferay.Language.get('map-x-csv-data'),
							[
								<TextTruncate
									inline
									key="NAME"
									maxCharLength={50}
									title={name}
								/>,
							],
							false
						)}
					/>
				</Sheet>
			</BasePage>
		);
	}
}

export {ConfigureCSV as ConfigureCSVTesting};

const ConnectedConfigureCSV = connect(null, {addAlert, close, open})(
	ConfigureCSV
);
export {ConnectedConfigureCSV as ConfigureCSV};

export default compose(
	withAdminPermission,
	connect(null, {addAlert, close, open})
)(ConfigureCSV);
