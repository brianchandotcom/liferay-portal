/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';
import Popover from '~/shared/components/Popover';

export interface IInfoPopoverProps {
	className?: string;
	content?: React.ReactText;
	popOverAttr?: {className: string};
	title?: string;
}

const InfoPopover: React.FC<IInfoPopoverProps> = ({
	className,
	content,
	popOverAttr,
	title,
}) => {
	const _iconSpanRef = useRef<HTMLSpanElement>(null);

	const [showPopover, setShowPopover] = useState(false);

	return (
		<>
			<span
				className={getCN('info-popover-root', className)}
				onBlur={() => setShowPopover(false)}
				onFocus={() => setShowPopover(true)}
				onMouseOut={() => setShowPopover(false)}
				onMouseOver={() => setShowPopover(true)}
				ref={_iconSpanRef}
			>
				<ClayIcon className="icon-root" symbol="question-circle-full" />
			</span>

			{ReactDOM.createPortal(
				<Popover
					alignElement={_iconSpanRef.current!}
					content={content}
					title={title}
					visible={showPopover}
					{...popOverAttr}
				/>,
				document.querySelector('body.dxp') || document.body
			)}
		</>
	);
};

export default InfoPopover;
