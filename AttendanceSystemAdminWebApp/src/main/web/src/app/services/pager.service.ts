/***************************
 * pagging details
 ***************************/
export class PagerService {
  getPager(totalItems: number, currentPage: number, pageSize: number) {
    let totalPages = Math.ceil(totalItems / pageSize); //2
    let startPage: number, endPage: number;
    if (totalPages <= 10) {
      startPage = 1;
      endPage = totalPages;
    } else {
      if (currentPage <= 6) {
        startPage = 1;
        // endPage = 10;
        endPage = 5;
      } else if (currentPage + 4 >= totalPages) {
        startPage = totalPages - 9;
        endPage = totalPages;
      } else {
        startPage = currentPage - 5;
        endPage = currentPage + 4;
      }
    }
    let startIndex = ((currentPage - 1) * pageSize) + 1; //1
    let endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1); //4
    return {
      totalItems: totalItems,
      currentPage: currentPage,
      pageSize: pageSize,
      totalPages: totalPages,
      startPage: startPage,
      endPage: endPage,
      startIndex: startIndex,
      endIndex: endIndex
    };
  }
}
