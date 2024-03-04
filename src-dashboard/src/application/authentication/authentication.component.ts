import { Component } from '@angular/core';
import {MatCard, MatCardContent} from "@angular/material/card";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    RouterOutlet
  ],
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.scss'
})
export class AuthenticationComponent {

}
