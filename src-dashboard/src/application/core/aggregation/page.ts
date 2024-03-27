/**
 * Represents a paginated collection of items.
 * @template T The type of items contained in the page.
 */
export interface Page<T> {

  /**
   * The content of the current page.
   */
  content: Array<T>;

  /**
   * The page number.
   */
  pageNumber: number;

  /**
   * The size of the page.
   */
  pageSize: number;

  /**
   * The total number of pages.
   */
  totalPages: number;

  /**
   * The total number of elements across all pages.
   */
  totalElements: number;

  /**
   * Indicates whether there is a next page available.
   */
  hasNext: boolean;

  /**
   * Indicates whether there is a previous page available.
   */
  hasPrevious: boolean;

  /**
   * Indicates whether the current page is the first page.
   */
  isFirst: boolean;

  /**
   * Indicates whether the current page is the last page.
   */
  isLast: boolean;
}