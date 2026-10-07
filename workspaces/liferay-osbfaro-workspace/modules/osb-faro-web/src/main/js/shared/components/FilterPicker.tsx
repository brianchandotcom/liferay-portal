import classNames from 'classnames';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import DropDown from '@clayui/drop-down';
import Loading, {Align} from 'shared/components/Loading';
import React, {useMemo, useState} from 'react';
import {ClayInput} from '@clayui/form';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Icon} from '@clayui/core';
import {MAX_LABEL_LENGTH, NetworkState} from 'shared/util/constants';
import {
	DEFAULT_PAGE_SIZE,
	PaginatedDataSourceFn,
	usePaginatedRequest,
} from 'shared/hooks/usePaginatedRequest';
import {sub} from 'shared/util/lang';
import {truncateText} from 'shared/util/util';
import {useDebounce} from 'shared/hooks/useDebounce';
import {useRequest} from 'shared/hooks/useRequest';

/**
 * Distance from the end of the option list at which the next page is
 * requested, matching Clay's own infinite scroll.
 */

const LOAD_MORE_THRESHOLD = 40;

const MENU_WIDTH = 240;

/**
 * Delay before a keystroke reaches the backend, so typing a word costs one
 * request rather than one per character.
 */

const SEARCH_DELAY = 300;

/**
 * Sentinel id for the "all items" entry. The option list is keyed by string
 * ids, so the absence of a filter is encoded rather than left as `null`.
 */

const ALL_VALUES_KEY = 'all';

const NO_ITEMS: IFilterPickerItem[] = [];

const NO_VARIABLES = {};

/**
 * Reads the option list off the response. The filter endpoints return either a
 * paginated `{items}` envelope or a bare array, so both are accepted and a
 * caller only passes `normalize` when its payload needs reshaping.
 */

const defaultNormalize = (data: any): IFilterPickerItem[] => {
	if (Array.isArray(data)) {
		return data;
	}

	return data?.items ?? NO_ITEMS;
};

/**
 * The shape every filter endpoint already returns, so no caller has to rename
 * its payload on the way in or out.
 */

export interface IFilterPickerItem {
	disabled?: boolean;
	id: string;
	name: string;
}

interface ITriggerButtonProps
	extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	buttonClassName?: string;
	displayType: 'filter' | 'select';
	filterLabel: string;
	loading?: boolean;
}

/**
 * `DropDown` clones its trigger with `classNames('dropdown-toggle', ...)`, and
 * earlier `Picker` pushed a `form-control form-control-select` className that
 * styled this as a select, making it taller than the buttons beside it. So the
 * caller's classes arrive as `buttonClassName` and the `className` set after
 * the spread below replaces Clay's rather than merging with it.
 */

const TriggerButton = React.forwardRef<HTMLButtonElement, ITriggerButtonProps>(
	({buttonClassName, displayType, filterLabel, loading, ...rest}, ref) => {
		const filter = displayType === 'filter';

		return (
			<ClayButton
				{...rest}
				className={classNames(
					buttonClassName,

					// The filter trigger sits in a flex sub-header, where a
					// wrapping label would make the button twice as tall as its
					// siblings.

					filter
						? ['rounded-lg', 'text-nowrap']
						: 'form-control form-control-select form-control-select-secondary'
				)}
				disabled={loading}
				displayType="secondary"
				ref={ref}
				size={filter ? 'sm' : undefined}
			>
				{filter && (
					<Icon
						className="inline-item inline-item-before"
						symbol="filter"
					/>
				)}

				{filterLabel}

				{loading ? (
					<Loading align={Align.Right} />
				) : (
					filter && (
						<Icon
							className="inline-item inline-item-after"
							symbol="caret-bottom"
						/>
					)
				)}
			</ClayButton>
		);
	}
);

TriggerButton.displayName = 'TriggerButton';

const renderOption = (
	item: IFilterPickerItem,
	selected: boolean,
	onSelect: (id: string) => void
) => (
	<DropDown.Item
		active={selected}
		aria-selected={selected}
		disabled={item.disabled}
		id={item.id}
		key={item.id}
		onClick={() => onSelect(item.id)}
		roleItem="option"
		symbolLeft={selected ? 'check-small' : undefined}
	>
		<div
			className="w-100"
			title={item.name.length > MAX_LABEL_LENGTH ? item.name : undefined}
		>
			{truncateText(item.name, MAX_LABEL_LENGTH, null)}
		</div>
	</DropDown.Item>
);

interface IFilterPickerProps {
	className?: string;

	/**
	 * Fetches the options. It runs on the first open rather than on mount, so a
	 * filter the user never touches costs no request. Prefer this over `items`.
	 */

