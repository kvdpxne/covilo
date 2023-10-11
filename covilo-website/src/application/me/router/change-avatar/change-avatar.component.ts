import {Component} from "@angular/core";
import {UserService} from "../../../core";

@Component({
  selector: "route-change-avatar",
  templateUrl: "./change-avatar.component.html"
})
export class ChangeAvatarComponent {

  private readonly userService: UserService;
  public file: File | null = null;

  constructor(userService: UserService) {
    this.userService = userService;
  }

  public onChange(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      this.file = file;
    }
  }

  public onUpload(): void {
    if (!this.file) {
      return;
    }

    const formData: FormData = new FormData();
    formData.append("file", this.file, this.file.name);

    this.userService.uploadAvatar(formData).subscribe();
  }

  public onDelete(): void {
    this.userService.deleteAvatar().subscribe();
  }

  public get lastModifiedDate(): Date {
    return new Date(this.file!.lastModified);
  }
}
