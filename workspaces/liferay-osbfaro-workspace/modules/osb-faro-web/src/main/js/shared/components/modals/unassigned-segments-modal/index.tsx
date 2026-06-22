import AssignSegments from './AssignSegments';
import Modal from 'shared/components/modal';
import React, {useState} from 'react';
import Welcome from './Welcome';

const MODAL_SCREENS = [Welcome, AssignSegments];

interface IUnassignedSegmentsModalProps {
	groupId: string;
	onClose: () => void;
}

const UnassignedSegmentsModal: React.FC<IUnassignedSegmentsModalProps> = ({
	groupId,
	onClose
}) => {
	const [step, setStep] = useState(0);

	const ScreenComponent = MODAL_SCREENS[step];

	return (
		<Modal className='unassigned-segments-modal-root' size='xl'>
			<ScreenComponent
				groupId={groupId}
				onClose={onClose}
				onNext={(increment = 1) => setStep(step + increment)}
			/>
		</Modal>
	);
};

export default UnassignedSegmentsModal;
