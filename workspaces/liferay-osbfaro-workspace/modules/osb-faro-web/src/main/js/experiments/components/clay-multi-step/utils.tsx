export const getStatus = (stepNumber: number, current: number) => {
	let status = 'wait';

	if (stepNumber === current) {
		status = 'active';
	} else if (stepNumber < current) {
		status = 'complete';
	}

	return status;
};
