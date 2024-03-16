import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderBarBreadcrumbsComponent } from './header-bar-breadcrumbs.component';

describe('HeaderBarBreadcrumbsComponent', () => {
  let component: HeaderBarBreadcrumbsComponent;
  let fixture: ComponentFixture<HeaderBarBreadcrumbsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderBarBreadcrumbsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HeaderBarBreadcrumbsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
