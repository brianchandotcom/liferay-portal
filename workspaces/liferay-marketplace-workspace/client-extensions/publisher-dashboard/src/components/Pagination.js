import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import React from 'react';

import '../Dashboard.css';
import getIconSpriteMap from './getIconSpriteMap';

function Pagination(props) {
	var {page, setPage, totalCount} = props;

	return (
		<ClayPaginationBarWithBasicItems
			activeDelta={10}
			activePage={page}
			ellipsisBuffer={1}
			ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
			onPageChange={setPage}
			showDeltasDropDown={false}
			spritemap={getIconSpriteMap()}
			totalItems={totalCount}
		/>
	);
}

export default Pagination;
