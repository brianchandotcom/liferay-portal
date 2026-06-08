/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import {Redirect} from 'react-router';
import {addAlert} from '~/shared/actions/alerts';
import {
	configureProject,
	createProject,
	createTrialProject,
} from '~/shared/actions/projects';
import AddWorkspaceForm from '~/shared/components/workspaces/AddWorkspaceForm';
import WorkspacesBasePage from '~/shared/components/workspaces/BasePage';
import {compose, optional, redirectIf, withProject} from '~/shared/hoc';
import {Alert} from '~/shared/types';
import {DataSourceStates} from '~/shared/util/constants';
import {Routes, toRoute} from '~/shared/util/router';

import {Project} from '../util/records';

export const routingFn = function routingFn({project}) {
	if (
		project &&
		project.groupId &&
		project.state !== DataSourceStates.Unconfigured
	) {
		return toRoute(Routes.WORKSPACE_WITH_ID, {groupId: project.groupId});
	}
	else {
		return null;
	}
};

export class AddWorkspace extends React.Component {
	state = {
		redirectToWorkspace: false,
	};

	static propTypes = {
		addAlert: PropTypes.func,
		createProject: PropTypes.func,
		history: PropTypes.object.isRequired,
		project: PropTypes.instanceOf(Project),
	};

	@autobind
	handleSubmit({
		emailAddressDomains,
		friendlyURL,
		incidentReportEmailAddresses,
		name,
		serverLocation,
		timeZoneId,
	}) {
		const {
			addAlert,
			configureProject,
			corpProjectUuid,
			createProject,
			createTrialProject,
			project: {groupId, state} = {},
		} = this.props;

		const params = {
			emailAddressDomains,
			friendlyURL: friendlyURL && `/${friendlyURL}`,
			incidentReportEmailAddresses,
			name,
			timeZoneId,
			...(state === DataSourceStates.Unconfigured
				? {groupId}
				: {corpProjectUuid, serverLocation}),
		};

		const createFn =
			state === DataSourceStates.Unconfigured
				? configureProject
				: corpProjectUuid
					? createProject
					: createTrialProject;

		return createFn(params)
			.then(({payload: {friendlyURL, groupId}}) => {
				this.setState({
					friendlyURL: friendlyURL
						? friendlyURL.replace('/', '')
						: groupId,
					redirectToWorkspace: true,
				});

				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get('success'),
				});
			})
			.catch((error) => {
				if (!error.field) {
					addAlert({
						alertType: Alert.Types.Error,
						message: error.message,
						timeout: false,
					});
				}

				return Promise.reject(error);
			});
	}

	render() {
		const {
			props: {className, project},
			state: {friendlyURL, redirectToWorkspace},
		} = this;

		return (
			<div
				className={getCN('add-workspace-root', className)}
				key="AddWorkspace"
			>
				{redirectToWorkspace ? (
					<Redirect
						to={toRoute(Routes.WORKSPACE_WITH_ID, {
							groupId: friendlyURL,
						})}
					/>
				) : (
					<WorkspacesBasePage
						title={Liferay.Language.get('create-workspace')}
					>
						<AddWorkspaceForm
							onSubmit={this.handleSubmit}
							project={project}
						/>
					</WorkspacesBasePage>
				)}
			</div>
		);
	}
}

export default compose(
	connect(null, {
		addAlert,
		configureProject,
		createProject,
		createTrialProject,
	}),
	optional(withProject, {idPropName: 'corpProjectUuid'}),
	redirectIf(routingFn)
)(AddWorkspace);
