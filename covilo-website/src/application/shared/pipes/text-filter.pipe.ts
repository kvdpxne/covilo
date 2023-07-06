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
      if ("nationalName" in item) {
        return item.nationalName.toLocaleLowerCase().includes(search)
      }
      if ("name" in item) {
        return item.name.toLocaleLowerCase().includes(search)
      }
      return item.toLowerCase().includes(search)
    })
  }
}
