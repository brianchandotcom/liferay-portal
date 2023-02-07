const TableBody = ({columns, data, languageId, status}) => {
	return (
		<tbody>
			{status === 'success' &&
				data &&
				data.items &&
				data.items.map((product) => {
					return (
						<tr key={product.id}>
							{columns.map(({accessor}) => {
								const productValue =
									accessor === 'name'
										? product[accessor][languageId]
										: product[accessor];
								return <td key={accessor}>{productValue}</td>;
							})}
						</tr>
					);
				})}
		</tbody>
	);
};

export default TableBody;
