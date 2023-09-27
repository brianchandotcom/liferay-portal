/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {CSSTransition} from 'react-transition-group';

interface IProps {
	children?: React.ReactNode;
	expanded: boolean;
}

const PanelBody = ({children, expanded}: IProps) => (
	<CSSTransition
		className={classNames('panel-collapse border-top', {
			collapse: !expanded,
		})}
		classNames={{
			enter: 'collapsing',
			enterActive: `show`,
			enterDone: 'show',
			exit: `show`,
			exitActive: 'collapsing',
		}}
		in={expanded}
		onEnter={(element: HTMLElement) =>
			element.setAttribute('style', `height: 0px`)
		}
		onEntering={(element: HTMLElement) =>
			element.setAttribute('style', `height: 100%`)
		}
		onExit={(element) => element.setAttribute('style', `height: 100%`)}
		onExiting={(element) => element.setAttribute('style', `height: 0px`)}
		role="tabpanel"
		timeout={100}
	>
		<div>{children}</div>
	</CSSTransition>
);

export default PanelBody;
