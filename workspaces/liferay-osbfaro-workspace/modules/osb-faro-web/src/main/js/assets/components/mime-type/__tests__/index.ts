import {getMimeType} from '../index';
import {STRUCTURES_MAP} from '../constants';

describe('getMimeType', () => {
	it('should return correct mapping for assetType when mimeType is missing', () => {
		expect(getMimeType({assetType: 'blog'})).toEqual(STRUCTURES_MAP.blog);
		expect(getMimeType({assetType: 'document'})).toEqual(
			STRUCTURES_MAP.document
		);
	});

	it('should return fallback for unknown assetType when mimeType is missing', () => {
		expect(getMimeType({assetType: 'unknown'})).toEqual(
			STRUCTURES_MAP.CMSDocumentDefault
		);
	});

	it('should return CMSDocumentDefault if both assetType and mimeType are missing', () => {
		expect(getMimeType({})).toEqual(STRUCTURES_MAP.CMSDocumentDefault);
	});

	it('should return mapping for exact mimeType match', () => {
		expect(getMimeType({mimeType: 'application/pdf'})).toEqual(
			STRUCTURES_MAP.CMSDocumentVector
		);
		expect(getMimeType({mimeType: 'text/plain'})).toEqual(
			STRUCTURES_MAP.CMSDocumentText
		);
	});

	it('should return mapping based on mimeType prefix (image)', () => {
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

	it('should return mapping based on mimeType prefix (audio)', () => {
		expect(getMimeType({mimeType: 'audio/mpeg'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
		expect(getMimeType({mimeType: 'audio/wav'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
	});

	it('should return mapping based on mimeType prefix (video)', () => {
		expect(getMimeType({mimeType: 'video/mp4'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
		expect(getMimeType({mimeType: 'video/webm'})).toEqual(
			STRUCTURES_MAP.CMSDocumentMultimedia
		);
	});

	it('should return default mapping for unknown mimeType and unknown prefix', () => {
		expect(getMimeType({mimeType: 'unknown/type'})).toEqual(
			STRUCTURES_MAP.CMSDocumentDefault
		);
	});

	it('should handle mimeType without slash by using it as prefix', () => {
		// Even if it's not a standard mime type, the logic uses the first part.
		expect(getMimeType({mimeType: 'image'})).toEqual(
			STRUCTURES_MAP.CMSDocumentImage
		);
	});
});
