import {Component, Input} from "@angular/core";
import {Link} from "../../types/link";

@Component({
  selector: "breadcrumb",
  templateUrl: "./breadcrumb.component.html",
  styleUrls: ["./breadcrumb.component.scss"]
})
export class BreadcrumbComponent {

  @Input()
  public content?: Link[];
}
