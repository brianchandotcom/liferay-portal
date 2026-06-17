export const escapeGroup = (group: string) =>
	group.replace(/([=!:$/()])/g, '\\$1');

export const escapeString = (str: string) =>
	str.replace(/([.+*?=^!:${}()[\]|/\\])/g, '\\$1');

export type PathToken =
	| string
	| {
			delimiter: string;
			name: string | number;
			optional: boolean;
			partial: boolean;
			pattern: string;
			prefix: string;
			repeat: boolean;
	  };

export const parse = (str: string): PathToken[] => {
	const tokens: PathToken[] = [];
	let key = 0;
	let index = 0;
	let path = '';

	const defaultDelimiter = '/';

	const PATH_REGEXP = new RegExp(
		[
			'(\\\\.)',
			'([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?)',
		].join('|'),
		'g'
	);

	let match;

	while ((match = PATH_REGEXP.exec(str)) !== null) {
		const [m, escaped, prefix = '', name, capture, group, modifier] = match;
		const offset = match.index;

		path += str.slice(index, offset);
		index = offset + m.length;

		if (escaped) {
			path += escaped[1];

			continue;
		}

		if (path) {
			tokens.push(path);

			path = '';
		}

		const repeat = modifier === '+' || modifier === '*';
		const optional = modifier === '?' || modifier === '*';
		const partial = prefix !== '';

		tokens.push({
			delimiter: prefix || defaultDelimiter,
			name: name || key++,
			optional,
			partial,
			pattern: capture
				? escapeGroup(capture)
				: group
					? escapeGroup(group)
					: `[^${escapeString(defaultDelimiter)}]+?`,
			prefix,
			repeat,
		});
	}

	if (path || index < str.length) {
		tokens.push(path + str.slice(index));
	}

	return tokens;
};

const tokensToFunction =
	(tokens: PathToken[]) =>
	(obj: Record<string, string | number | Array<string | number>>) => {
		let path = '';

		tokens.forEach((token: PathToken) => {
			if (typeof token === 'string') {
				path += token;

				return;
			}

			const value = obj[token.name];

			if (value == null) {
				if (token.optional) {
					return;
				}

				throw new TypeError(`Expected "${token.name}" to be defined`);
			}

			const pattern = new RegExp(`^${token.pattern}$`);

			if (Array.isArray(value)) {
				if (!token.repeat) {
					throw new TypeError(
						`Expected "${token.name}" to not repeat`
					);
				}

				if (value.length === 0) {
					if (token.optional) {
						return;
					}

					throw new TypeError(
						`Expected "${token.name}" to not be empty`
					);
				}

				value.forEach((segmentValue, index) => {
					const segment = encodeURIComponent(segmentValue);

					if (!pattern.test(segment)) {
						throw new TypeError(
							`Expected all "${token.name}" to match "${token.pattern}", but received "${segment}"`
						);
					}

					const prefix = index === 0 ? token.prefix : token.delimiter;

					path += prefix + segment;
				});

				return;
			}

			const segment = encodeURIComponent(String(value));

			if (!pattern.test(segment)) {
				throw new TypeError(
					`Expected "${token.name}" to match "${token.pattern}", but received "${segment}"`
				);
			}

			path += token.prefix + segment;
		});

		return path;
	};

export const compile = (path: string) => {
	const tokens = parse(path);

	return tokensToFunction(tokens);
};
