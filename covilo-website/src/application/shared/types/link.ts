export class Link {

  constructor(
    public readonly name: string,
    public readonly reference: string
  ) {
  }
}

export const buildLinkArrayWithChildren = (root: string, paths: string[]): Link[] => {
  return paths.map(path => new Link(
    path,
    root + "/" + path
  ));
};
