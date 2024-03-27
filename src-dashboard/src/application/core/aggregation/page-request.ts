/**
 * Represents a request for a specific page of data.
 */
export interface PageRequest {

  /**
   * The number of the requested page.
   */
  pageNumber: number;

  /**
   * The size of the requested page.
   * It specifies the maximum number of elements to be included in the page.
   * Additional constraints: pageSize cannot be greater than 100.
   */
  pageSize: number;
}