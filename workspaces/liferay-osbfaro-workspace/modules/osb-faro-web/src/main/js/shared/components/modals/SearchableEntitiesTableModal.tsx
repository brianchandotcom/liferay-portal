/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {OrderedMap} from 'immutable';
import {noop} from 'lodash';
import React from 'react';
import SearchableEntityTable from '~/shared/components/SearchableEntityTable';
import Modal, {Size} from '~/shared/components/modal';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';

interface ISearchableEntitiesTableModalProps {
	className: string;
	defaultParams: {[key: string]: any};
	initialDelta?: number;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	onClose: () => void;
	size: Size;
	title: string;
}

const SearchableEntitiesTableModal: React.FC<
	ISearchableEntitiesTableModalProps
> = ({
	className,
	initialDelta = 10,
	initialOrderIOMap = createOrderIOMap(NAME),
	onClose = noop,
	size = 'xxl',
	title = 'entities',
	...otherProps
}) => {
	const {
		delta,
		onDeltaChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query,
	} = useStatefulPagination(undefined, {
		initialDelta,
		initialOrderIOMap,
	});

	return (
		<Modal className={className} size={size}>
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body className="p-0">
				<SearchableEntityTable
					{...otherProps}
					autoFocusSearch
					delta={delta}
					onDeltaChange={onDeltaChange}
					onOrderIOMapChange={onOrderIOMapChange}
					onPageChange={onPageChange}
					onQueryChange={onQueryChange}
					orderIOMap={orderIOMap}
					page={page}
					query={query}
				/>
			</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className="button-root"
					displayType="primary"
					onClick={onClose}
				>
					{Liferay.Language.get('done')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default SearchableEntitiesTableModal;
