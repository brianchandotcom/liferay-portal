import ClayButton from '@clayui/button';
import {useState} from 'react';

import {GetAppModal} from '../../components/GetAppModal/GetAppModal';

export default function GetAppPage() {
	const [showModal, setShowModal] = useState(false);

	return (
		<>
			<ClayButton onClick={() => setShowModal(true)}>Get App</ClayButton>
			{showModal && (
				<GetAppModal handleClose={() => setShowModal(false)} />
			)}
		</>
	);
}
