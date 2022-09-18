import { Link } from "./link"

export class Section {

  constructor(
    public readonly name: string,
    public readonly linkArray: Array<Link>
  ) {
  }
}
