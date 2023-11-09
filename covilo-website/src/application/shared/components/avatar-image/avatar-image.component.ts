import {Component, Input, OnInit} from "@angular/core";
import {User, UserLifecycleService} from "../../../core";
import {environment} from "../../../../environments/environment";

@Component({
  selector: "avatar-image",
  templateUrl: "./avatar-image.component.html"
})
export class AvatarImageComponent
  implements OnInit {

  @Input()
  public size?: number;

  private readonly userLifecycleService: UserLifecycleService;
  public avatarReference?: string;

  public constructor(userLifecycleService: UserLifecycleService) {
    this.userLifecycleService = userLifecycleService;
  }

  private getUrlToUserAvatar(user: User): string {
    return `${environment.resourceUrl}/avatars/${user.identifier}.webp`;
  }

  private getUrlToUserDefaultAvatar(): string {
    return `${environment.resourceUrl}/avatars/default_user_avatar.svg`;
  }

  public handleNotFoundImage(): void {
    this.avatarReference = this.getUrlToUserDefaultAvatar();
  }

  public ngOnInit(): void {
    this.userLifecycleService.user.subscribe({
      next: (user: User): void => {
        this.avatarReference = this.getUrlToUserAvatar(user);
      },
      error: (): void => this.handleNotFoundImage()
    });
  }
}
