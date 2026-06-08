/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import autobind from 'autobind-decorator';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import Label from '~/shared/components/Label';
import SubnavTbar from '~/shared/components/SubnavTbar';
import {sub} from '~/shared/util/lang';

class StagedSubnav extends React.Component {
	static defaultProps = {
		onToggle: noop,
		showStaged: false,
		stagedMessage: sub(Liferay.Language.get('showing-only-selected-x'), [
			Liferay.Language.get('items').toLowerCase(),
		]),
	};

	static propTypes = {
		handleClearChecked: PropTypes.func,
		onToggle: PropTypes.func,
		onUndoChanges: PropTypes.func,
		selectedCountMessage: PropTypes.string.isRequired,
		showStaged: PropTypes.bool,
		stagedMessage: PropTypes.string,
		viewCurrentLinkText: PropTypes.string.isRequired,
		viewStagedLinkText: PropTypes.string.isRequired,
	};

	@autobind
	handleUndoChanges() {
		const {handleClearChecked, onUndoChanges} = this.props;

		onUndoChanges({handleClearChecked});
	}

	render() {
		const {
			className,
			onToggle,
			onUndoChanges,
			selectedCountMessage,
			showStaged,
			stagedMessage,
			viewCurrentLinkText,
			viewStagedLinkText,
		} = this.props;

		return (
			<SubnavTbar className={className}>
				<SubnavTbar.Item>
					{showStaged ? (
						<span>{stagedMessage}</span>
					) : (
						<Label display="success" size="lg" uppercase>
							{selectedCountMessage}
						</Label>
					)}
				</SubnavTbar.Item>

				<SubnavTbar.Item expand>
					<ClayButton
						borderless
						className="btn-link button-root"
						displayType="unstyled"
						onClick={onToggle}
					>
						{showStaged ? viewCurrentLinkText : viewStagedLinkText}
					</ClayButton>
				</SubnavTbar.Item>

				{showStaged && onUndoChanges && (
					<SubnavTbar.Item>
						<ClayButton
							borderless
							className="button-root"
							displayType="unstyled"
							onClick={this.handleUndoChanges}
						>
							{Liferay.Language.get('undo-all')}
						</ClayButton>
					</SubnavTbar.Item>
				)}
			</SubnavTbar>
		);
	}
}

export default StagedSubnav;
