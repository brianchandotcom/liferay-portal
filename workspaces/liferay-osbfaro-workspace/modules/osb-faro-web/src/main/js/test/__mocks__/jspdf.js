/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const jsPDFMock = jest.fn().mockImplementation(() => {
	const state = {
		fontList: {helvetica: {}},
		fontName: 'helvetica',
		fontSize: 8,
		fontStyle: 'normal',
		pageCount: 1,
		textColor: '#000000',
	};

	return {
		addFont: jest.fn((_path, name, style) => {
			state.fontList[name] = state.fontList[name] || {};
			state.fontList[name][style] = true;
		}),
		addImage: jest.fn(),
		addPage: jest.fn(() => {
			state.pageCount += 1;
		}),
		getFont: jest.fn(() => ({
			fontName: state.fontName,
			fontStyle: state.fontStyle,
		})),
		getFontList: jest.fn(() => state.fontList),
		getFontSize: jest.fn(() => state.fontSize),
		getNumberOfPages: jest.fn(() => state.pageCount),
		getTextColor: jest.fn(() => state.textColor),
		getTextDimensions: jest.fn(() => ({h: 10, w: 40})),
		internal: {
			pageSize: {
				getHeight: jest.fn(() => 297),
				getWidth: jest.fn(() => 210),
			},
		},
		line: jest.fn(),
		output: jest.fn(() => 'data:application/pdf;base64,mock'),
		rect: jest.fn(),
		save: jest.fn(),
		setDrawColor: jest.fn(),
		setFillColor: jest.fn(),
		setFont: jest.fn((name, style) => {
			state.fontName = (name || '').toLowerCase();
			state.fontStyle = style || 'normal';
		}),
		setFontSize: jest.fn((size) => {
			state.fontSize = size;
		}),
		setLineWidth: jest.fn(),
		setTextColor: jest.fn((color) => {
			const nameToHex = {
				black: '#000000',
				blue: '#0000ff',
				green: '#008000',
				red: '#ff0000',
				white: '#ffffff',
			};
			state.textColor = nameToHex[color] || color;
		}),
		splitTextToSize: jest.fn((text) =>
			Array.isArray(text) ? text : [text]
		),
		text: jest.fn(),
		textWithLink: jest.fn(),
	};
});

export default jsPDFMock;
