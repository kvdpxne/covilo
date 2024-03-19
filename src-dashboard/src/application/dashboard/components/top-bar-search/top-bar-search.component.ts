import { Component } from '@angular/core';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";

@Component({
  selector: 'app-top-bar-search',
  standalone: true,
  imports: [
    MatFormField,
    MatInput,
    MatLabel
  ],
  templateUrl: './top-bar-search.component.html',
  styleUrl: './top-bar-search.component.scss'
})
export class TopBarSearchComponent {

}
