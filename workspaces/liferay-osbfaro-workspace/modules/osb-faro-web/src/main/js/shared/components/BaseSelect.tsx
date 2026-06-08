/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {identity, noop} from 'lodash';
import React, {useEffect, useImperativeHandle, useRef, useState} from 'react';
import Loading from '~/shared/components/Loading';
import {useDebounce} from '~/shared/hooks/useDebounce';
import {useRequest} from '~/shared/hooks/useRequest';

import {ARROW_DOWN, ARROW_UP, ENTER} from '../util/key-constants';
import Input from './Input';

const DEBOUNCE_DELAY = 250;
const SELECT_KEYS = [ARROW_DOWN, ARROW_UP, ENTER];

type GraphqlQuery = {
	mapResultsToProps: (data: any) => TMappedData;
	query: DocumentNode;
	variables: object;
};

type TMappedData = {
	data: string[];
	total: number;
};

interface IItemProps extends React.HTMLAttributes<HTMLLIElement> {
	active?: boolean;
	disabled?: boolean;
	item: any;
	itemRenderer: (item: any) => React.ReactNode;
	onSelect: (item: any) => void;
}

export const Item = function Item({
	active,
	className,
	disabled,
	item,
	itemRenderer,
	onSelect,
}: IItemProps) {
	return (
		<li className={className}>
			<ClayButton
				className={getCN('button-root dropdown-item text-truncate', {
					active,
				})}
				disabled={disabled}
				displayType="unstyled"
				onClick={() => onSelect(item)}
			>
				{itemRenderer(item)}
			</ClayButton>
		</li>
	);
};

interface IBaseSelectProps extends React.HTMLAttributes<HTMLInputElement> {
	alwaysFetchOnFocus?: boolean;
	className?: string;
	containerClass?: string;
	dataSourceFn?: (value: string | number) => Promise<any>;
	disabled?: boolean;
	emptyInputOnInactive?: boolean;
	focusOnInit?: boolean;
	forwardedRef?: React.Ref<any>;
	graphqlQuery?: GraphqlQuery;
	id?: string;
	inputName?: string;
	inputSize?: string;
	inputValue?: string | React.ReactText;
	inset?: boolean;
	itemRenderer?: (item: any) => React.ReactNode;
	menuTitle?: string;
	onFocus?: () => void;
	onInputValueChange?: (value: string | number) => void;
	onSelect?: (item: any) => void;
	placeholder?: string;
	selectedItem?: any;
}

const BaseSelect: React.FC<IBaseSelectProps> = ({
	alwaysFetchOnFocus = false,
	className,
	containerClass,
	dataSourceFn,
	disabled = false,
	emptyInputOnInactive = false,
	focusOnInit = false,
	forwardedRef,
	graphqlQuery,
	id,
	inputName,
	inputSize,
	inputValue = '',
	inset = false,
	itemRenderer,
	menuTitle,
	onBlur,
	onFocus,
	onInputValueChange = noop,
	onSelect = noop,
	placeholder = '',
	selectedItem,
}) => {
	useImperativeHandle(forwardedRef, () => ({
		focus: () => {
			handleFocus();

			_inputRef.current.focus();
		},
	}));

	const [active, setActive] = useState<boolean>(false);
	const [focusIndex, setFocusIndex] = useState<number>(0);

	const _inputRef = useRef<any>();

	let response;

	if (graphqlQuery) {
		const {
			mapResultsToProps = (value) => value,
			query,
			variables,
		} = graphqlQuery;

		const debouncedInputValue = useDebounce(inputValue, DEBOUNCE_DELAY);

		response = useQuery(query, {
			fetchPolicy: 'network-only',
			skip: !active,
			variables: {
				...variables,
				keywords: debouncedInputValue,
			},
		});

		response = {
			...response,
			...mapResultsToProps(response.data),
		};
	}
	else {
		response = useRequest({
			dataSourceFn: ({value}) => dataSourceFn?.(value),
			debounceDelay: DEBOUNCE_DELAY,
			initialState: {
				data: [],
				error: false,
				loading: false,
			},
			resetStateIfSkipingRequest: true,
			skipRequest: !active,
			variables: {value: inputValue},
		});
	}

	const {data: items = [], loading, refetch} = response;

	useEffect(() => {
		if (focusOnInit) {
			_inputRef.current.focus();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleBlur = (event: React.FocusEvent<HTMLInputElement>) => {
		onBlur && onBlur(event);
	};

	const handleFocus = () => {
		if (!active) {
			if (!items?.length || alwaysFetchOnFocus) {
				refetch();
			}

			onFocus && onFocus();

			setActive(true);

			handleSetFocusIndex(0);
		}
	};

	const handleKeyDown = (event: React.KeyboardEvent) => {
		const {keyCode} = event;

		if (!SELECT_KEYS.includes(keyCode)) {
			return;
		}

		event.preventDefault();

		switch (keyCode) {
			case ARROW_DOWN:
				handleSetFocusIndex(focusIndex + 1);
				break;
			case ARROW_UP:
				handleSetFocusIndex(focusIndex - 1);
				break;
			case ENTER:
				handleSelect(items[focusIndex]);
				break;
			default:
				break;
		}
	};

	const handleOutsideClick = () => {
		_inputRef.current.blur();

		setActive(false);
	};

	const handleSelect = (item: any) => {
		handleOutsideClick();

		onSelect(item);
	};

	const handleSetFocusIndex = (val: number): void => {
		const count = items?.length;

		setFocusIndex((val + count) % count || 0);
	};

	return (
		<ClayDropDown
			className={getCN(
				'dropdown-root',
				'base-select-container',
				containerClass
			)}
			closeOnClick
			trigger={
				<div>
					<Input.Group
						className={getCN(
							'base-select-input-root select-input-root',
							className,
							{inset}
						)}
						onClick={disabled ? null : handleFocus}
					>
						<Input.GroupItem>
							<Input
								autoComplete="off"
								disabled={disabled}
								id={id}
								inset="after"
								name={inputName}
								onBlur={handleBlur}
								onChange={(
									event: React.ChangeEvent<HTMLInputElement>
								) => {
									onInputValueChange(event.target.value);
								}}
								onFocus={handleFocus}
								onKeyDown={handleKeyDown}
								placeholder={placeholder}
								ref={_inputRef}
								size={inputSize}
								value={
									active || !emptyInputOnInactive
										? inputValue
										: ''
								}
							/>

							<Input.Inset position="after">
								{loading ? (
									<Loading />
								) : (
									<ClayIcon
										className="icon-root"
										symbol="caret-bottom"
									/>
								)}
							</Input.Inset>
						</Input.GroupItem>

						{!active && selectedItem && itemRenderer && (
							<div className="selected-item-container">
								{itemRenderer(selectedItem)}
							</div>
						)}
					</Input.Group>
				</div>
			}
		>
			{!!menuTitle && (
				<ClayDropDown.Caption>{menuTitle}</ClayDropDown.Caption>
			)}

			{items.map((item: any, i: number) => (
				<ClayDropDown.Item
					active={i === focusIndex}
					className={className}
					disabled={loading}
					key={i}
					onClick={() => handleSelect(item)}
				>
					{itemRenderer ? itemRenderer(item) : identity(item)}
				</ClayDropDown.Item>
			))}
		</ClayDropDown>
	);
};

export default React.forwardRef<HTMLInputElement, IBaseSelectProps>(
	(props, ref) => <BaseSelect {...props} forwardedRef={ref} />
);
