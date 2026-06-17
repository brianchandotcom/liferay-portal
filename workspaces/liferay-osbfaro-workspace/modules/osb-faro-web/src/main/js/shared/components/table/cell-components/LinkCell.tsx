import React from 'react';
import TextTruncate from 'shared/components/TextTruncate';
import {Link} from 'react-router-dom';

interface ILinkCellProps {
	data: {id?: string; name: string};
	hrefFormatter: (data: any) => string;
}

const LinkCell = ({data, hrefFormatter}: ILinkCellProps) => (
	<td className="table-cell-expand" key={data.id}>
		<div className="table-title text-truncate">
			<Link to={hrefFormatter(data)}>
				<TextTruncate title={data.name} />
			</Link>
		</div>
	</td>
);

export default LinkCell;