	dataSourceFn?: (variables: any) => Promise<any> | undefined;

	/**
	 * `filter` is the dashboard sub-header button; `select` looks like a form
	 * select, for a picker that is part of a form.
	 */

	displayType?: 'filter' | 'select';

	/**
	 * Plural name of what is being filtered, used for the "All <entity>" label
	 * and for the accessible name.
	 */

	entityLabel: string;

	/**
	 * Options owned by the caller, for the rare list that cannot be expressed
	 * as a single request (e.g. one whose entries are enriched by a second
	 * query). Ignored when `dataSourceFn` is set.
	 */

	items?: IFilterPickerItem[];

	/**
	 * Loading state owned by the caller. Only read on the `items` path;
	 * `dataSourceFn` tracks its own.
	 */

	loading?: boolean;

	/**
	 * Turns the response into the option list. Only needed when the payload is
	 * neither an array of items nor an `{items}` envelope.
	 */

	normalize?: (data: any) => IFilterPickerItem[];
	onFilterChange: (item: IFilterPickerItem | null) => void;
	pageSize?: number;

	/**
	 * Fetches the options one page at a time, loading the next page as the
	 * list is scrolled. Takes precedence over `dataSourceFn` and `items`.
	 */

	paginatedDataSourceFn?: PaginatedDataSourceFn<IFilterPickerItem>;

	/**
	 * The selected item, for callers that own the selection (a URL query, a
	 * context). Taking the whole item rather than just its id lets a selection
	 * display even when the fetched page does not contain it. Leave it
	 * undefined to let this component track the selection itself.
	 */

	selected?: IFilterPickerItem | null;

	/**
	 * Whether to prepend the "All <entity>" entry, for a picker whose value is
	 * required rather than optional.
	 */

	showAllOption?: boolean;

	/**
	 * Arguments handed to `dataSourceFn`.
	 */

	variables?: object;
}

/**
 * The dashboard filter dropdown: a searchable option list behind a secondary
 * button, prepended with an "All <entity>" entry. Shared by every filter in a
 * page sub-header so they stay visually and behaviorally identical.
 */

