import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Matchpage } from './matchpage';

describe('Matchpage', () => {
  let component: Matchpage;
  let fixture: ComponentFixture<Matchpage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Matchpage],
    }).compileComponents();

    fixture = TestBed.createComponent(Matchpage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
