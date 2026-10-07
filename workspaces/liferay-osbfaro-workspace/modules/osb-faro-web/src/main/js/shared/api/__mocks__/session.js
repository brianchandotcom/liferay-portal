import * as data from 'test/data';

export const fetchAcquisitionParameters = jest.fn(() =>
	Promise.resolve({items: [], total: 0})
);

export const fetchFieldValues = jest.fn(() =>
	Promise.resolve(data.mockSearch(String))
);
