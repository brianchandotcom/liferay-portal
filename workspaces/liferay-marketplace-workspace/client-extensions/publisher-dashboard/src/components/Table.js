import {useState} from 'react';

import Pagination from './Pagination';
import TableBody from './TableBody';
import TableHead from './TableHead';
import useProducts from '../hooks/useProducts';

const Table = () => {
	const columns = [
		{label: 'Name', accessor: 'name'},
		{label: 'Version', accessor: 'version'},
		{label: 'Last Updated', accessor: 'modifiedDate'},
		{label: 'Status', accessor: 'productStatus'},
	];

	const publisherName = 'Acme Co';
	const [languageId] = useState(Liferay.ThemeDisplay.getLanguageId());
	const [page, setPage] = useState(1);

	const {data, status} = useProducts(languageId, page);

	if (status === 'success' && data.totalCount === 0) {
		return (
			<div className="align-items-center d-flex flex-column justify-items-center no-apps">
				<svg
					width="144"
					height="80"
					viewBox="0 0 144 80"
					fill="none"
					xmlns="http://www.w3.org/2000/svg"
				>
					<rect width="144" height="80" rx="8" fill="#EDF3FE" />
					<path
						opacity="0.2"
						d="M86 44H76V54H86V44Z"
						fill="#0B5FFF"
					/>
					<path
						d="M86 44H76V54H86V44Z"
						stroke="#004AD7"
						stroke-width="3"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						opacity="0.2"
						d="M68 44H58V54H68V44Z"
						fill="#0B5FFF"
					/>
					<path
						d="M68 44H58V54H68V44Z"
						stroke="#004AD7"
						stroke-width="3"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						opacity="0.2"
						d="M86 26H76V36H86V26Z"
						fill="#0B5FFF"
					/>
					<path
						d="M86 26H76V36H86V26Z"
						stroke="#004AD7"
						stroke-width="3"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
					<path
						opacity="0.2"
						d="M68 26H58V36H68V26Z"
						fill="#0B5FFF"
					/>
					<path
						d="M68 26H58V36H68V26Z"
						stroke="#004AD7"
						stroke-width="3"
						stroke-linecap="round"
						stroke-linejoin="round"
					/>
				</svg>

				<h4 className="font-weight-bold">No apps yet</h4>

				<div>
					Create new apps and they will show up here. Click on "New
					App" to start creating apps
				</div>
			</div>
		);
	}

	if (status === 'success' && data.totalCount !== 0) {
		return (
			<>
				<table className="table">
					<TableHead columns={columns} />
					<TableBody
						columns={columns}
						data={data}
						languageId={languageId}
						status={status}
					/>
				</table>

				<Pagination
					page={page}
					setPage={setPage}
					totalCount={data.totalCount}
				/>
			</>
		);
	}

	return (
		<div className="align-items-center d-flex flex-column justify-items-center">
			<div class="spinner-border text-primary" role="status">
				<span class="sr-only">Loading...</span>
			</div>

			<div>
				Hang tight, we are preparing your arrival as publisher and
				member of{' '}
				<span className="font-weight-bold">{publisherName}</span>
			</div>
		</div>
	);
};

export default Table;