const FilterPicker: React.FC<IFilterPickerProps> = ({
	className,
	dataSourceFn,
	displayType = 'filter',
	entityLabel,
	items = NO_ITEMS,
	loading,
	normalize = defaultNormalize,
	onFilterChange,
	pageSize = DEFAULT_PAGE_SIZE,
	paginatedDataSourceFn,
	selected,
	showAllOption = true,
	variables = NO_VARIABLES,
}) => {
	const [ownSelected, setOwnSelected] = useState<IFilterPickerItem | null>(
		null
	);

	// Whether the user wants the menu shown. `active` is derived from it rather
	// than being it, so the menu can be held back while the options load.

	const [expanded, setExpanded] = useState(false);

	// Flipped by the first open and never back, so the options are fetched once
	// on demand. `useRequest` keys its effect on `skipRequest` and `variables`,
	// so reopening the picker does not refetch.

	const [opened, setOpened] = useState(false);

	// `search` is what the input shows; `query` is what reaches the backend, one
	// step behind so a burst of keystrokes costs a single request. Lowercased
	// because the filter endpoints match case-sensitively.

	const [search, setSearch] = useState('');

	const paginated = Boolean(paginatedDataSourceFn);

	// The paginated source debounces the search itself, so this one is held
	// still to avoid a second render per burst of keystrokes.

	const query = useDebounce(
		paginated ? '' : search.toLowerCase(),
		SEARCH_DELAY
	);

	// `query` is omitted while empty so the request keeps the caller's own
	// shape; every filter endpoint already defaults it to the empty string.
	// Not memoized: `useRequest` compares `variables` deeply, so the identity
	// is never read.

	const requestVariables = {
		...variables,
		...(query ? {query} : {}),
	};

	const {
		data,
		error,
		loading: requestLoading,
	} = useRequest({
		dataSourceFn,

		// The default leaves `loading` true, which would disable the trigger
		// and leave no way to start the request this component is waiting on.

		initialState: {data: null, error: false, loading: false},
		skipRequest: !dataSourceFn || !opened || paginated,
		variables: requestVariables,
	});

	const {
		items: paginatedItems,
		loaded: paginatedLoaded,
		networkState,
		onLoadMore,
	} = usePaginatedRequest({
		dataSourceFn: paginatedDataSourceFn,
		debounceDelay: SEARCH_DELAY,
		pageSize,
		query: search.toLowerCase(),
		skip: !opened,
	});

	const fetching = Boolean(dataSourceFn);

	// True once the first response has landed, and monotone from then on because
	// `useRequest` keeps `data` across refetches. The menu opens on this rather
	// than on the absence of `loading`, which would both flash it open for the
	// paint between the click and the request starting, and slam it shut on
	// every keystroke. A failure counts, so the menu opens empty rather than
	// never.

	const settled = paginated
		? paginatedLoaded
		: data !== null || Boolean(error);

	const optionsReady = paginated || fetching ? settled : !loading;

	// Normalized here rather than through `useRequest`'s own `normalize`, which
	// is captured in a `useCallback([])` and would go stale.

	const fetchedItems = useMemo(
		() => (data === null ? NO_ITEMS : normalize(data)),
		[data, normalize]
	);

	let resolvedItems: IFilterPickerItem[] = fetching ? fetchedItems : items;
	let resolvedLoading = fetching ? requestLoading : loading;

	if (paginated) {
		resolvedItems = paginatedItems;
		resolvedLoading = networkState === NetworkState.Loading;
	}

	const loadingMore = paginated && networkState === NetworkState.Refetch;

	const handleListScroll = (event: React.UIEvent<HTMLElement>) => {
		const {clientHeight, scrollHeight, scrollTop} = event.currentTarget;

		if (scrollTop + clientHeight >= scrollHeight - LOAD_MORE_THRESHOLD) {
			onLoadMore?.();
		}
	};

	const controlled = selected !== undefined;

	const selectedItem = controlled ? selected : ownSelected;

	const allValuesLabel = sub(Liferay.Language.get('all-x'), [
		entityLabel,
	]) as string;

	const options = useMemo(
		() =>
			showAllOption
				? [{id: ALL_VALUES_KEY, name: allValuesLabel}, ...resolvedItems]
				: resolvedItems,
		[allValuesLabel, resolvedItems, showAllOption]
	);

	const handleSelectionChange = (key: string) => {
		const item =
			key === ALL_VALUES_KEY
				? null
				: resolvedItems.find(({id}) => id === key) ?? null;

		if (!controlled) {
			setOwnSelected(item);
		}

		setExpanded(false);

		onFilterChange(item);
	};

	const selectedKey = selectedItem?.id ?? ALL_VALUES_KEY;

	// `DropDown` renders its own `div.dropdown` container, which takes the place
	// of the wrapper `Picker` needed.

	return (
		<DropDown

			// Held closed until the options are in, so the menu never
			// flashes empty while the request is in flight.

			active={expanded && optionsReady}

			// Matches what `Picker` passed. Left at the `DropDown`
			// default of false, overflow is resolved against the
			// scrolling container instead of the viewport, so the
			// menu can shift or flip while the page scrolls.

			alignmentByViewport
			closeOnClick={false}
			closeOnClickOutside
			hasLeftSymbols
			menuElementAttrs={{
				className: 'dropdown-menu-select',
				style: {maxWidth: 'none', width: MENU_WIDTH},
			}}
			menuWidth="shrink"

			// Without this the menu markup is mounted (hidden) for
			// every filter on the page, as `Picker` never did.

			onActiveChange={(active) => {
				setExpanded(active);

				if (active) {
					setOpened(true);
				}
			}}
			renderMenuOnClick
			trigger={
				<TriggerButton
					aria-label={
						displayType === 'select'
							? entityLabel
							: (sub(Liferay.Language.get('filter-by-x'), [
									entityLabel,
								]) as string)
					}
					buttonClassName={className}
					displayType={displayType}
					filterLabel={
						selectedItem
							? truncateText(
									selectedItem.name,
									MAX_LABEL_LENGTH,
									null
								)
							: showAllOption
								? allValuesLabel
								: entityLabel
					}
					loading={resolvedLoading}
					role="combobox"
				/>
			}
		>
			<div className="pb-2 pt-3 px-3">
				<ClayInput.Group small>
					<ClayInput.GroupItem className="input-group-item-focusable">
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							insetAfter
							onChange={(event) => setSearch(event.target.value)}
							placeholder={Liferay.Language.get('search')}
							type="text"
							value={search}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<Icon symbol="search" />
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</div>

			{/* The provider wraps the list alone: its hover state would
				    otherwise re-render `DropDown`, which realigns the menu on
				    every render and stutters the list while it scrolls. */}

			<ClayTooltipProvider>
				<DropDown.ItemList
					className="inline-scroller"
					onScroll={handleListScroll}
					role="listbox"
				>
					{options.map((item) =>
						renderOption(
							item,
							item.id === selectedKey,
							handleSelectionChange
						)
					)}

					{loadingMore && (
						<li className="my-2" role="presentation">
							<ClayLoadingIndicator size="sm" />
						</li>
					)}
				</DropDown.ItemList>
			</ClayTooltipProvider>
		</DropDown>
	);
};

export default FilterPicker;
