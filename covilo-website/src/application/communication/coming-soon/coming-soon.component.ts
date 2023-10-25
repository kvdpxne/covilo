import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription, timer} from "rxjs";

@Component({
  selector: "router-coming-soon",
  templateUrl: "./coming-soon.component.html"
})
export class ComingSoonComponent implements OnInit, OnDestroy {

  private subscription?: Subscription;

  /**
   * Countdown start time.
   */
  private readonly startDate: Date;

  /**
   * Countdown end time.
   */
  private readonly endTime: number;

  /**
   * Remaining days until the end time.
   */
  public leftDays: number;

  /**
   * Remaining hours until the end time.
   */
  public leftHours: number;

  /**
   * Remaining minutes until the end time.
   */
  public leftMinutes: number;

  /**
   * Remaining seconds until the end time.
   */
  public leftSeconds: number;

  public constructor() {
    this.startDate = new Date();
    this.endTime = this.startDate.setMonth(6 + this.startDate.getMonth());

    this.leftDays = this.leftHours = this.leftMinutes = this.leftSeconds = 0;
  }

  public ngOnInit(): void {
    this.subscription = timer(0, 1000).subscribe((): void => {
      // The time difference between the current time and the end time.
      const now: number = this.endTime - Date.now();

      this.leftDays = Math.floor(now / (1000 * 60 * 60 * 24));
      this.leftHours = Math.floor(now / (1000 * 60 * 60) % 24);
      this.leftMinutes = Math.floor(now / (1000 * 60) % 60);
      this.leftSeconds = Math.floor(now / 1000 % 60);
    });
  }

  public ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
