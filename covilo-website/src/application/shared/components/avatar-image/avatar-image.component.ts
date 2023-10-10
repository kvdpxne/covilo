import {Component, Input, OnInit} from "@angular/core";
import {UserService} from "../../../core/service/user.service";
import {User} from "../../../core/model/user";
import {environment} from "../../../../environments/environment";

@Component({
  selector: "avatar-image",
  templateUrl: "./avatar-image.component.html"
})
export class AvatarImageComponent implements OnInit {

  @Input()
  public size?: number;

  private readonly userService: UserService;
  private user?: User;

  public avatarReference?: string;

  public constructor(userService: UserService) {
    this.userService = userService;
  }

  private getUrlToUserAvatar(user: User): string {
    return `${environment.resourceUrl}/avatars/${user.identifier}.png`;
  }

  private getUrlToUserDefaultAvatar(): string {
    return `${environment.resourceUrl}/avatars/default_user_avatar.svg`;
  }

  public handleNotFoundImage(): void {
    this.avatarReference = this.getUrlToUserDefaultAvatar();
  }

  public ngOnInit(): void {
    this.userService.getMe().subscribe({
      next: (user: User): void => {
        this.user = user;
      },
      error: (): void => {
        this.handleNotFoundImage();
      },
      complete: (): void => {
        if (this.user) {
          this.avatarReference = this.getUrlToUserAvatar(this.user);
          return;
        }
        this.handleNotFoundImage();
      }
    });
  }
}
