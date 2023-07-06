import { Component, OnInit } from '@angular/core';
import { StorageService } from "../authentication"

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  constructor(
    private readonly tokenStorageService: StorageService
  ) { }

  ngOnInit(): void {
  }

}
