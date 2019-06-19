import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {PriceListService} from '../price-list.service';
import {PriceList} from '../../../shared/model/price-list';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

@Component({
  selector: 'app-edit-price-list',
  templateUrl: './edit-price-list.component.html',
  styleUrls: ['./edit-price-list.component.css']
})
export class EditPriceListComponent implements OnInit {
  @ViewChild('inputEl1') public inputEl1: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;
  @ViewChild('inputEl4') public inputEl4: ElementRef;
  @ViewChild('inputEl5') public inputEl5: ElementRef;
  @ViewChild('inputEl6') public inputEl6: ElementRef;
  priceListFound = false;
  show = 'priceList';
  priceList: PriceList = new PriceList();
  failed = false;
  success = false;
  errorMessage = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'edit-price-list';

  constructor(private router: Router, private priceListService: PriceListService) { }

  setPriceList(data: PriceList) {
    this.priceList = data;
  }
  getPriceList() {
    this.priceListService.getThis().subscribe(
      data => {
        this.setPriceList(data);
        this.priceList = data;
        if (typeof this.priceList !== 'undefined') {
          this.priceListFound = true;
        }
      }
    );
  }
  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.getPriceList();
  }
  onSubmit() {
    this.priceListService.update(this.priceList).subscribe(
      data => {
        window.alert('Price list edited successfully!');
        this.success = true;
        this.failed = false;
        this.router.navigateByUrl('/price-list/edit');
      }, error => {
        console.log(error);
        this.errorMessage = error.errorMessage;
        this.success = false;
        this.failed = true;
      }
    );
  }
  edit() {
    this.show = 'change';
    this.inputEl1.nativeElement.disabled = false;
    this.inputEl2.nativeElement.disabled = false;
    this.inputEl3.nativeElement.disabled = false;
    this.inputEl4.nativeElement.disabled = false;
    this.inputEl5.nativeElement.disabled = false;
    this.inputEl6.nativeElement.disabled = false;
  }
  cancel() {
    this.show = 'priceList';
    this.inputEl1.nativeElement.disabled = true;
    this.inputEl2.nativeElement.disabled = true;
    this.inputEl3.nativeElement.disabled = true;
    this.inputEl4.nativeElement.disabled = true;
    this.inputEl5.nativeElement.disabled = true;
    this.inputEl6.nativeElement.disabled = true;
  }
  onNavigate(feature: string) {
    console.log(feature);
    this.showView = feature;
    if (feature === 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['']);
      window.alert('Successfully Logged out!');
    }
  }

}
