export const getSearchParams = <T>(object: T) => {
  const searchParams = new URLSearchParams();

  for (const key in object) {
    searchParams.set(key, object[key] as string);
  }

  return searchParams.toString();
};
