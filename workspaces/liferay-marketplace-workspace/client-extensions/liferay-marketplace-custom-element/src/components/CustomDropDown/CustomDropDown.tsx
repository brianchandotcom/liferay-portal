/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';

interface Option {
	key: string;
	name: string;
	text: string;
}

interface DropdownProps {
	options: Option[];
	value?: Option;
	onChange: (value: string) => void;
}

export default function CustomDropdown({
	options,
	value,
	onChange,
}: DropdownProps) {
	const [isOpen, setIsOpen] = useState(false);
	const [selected, setSelected] = useState<Option | null>(null);
	const dropdownRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		function handleClickOutside(event: MouseEvent) {
			if (
				dropdownRef.current &&
				!dropdownRef.current.contains(event.target as Node)
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

	console.log('rerender');
	return (
		<div ref={dropdownRef} style={{position: 'relative'}}>
			<button
				onClick={() => setIsOpen((prev) => !prev)}
				style={{
					backgroundColor: 'white',
					border: '1px solid #ccc',
					borderRadius: '8px',
					color: '#282934',
					cursor: 'pointer',
					padding: '10px 14px',
					textAlign: 'left',
					width: '100%',
				}}
			>
				<div className="align-items-center d-flex justify-content-between">
					{value?.name}
					<ClayIcon symbol="caret-bottom" />
				</div>
			</button>

			{isOpen && (
				<ul
					style={{
						background: 'white',
						border: '1px solid #ccc',
						borderRadius: '8px',
						boxShadow: '0 2px 5px rgba(0,0,0,0.1)',
						left: 0,
						listStyle: 'none',
						margin: 0,
						padding: '4px 0',
						position: 'absolute',
						right: 0,
						top: '110%',
						zIndex: 10,
					}}
				>
					{options.map((option) => (
						<li
							key={option.key}
							onClick={() => handleSelect(option)}
							style={{
								background:
									selected?.key === option.key
										? '#f0f8ff'
										: 'transparent',
								color:
									selected?.key === option.key
										? '#004AD7'
										: '#54555F',
								cursor: 'pointer',

								padding: '10px 12px',
							}}
							onMouseEnter={(e) =>
								((
									e.target as HTMLLIElement
								).style.backgroundColor = '#f8f9fa')
							}
							onMouseLeave={(e) =>
								((
									e.target as HTMLLIElement
								).style.backgroundColor =
									selected?.key === option.key
										? '#f0f8ff'
										: 'transparent')
							}
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
