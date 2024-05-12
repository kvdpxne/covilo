/**
 * Constructs a query string from the provided source object.
 *
 * @param source The object containing key-value pairs to be converted into a query string.
 * @returns The constructed query string.
 */
const useParameters = (source: [key: any]): string => {
  const keys: string[] = Object.keys(source);
  if (0 === keys.length) {
    return "";
  }
  let query: string = "?";
  keys.forEach((key: string): void => {
    const value = source[key];
    if (undefined === typeof value || null === value) {
      return;
    }
    query += `&${key}=${value}`;
  });
};

/**
 * Exports the useParameters function.
 */
export {
  useParameters
};