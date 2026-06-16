jest.mock('shared/actions/modals', () => ({
	actionTypes: {},
	close: jest.fn(),
	modalTypes: {},
	open: jest.fn(() => ({meta: {}, payload: {}, type: 'open'}))
}));
