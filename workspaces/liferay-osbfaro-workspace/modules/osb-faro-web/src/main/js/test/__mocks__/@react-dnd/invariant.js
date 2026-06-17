function invariant(condition, format, ...args) {
	if (!condition) {
		let error;

		if (format === undefined) {
			error = new Error('Invariant Violation');
		}
		else {
			let argIndex = 0;

			error = new Error(format.replace(/%s/g, () => args[argIndex++]));
			error.name = 'Invariant Violation';
		}

		error.framesToPop = 1;

		throw error;
	}
}

module.exports = {invariant};
