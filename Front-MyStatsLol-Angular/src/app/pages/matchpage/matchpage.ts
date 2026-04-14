import { ChangeDetectorRef, Component, signal } from '@angular/core';
import { PlayerService } from '../../services/playerService';


@Component({
  selector: 'app-matchpage',
  standalone: true,
  templateUrl: './matchpage.html',
  styleUrl: './matchpage.css',
})
export class Matchpage {
  result: string | null = null;
  constructor(
    private testService: PlayerService,
    private cdr: ChangeDetectorRef,
  ) {}

  retrievePlayer(puuid : string) {
    this.testService.getPlayerByPuuid(puuid).subscribe((res) => {
      this.result = res;
      this.cdr.detectChanges();
      console.log('result : ' + this.result);
    });
  }
}
