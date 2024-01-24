import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BootstrapIconComponent } from './bootstrap-icon.component';

describe('BootstrapIconComponent', () => {
  let component: BootstrapIconComponent;
  let fixture: ComponentFixture<BootstrapIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BootstrapIconComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BootstrapIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
