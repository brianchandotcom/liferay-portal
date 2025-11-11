import { useEffect, useState } from 'react';
import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';

const DistributorDetails = () => {
	const [selectedDistributor, setSelectedDistributor] = useState(null);

	useEffect(() => {
		Liferay.on('selectDistributor', (distributor) => {
			setSelectedDistributor(distributor);
		});
	}, []);

	return !selectedDistributor ? (
		<ClayAlert displayType="info" title="Info:">
			Please select a distributor from the table.
		</ClayAlert>
	) : (
		<div class="row">
			<div class="col">
				<h2>{selectedDistributor.name}</h2>
				<p>
					Location: {selectedDistributor.city}, {selectedDistributor.state}
				</p>
			</div>
			<div class="col">
				<ClayButton displayType="primary">
					Contact Distributor
				</ClayButton>
			</div>
		</div>
	);
};

export default DistributorDetails;
