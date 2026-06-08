/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap} from 'immutable';
import React, {useReducer} from 'react';

export const ACTION_TYPES: {[key: string]: ActionType} = {
	add: 'add',
	clearAll: 'clear-all',
	remove: 'remove',
	toggle: 'toggle',
};

export enum ActionTypes {
	Add = 'add',
	ClearAll = 'clear-all',
	Toggle = 'toggle',
	Remove = 'remove',
}

type ActionType = 'add' | 'clear-all' | 'toggle' | 'remove';

type Action = {
	payload?: {
		item?: {[key: string]: any};
		items?: {[key: string]: any}[];
	};
	type: ActionType;
};
type Dispatch = (action: Action) => void;
type State = {selectedItems: OrderedMap<any, any>};
type SelectionProviderProps = {
	children: React.ReactNode;
	selectedItems?: {id: string}[];
};

export const SelectionContext = React.createContext<{
	selectedItems: OrderedMap<any, any>;
	selectionDispatch?: Dispatch;
}>({selectedItems: OrderedMap()});

export const selectionReducer = function selectionReducer(
	{selectedItems}: State,
	{payload, type}: Action
) {
	switch (type) {
		case 'add': {
			return {
				selectedItems: selectedItems.merge(
					OrderedMap(
						(payload?.items ?? []).map((item) => [item.id, item])
					)
				),
			};
		}
		case 'clear-all': {
			return {
				selectedItems: OrderedMap(),
			};
		}
		case 'remove': {
			const items = payload?.items ?? [];

			return {
				selectedItems: selectedItems.filter(
					(_, key) => !items.some(({id}) => id === key)
				) as OrderedMap<any, any>, // Assert return type from .filter() is OrderedMap until we can update Immutable package
			};
		}
		case 'toggle': {
			if (!payload?.item) {
				return {selectedItems};
			}
			if (selectedItems.has(payload.item.id)) {
				return {selectedItems: selectedItems.delete(payload.item.id)};
			}
			else {
				return {
					selectedItems: selectedItems.set(
						payload.item.id,
						payload.item
					),
				};
			}
		}
		default: {
			throw new Error(`Unhandled action type: ${type}`);
		}
	}
};

export const SelectionProvider = function SelectionProvider({
	children,
	selectedItems: initialValue,
}: SelectionProviderProps) {
	const [state, dispatch] = useReducer(selectionReducer, {
		selectedItems: initialValue
			? OrderedMap(initialValue.map((item) => [item.id, item]))
			: OrderedMap(),
	});

	return (
		<SelectionContext.Provider
			value={{
				selectedItems: state.selectedItems,
				selectionDispatch: dispatch,
			}}
		>
			{children}
		</SelectionContext.Provider>
	);
};

export const useSelectionContext = function useSelectionContext() {
	const context = React.useContext(SelectionContext);
	if (context === undefined) {
		throw new Error(
			'useSelectionContext must be used within a SelectionProvider'
		);
	}

	return context;
};

export const withSelectionProvider = function withSelectionProvider<
	P extends object,
>(WrappedComponent: React.ComponentType<P>) {
	return (props: P) => (
		<SelectionProvider>
			<WrappedComponent {...props} />
		</SelectionProvider>
	);
};
