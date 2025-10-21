/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useEffect, useRef, useState} from 'react';
import classNames from 'classnames';

import './AccountSelectDropDown.scss';

interface Option {
	key: string;
	name: string;
	text: string;
}

interface DropdownProps {
	onChange: (value: string) => void;
	options: Option[];
	value?: Option;
}

export default function AcountSelectDropDown({
	onChange,
	options,
	value,
}: DropdownProps) {
	const [isOpen, setIsOpen] = useState(false);
	const [selected, setSelected] = useState<Option | undefined>(value);
	const dropDownRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		function handleClickOutside(event: MouseEvent) {
			if (
				dropDownRef.current &&
				!dropDownRef.current.contains(event.target as Node)
			) {
				setIsOpen(false);
			}
		}
		document.addEventListener('click', handleClickOutside);

		return () => document.removeEventListener('click', handleClickOutside);
	}, []);

	function handleSelect(option: Option) {
		setSelected(option);
		setIsOpen(false);
		onChange(option.key);
	}

	return (
		<div className="custom-drop-down-container" ref={dropDownRef}>
			<button onClick={() => setIsOpen((prev) => !prev)}>
				<div className="align-items-center d-flex justify-content-between">
					{value?.name}
					<ClayIcon symbol="caret-bottom" />
				</div>
			</button>

			{isOpen && (
				<ul>
					{options.map((option) => (
						<li
							className={classNames({
								'drop-down-item-selected':
									selected?.key === option.key,
							})}
							key={option.key}
							onClick={() => handleSelect(option)}
						>
							<b>{option.name}</b>
							<div>
								<small>{option.text}</small>
							</div>
						</li>
					))}
				</ul>
			)}
		</div>
	);
}
