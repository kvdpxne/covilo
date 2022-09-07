import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textFilter',
  pure: false
})
export class TextFilterPipe implements PipeTransform {

  transform(items?: any[], search?: string): any[] {
    if (!items) {
      return []
    }
    // return the original array if search text is undefined or empty
    if (!search || 0 === search.length) {
      return items
    }

    search = search.toLowerCase()
    return items.filter(item => item.toLowerCase().includes(search))
  }
}
