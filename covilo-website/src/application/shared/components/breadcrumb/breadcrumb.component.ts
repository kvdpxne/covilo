import {Component, Input} from "@angular/core";
import {Link} from "../../types/link";
import { NgFor } from "@angular/common";

@Component({
    selector: "breadcrumb",
    templateUrl: "./breadcrumb.component.html",
    styleUrls: ["./breadcrumb.component.scss"],
    standalone: true,
    imports: [NgFor]
})
export class BreadcrumbComponent {

  @Input()
  public content?: Link[];
}
