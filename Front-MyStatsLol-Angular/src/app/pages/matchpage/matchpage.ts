import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { TestService } from '../../services/testService';


@Component({
  selector: 'app-matchpage',
  standalone: true,
  templateUrl: './matchpage.html',
  styleUrl: './matchpage.css',
})
export class Matchpage {
  result: string | null = null;
  constructor(
    private testService: TestService,
    private cdr: ChangeDetectorRef,
  ) {}

  callTest() {
    this.testService.getTest().subscribe((res) => {
      this.result = res;
      this.cdr.detectChanges();
      console.log('result : ' + this.result);
    });
  }
}
