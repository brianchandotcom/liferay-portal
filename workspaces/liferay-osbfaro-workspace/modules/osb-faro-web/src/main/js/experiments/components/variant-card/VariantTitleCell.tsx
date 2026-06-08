/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useRef, useState} from 'react';
import ReactDOM from 'react-dom';
import Label from '~/shared/components/Label';
import Popover from '~/shared/components/Popover';
import {isEllipisActive} from '~/shared/util/util';

interface IVariantTitleProps {
	labels: Array<{status: string; value: string}>;
	title: string;
}

const VariantTitle: React.FC<IVariantTitleProps> = ({labels = [], title}) => {
	const [showPopover, setShowPopover] = useState(false);
	const titleRef = useRef<HTMLDivElement>(null);

	const handleMouseOut = () => setShowPopover(false);
	const handleMouseOver = (event: React.SyntheticEvent) =>
		setShowPopover(isEllipisActive(event));

	return (
		<td className="table-cell-expanded">
			<div
				className="h5 mb-1 text-truncate variant-title"
				onBlur={handleMouseOut}
				onFocus={handleMouseOver}
				onMouseOut={handleMouseOut}
				onMouseOver={handleMouseOver}
				ref={titleRef}
			>
				{title}
			</div>

			{!!labels.length &&
				labels.map(({status, value}, index) => (
					<Label display={status} key={index}>
						{value}
					</Label>
				))}

			{ReactDOM.createPortal(
				<Popover
					alignElement={titleRef.current}
					title={title}
					visible={showPopover}
				/>,
				document.querySelector('body.dxp') ?? document.body
			)}
		</td>
	);
};

export default VariantTitle;
