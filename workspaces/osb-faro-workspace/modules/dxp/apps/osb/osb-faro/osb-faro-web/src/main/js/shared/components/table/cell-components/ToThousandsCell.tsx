import React from 'react';
import {toThousands} from 'shared/util/numbers';

const ToThousandsCell = ({data}: {data: any}) => {
	const formattedCount = toThousands(data.individualCount);

	return (
		<td className='table-cell-expand '>
			<div className='text-truncate text-right'>{formattedCount}</div>
		</td>
	);
};

export default ToThousandsCell;
