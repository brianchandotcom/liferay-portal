export enum ClassName {
	BasicWebContent = 'basic-web-content',
	Blog = 'blog',
	CustomStructure = 'custom-structure',
	DocumentCode = 'document-code',
	DocumentCompressed = 'document-compressed',
	DocumentDefault = 'document-default',
	DocumentImage = 'document-image',
	DocumentMultimedia = 'document-multimedia',
	DocumentPresentation = 'document-presentation',
	DocumentTable = 'document-table',
	DocumentText = 'document-text',
	DocumentVector = 'document-vector',
	FileIconColor0 = 'file-icon-color-0',
	Folder = 'folder',
	KnowledgeBase = 'knowledge-base',
}

export const STRUCTURES_MAP: Record<
	string,
	{className: ClassName; icon: string}
> = {
	blog: {
		className: ClassName.Blog,
		icon: 'blogs',
	},
	CMSBasicDocument: {
		className: ClassName.DocumentDefault,
		icon: 'document-text',
	},
	CMSBasicWebContent: {
		className: ClassName.BasicWebContent,
		icon: 'forms',
	},
	CMSBlog: {
		className: ClassName.Blog,
		icon: 'blogs',
	},
	CMSCustomStructure: {
		className: ClassName.CustomStructure,
		icon: 'web-content',
	},

	CMSDocumentCode: {
		className: ClassName.DocumentCode,
		icon: 'code',
	},
	CMSDocumentCompressed: {
		className: ClassName.DocumentCompressed,
		icon: 'document-compressed',
	},
	CMSDocumentDefault: {
		className: ClassName.DocumentDefault,
		icon: 'document-default',
	},
	CMSDocumentImage: {
		className: ClassName.DocumentImage,
		icon: 'document-image',
	},
	CMSDocumentMultimedia: {
		className: ClassName.DocumentMultimedia,
		icon: 'document-multimedia',
	},
	CMSDocumentPresentation: {
		className: ClassName.DocumentPresentation,
		icon: 'document-presentation',
	},
	CMSDocumentTable: {
		className: ClassName.DocumentTable,
		icon: 'document-table',
	},
	CMSDocumentText: {
		className: ClassName.DocumentText,
		icon: 'document-text',
	},
	CMSDocumentVector: {
		className: ClassName.DocumentVector,
		icon: 'document-vector',
	},

	CMSExternalVideo: {
		className: ClassName.DocumentImage,
		icon: 'document-multimedia',
	},

	CMSFolder: {
		className: ClassName.Folder,
		icon: 'folder',
	},
	CMSKnowledgeBase: {
		className: ClassName.KnowledgeBase,
		icon: 'wiki',
	},
	Default: {
		className: ClassName.FileIconColor0,
		icon: 'document-default',
	},
	document: {
		className: ClassName.DocumentDefault,
		icon: 'document-text',
	},
	form: {
		className: ClassName.DocumentText,
		icon: 'forms',
	},
	webContent: {
		className: ClassName.BasicWebContent,
		icon: 'forms',
	},
};

export const FILE_MIME_MAP: Record<
	string,
	{className: ClassName; icon: string}
> = {
	'application/pdf': STRUCTURES_MAP['CMSDocumentVector'],
	audio: STRUCTURES_MAP['CMSDocumentMultimedia'],
	default: STRUCTURES_MAP['CMSDocumentDefault'],
	image: STRUCTURES_MAP['CMSDocumentImage'],
	video: STRUCTURES_MAP['CMSDocumentMultimedia'],
	...Object.fromEntries(
		[
			'application/javascript',
			'text/asp',
			'text/css',
			'text/ecmascript',
			'text/html',
			'text/javascript',
			'text/x-c',
			'text/x-fortran',
			'text/x-java-source',
			'text/x-jsp',
			'text/x-pascal',
			'text/x-script.perl',
			'text/x-script.perl-module',
			'text/xml',
		].map((mime) => [mime, STRUCTURES_MAP['CMSDocumentCode']])
	),
	...Object.fromEntries(
		[
			'application/x-7z-compressed',
			'application/x-ace-compressed',
			'application/x-compressed',
			'application/x-rar-compressed',
			'application/x-zip-compressed',
			'application/zip',
		].map((mime) => [mime, STRUCTURES_MAP['CMSDocumentCompressed']])
	),
	'application/vnd+liferay.video.external.shortcut+html':
		STRUCTURES_MAP['CMSDocumentMultimedia'],
	...Object.fromEntries(
		[
			'application/mspowerpoint',
			'application/powerpoint',
			'application/vnd.apple.keynote',
			'application/vnd.ms-powerpoint',
			'application/vnd.oasis.opendocument.presentation',
			'application/vnd.openxmlformats-officedocument.presentationml.presentation',
			'application/x-mspowerpoint',
		].map((mime) => [mime, STRUCTURES_MAP['CMSDocumentPresentation']])
	),
	...Object.fromEntries(
		[
			'application/excel',
			'application/vnd.ms-excel',
			'application/vnd.oasis.opendocument.spreadsheet',
			'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			'application/vnd.sun.xml.calc',
			'application/x-excel',
			'application/x-msexcel',
		].map((mime) => [mime, STRUCTURES_MAP['CMSDocumentTable']])
	),
	...Object.fromEntries(
		[
			'application/msword',
			'application/vnd.oasis.opendocument.text',
			'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
			'text/plain',
		].map((mime) => [mime, STRUCTURES_MAP['CMSDocumentText']])
	),
};
