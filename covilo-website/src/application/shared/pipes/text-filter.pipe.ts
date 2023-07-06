import { Pipe, PipeTransform } from "@angular/core"

@Pipe({
  name: "textFilter",
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
    return items.filter(item => {
      if ("domesticName" in item) {
        return item.domesticName.toLocaleLowerCase().includes(search)
      }
      if ("key" in item) {
        return item.key.toLocaleLowerCase().includes(search)
      }
      return item.toLowerCase().includes(search)
    })
  }
}
