/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useEffect, useRef} from 'react';

import AutofillBuilds from './AutoFillBuilds';
import CompareRuns from './CompareRuns';

type ComparePopoverProps = {
	expanded?: boolean;
	setVisible: (state: boolean) => void;
	triggedRef: React.RefObject<HTMLDivElement>;
	visible: boolean;
};

const ComparePopover: React.FC<ComparePopoverProps> = ({
	expanded = false,
	setVisible,
	triggedRef,
	visible,
}) => {
	const ref = useRef<HTMLDivElement>(null);

	useEffect(() => {
		const handleClickOutside = (event: any) => {
			if (
				ref.current &&
				!ref.current.contains(event.target) &&
				!triggedRef.current?.contains(event.target)
			) {
				setVisible(false);
			}
		};

		document.addEventListener('mousedown', handleClickOutside);

		return () =>
			document.removeEventListener('mousedown', handleClickOutside);
	}, [setVisible, triggedRef]);

	return (
		<div
			className={classNames('tr-compare-runs-popover', {
				'hidden': !visible && !expanded,
				'hidden--expanded': !visible && expanded,
				'visible': visible && !expanded,
				'visible--expanded': visible && expanded,
			})}
			onBlur={() => setVisible(false)}
			ref={ref}
		>
			<CompareRuns setVisible={setVisible} />
			<AutofillBuilds setVisible={setVisible} />
		</div>
	);
};

export default ComparePopover;
