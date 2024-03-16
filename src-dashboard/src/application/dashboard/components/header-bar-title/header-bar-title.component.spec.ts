import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderBarTitleComponent } from './header-bar-title.component';

describe('HeaderBarTitleComponent', () => {
  let component: HeaderBarTitleComponent;
  let fixture: ComponentFixture<HeaderBarTitleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderBarTitleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HeaderBarTitleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
