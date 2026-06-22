/**
 * Executes validator functions in sequence, short-circuiting on the first
 * truthy resolved value (the first validation error).
 *
 * Designed for Formik v2 field validators: each validator resolves with
 * an error message string (or a falsy value when valid). Returns a validator
 * that resolves with the first error, or with a falsy value if all pass.
 */
export function sequence(fns: Array<(value: any) => Promise<any>>) {
	return (value?: any) =>
		fns.reduce(
			(result, fn) => result.then(err => err || fn(value)),
			Promise.resolve<any>(undefined)
		);
}
