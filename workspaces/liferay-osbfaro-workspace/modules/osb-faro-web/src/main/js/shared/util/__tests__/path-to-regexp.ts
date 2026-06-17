import {
	compile,
	escapeGroup,
	escapeString,
	parse,
	PathToken,
} from 'shared/util/path-to-regexp';

type PathTokenObject = Exclude<PathToken, string>;

describe('Escape Functions', () => {
	it('escapes special group characters', () => {
		const input = 'a=b!c:d$e/f(g)';
		const expected = 'a\\=b\\!c\\:d\\$e\\/f\\(g\\)';

		expect(escapeGroup(input)).toBe(expected);
	});

	it('returns the string unchanged if no special characters are present', () => {
		const input = 'abc-123_xyz';

		expect(escapeGroup(input)).toBe(input);
	});

	it('escapes special regex characters', () => {
		const input = '.+*?=^!:${}[]|/\\()';
		const expected =
			'\\.\\+\\*\\?\\=\\^\\!\\:\\$\\{\\}\\[\\]\\|\\/\\\\\\(\\)';

		expect(escapeString(input)).toBe(expected);
	});

	it('returns the string unchanged if no special characters are present', () => {
		const input = 'abc-123_xyz';

		expect(escapeString(input)).toBe(input);
	});
});

describe('parse(str)', () => {
	it('correctly parse a static route', () => {
		const route = '/users/profile';

		expect(parse(route)).toEqual(['/users/profile']);
	});

	it('parses a simple named parameter', () => {
		const route = '/users/:id';

		expect(parse(route)).toEqual([
			'/users',
			{
				delimiter: '/',
				name: 'id',
				optional: false,
				partial: true,
				pattern: '[^\\/]+?',
				prefix: '/',
				repeat: false,
			},
		]);
	});

	it('parses an optional parameter', () => {
		const route = '/users/:id?';

		expect((parse(route)[1] as PathTokenObject).optional).toBe(true);
	});

	it('parses a repeatable parameter (+)', () => {
		const route = '/files/:path+';

		expect((parse(route)[1] as PathTokenObject).repeat).toBe(true);
		expect((parse(route)[1] as PathTokenObject).optional).toBe(false);
	});

	it('parses an optional and repeatable parameter (*)', () => {
		const route = '/files/:path*';

		expect((parse(route)[1] as PathTokenObject).repeat).toBe(true);
		expect((parse(route)[1] as PathTokenObject).optional).toBe(true);
	});

	it('parses a parameter with a custom capture pattern', () => {
		const route = '/item/:id(\\d{4})';

		expect(parse(route)).toEqual([
			'/item',
			{
				delimiter: '/',
				name: 'id',
				optional: false,
				partial: true,
				pattern: '\\d{4}',
				prefix: '/',
				repeat: false,
			},
		]);
	});

	it('parses an unnamed capture group', () => {
		const route = '/group/(users|admins)';
		const result = parse(route);

		expect(result).toHaveLength(2);
		expect(result[1]).toMatchObject({
			name: 0,
			optional: false,
			pattern: 'users|admins',
		});
	});

	it('handles delimiters other than "/"', () => {
		const route = '/prefix.:ext';

		expect(parse(route)).toEqual([
			'/prefix',
			{
				delimiter: '.',
				name: 'ext',
				optional: false,
				partial: true,
				pattern: '[^\\/]+?',
				prefix: '.',
				repeat: false,
			},
		]);
	});

	it('handles multiple parameters and static tokens', () => {
		const route = '/:lang/book/:id-:slug?';

		expect(parse(route)).toEqual([
			{
				delimiter: '/',
				name: 'lang',
				optional: false,
				partial: true,
				pattern: '[^\\/]+?',
				prefix: '/',
				repeat: false,
			},
			'/book',
			{
				delimiter: '/',
				name: 'id',
				optional: false,
				partial: true,
				pattern: '[^\\/]+?',
				prefix: '/',
				repeat: false,
			},
			'-',
			{
				delimiter: '/',
				name: 'slug',
				optional: true,
				partial: false,
				pattern: '[^\\/]+?',
				prefix: '',
				repeat: false,
			},
		]);
	});

	it('ignores parameters escaped with a backslash', () => {
		const route = '/api/\\:version/resource';

		expect(parse(route)).toEqual(['/api/:version/resource']);
	});
});

describe('compile(path) and tokensToFunction', () => {
	it('compiles a static route', () => {
		const toPath = compile('/users/list');

		expect(toPath({})).toBe('/users/list');
	});

	it('compiles a route with a simple parameter', () => {
		const toPath = compile('/users/:id');

		expect(toPath({id: 'john-doe'})).toBe('/users/john-doe');
	});

	it('URI encode the parameter value', () => {
		const toPath = compile('/item/:name');

		expect(toPath({name: 'Product with space'})).toBe(
			'/item/Product%20with%20space'
		);
	});

	it('throws an error for a missing required parameter', () => {
		const toPath = compile('/users/:id');

		expect(() => toPath({})).toThrow('Expected "id" to be defined');
	});

	it('does not throws an error for a missing optional parameter', () => {
		const toPath = compile('/users/:id?');

		expect(toPath({})).toBe('/users');
	});

	it('correctly compile a repeatable parameter (+)', () => {
		const toPath = compile('/files/:path+');

		expect(toPath({path: ['foo', 'bar']})).toBe('/files/foo/bar');
	});

	it('correctly compile an optional repeatable parameter (*)', () => {
		const toPath = compile('/files/:path*');

		expect(toPath({path: ['foo', 'bar']})).toBe('/files/foo/bar');
		expect(toPath({})).toBe('/files');
	});

	it('throws an error if an array is passed to a non-repeatable parameter', () => {
		const toPath = compile('/file/:name');

		expect(() => toPath({name: ['foo', 'bar']})).toThrow(
			'Expected "name" to not repeat'
		);
	});

	it('throws an error if the array for a repeatable parameter is empty (if not optional)', () => {
		const toPath = compile('/files/:path+');

		expect(() => toPath({path: []})).toThrow(
			'Expected "path" to not be empty'
		);
	});

	it('throws an error if an array value does not match the pattern (repeatable)', () => {
		const toPath = compile('/files/:path(foo|bar)+');

		expect(() => toPath({path: ['foo', 'baz']})).toThrow(
			/Expected all "path" to match "foo\|bar", but received "baz"/
		);
		expect(toPath({path: ['foo', 'bar']})).toBe('/files/foo/bar');
	});

	it('compiles with a custom prefix/delimiter', () => {
		const toPath = compile('/prefix.:ext');

		expect(toPath({ext: 'json'})).toBe('/prefix.json');
	});

	it('compiles a complex route with multiple parameters', () => {
		const toPath = compile('/:lang/book/:id-:slug?');
		const params = {
			id: 42,
			lang: 'en',
			slug: 'the-book',
		};

		expect(toPath(params)).toBe('/en/book/42-the-book');
	});

	it('compiles a complex route without the optional parameter', () => {
		const toPath = compile('/:lang/book/:id-:slug?');
		const params = {
			id: 101,
			lang: 'pt',
		};

		expect(toPath(params)).toBe('/pt/book/101-');
	});
});
