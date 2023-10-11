import {Injectable} from "@angular/core";
import {BehaviorSubject, filter, Observable, Subject} from "rxjs";
import {map} from "rxjs/operators";
import {User} from "../model/user";

@Injectable({
  providedIn: "root"
})
export class UserLifecycleService {

  private readonly userSubject: Subject<User | null>;

  public constructor() {
    this.userSubject = new BehaviorSubject<User | null>(null);
  }

  get user(): Observable<User> {
    return this.userSubject.asObservable().pipe(
      filter((user: User | null): boolean => user !== null),
      map((user: User | null): User => user!!)
    );
  }

  public addUser(user: User): void {
    this.userSubject.next(user);
    // console.debug("The user has been added to the lifecycle service.");
  }

  public removeUser(): void {
    this.userSubject.complete();
  }
}
