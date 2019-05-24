import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {PriceListService} from '../price-list.service';
import {PriceListDTO} from '../price-list-dto';

@Component({
  selector: 'app-add-price-list',
  templateUrl: './add-price-list.component.html',
  styleUrls: ['./add-price-list.component.css']
})
export class AddPriceListComponent implements OnInit {
  DTO: PriceListDTO = new PriceListDTO();
  failed = false;
  success = false;
  errorMessage = '';

  constructor(private router: Router, private priceListService: PriceListService) { }

  cancel() {
    this.router.navigateByUrl('');
  }
  ngOnInit() {
  }
  onSubmit() {
    this.priceListService.create(this.DTO).subscribe(
      data => {
        window.alert('Price list added successfully!');
        this.success = true;
        this.failed = false;
        this.router.navigateByUrl('');
      }, error => {
        console.log(error);
        this.errorMessage = error.errorMessage;
        this.success = false;
        this.failed = true;
      }
    );
  }

}
