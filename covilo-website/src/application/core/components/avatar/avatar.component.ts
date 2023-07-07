import {Component} from "@angular/core";
import {StorageService} from "../../../shared/services/storage.service";
import {StorageKey} from "../../../shared/services/storage-key";

@Component({
  selector: "covilo-avatar",
  templateUrl: "./avatar.component.html",
  styleUrls: ["./avatar.component.scss"]
})
export class AvatarComponent {

  private readonly storageService: StorageService;

  public isLogged: boolean;

  public constructor(storageService: StorageService) {
    this.storageService = storageService;

    this.isLogged = storageService.has(StorageKey.TOKEN);
  }


}
