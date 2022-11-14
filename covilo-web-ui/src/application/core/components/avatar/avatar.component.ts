import { Component, OnInit } from "@angular/core"
import { StorageService } from "../../../authentication"
import { User } from "../../models/user"

@Component({
  selector: "a-avatar",
  templateUrl: "./avatar.component.html",
  styleUrls: ["./avatar.component.scss"]
})
export class AvatarComponent implements OnInit {

  currentUser?: User | null
  isLogged: boolean

  constructor(private readonly storageService: StorageService) {
    this.isLogged = false
  }

  ngOnInit(): void {
    if (this.storageService.isLogged()) {
      this.isLogged = true
      this.currentUser = this.storageService.getUser()
    }
  }
}
