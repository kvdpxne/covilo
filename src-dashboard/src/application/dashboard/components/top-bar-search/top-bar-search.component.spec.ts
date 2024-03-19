import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopBarSearchComponent } from './top-bar-search.component';

describe('TopBarSearchComponent', () => {
  let component: TopBarSearchComponent;
  let fixture: ComponentFixture<TopBarSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopBarSearchComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TopBarSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
