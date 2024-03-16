import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarNavigationItemComponent } from './side-bar-navigation-item.component';

describe('SideBarNavigationItemComponent', () => {
  let component: SideBarNavigationItemComponent;
  let fixture: ComponentFixture<SideBarNavigationItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SideBarNavigationItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SideBarNavigationItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
