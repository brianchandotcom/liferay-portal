/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STRUCTURES_MAP} from '../constants';
import {getMimeType} from '../index';

describe('getMimeType', () => {
	it('returns correct mapping for assetType when mimeType is missing', () => {
		expect(getMimeType({assetType: 'blog'})).toEqual(STRUCTURES_MAP.blog);
		expect(getMimeType({assetType: 'document'})).toEqual(
			STRUCTURES_MAP.document
		);
	});

	it('returns fallback for unknown assetType when mimeType is missing', () => {
		expect(getMimeType({assetType: 'unknown'})).toEqual(
			STRUCTURES_MAP.CMSDocumentDefault
		);
	});

	it('returns CMSDocumentDefault if both assetType and mimeType are missing', () => {
		expect(getMimeType({})).toEqual(STRUCTURES_MAP.CMSDocumentDefault);
	});

	it('returns mapping for exact mimeType match', () => {
		expect(getMimeType({mimeType: 'application/pdf'})).toEqual(
			STRUCTURES_MAP.CMSDocumentVector
		);
		expect(getMimeType({mimeType: 'text/plain'})).toEqual(
			STRUCTURES_MAP.CMSDocumentText
		);
	});

	it('returns mapping based on mimeType prefix (image)', () => {
		expect(getMimeType({mimeType: 'image/png'})).toEqual(
			STRUCTURES_MAP.CMSDocumentImage
		);
		expect(getMimeType({mimeType: 'image/jpeg'})).toEqual(
			STRUCTURES_MAP.CMSDocumentImage
		);
		expect(getMimeType({mimeType: 'image/gif'})).toEqual(
			STRUCTURES_MAP.CMSDocumentImage
		);
	});

	it('returns mapping based on mimeType prefix (audio)', () => {
		expect(getMimeType({mimeType: 'audio/mpeg'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
		expect(getMimeType({mimeType: 'audio/wav'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
	});

	it('returns mapping based on mimeType prefix (video)', () => {
		expect(getMimeType({mimeType: 'video/mp4'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
		expect(getMimeType({mimeType: 'video/webm'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
	});

	it('returns default mapping for unknown mimeType and unknown prefix', () => {
		expect(getMimeType({mimeType: 'unknown/type'})).toEqual(
			STRUCTURES_MAP.CMSDocumentDefault
		);
	});

	it('handles mimeType without slash by using it as prefix', () => {

		// Even if it's not a standard mime type, the logic uses the first part.

		expect(getMimeType({mimeType: 'image'})).toEqual(
			STRUCTURES_MAP.CMSDocumentImage
		);
	});
});
