import {City} from "./city";
import {User} from "./user";
import {Category} from "./category";
import {Identifiable} from "../aggregation/identifiable";

export class Crime implements Identifiable {

  /**
   * A random unique identifier.
   */
  public readonly identifier: string;

  /**
   * Additional title of the committed crime.
   */
  public readonly title: string;

  /**
   * Additional description of the committed crime.
   */
  public readonly description: string;

  /**
   * Categories into which the crime has been categorized.
   */
  public readonly categories: Category[];

  /**
   * The date and time the crime was committed.
   */
  public readonly time: Date;

  /**
   * The place where the crime was committed .
   */
  public readonly place: City;

  /**
   * The user who reported the crime.
   */
  public readonly reporter: User;

  /**
   * Whether the crime is confirmed.
   */
  public readonly confirmed: boolean;

  public constructor(
    identifier: string,
    title: string,
    description: string,
    categories: Category[],
    time: Date,
    place: City,
    reporter: User,
    confirmed: boolean
  ) {
    this.identifier = identifier;
    this.title = title;
    this.description = description;
    this.categories = categories;
    this.time = time;
    this.place = place;
    this.reporter = reporter;
    this.confirmed = confirmed;
  }
}
