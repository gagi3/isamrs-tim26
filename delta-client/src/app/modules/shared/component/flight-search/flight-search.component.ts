import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FlightSearchDTO} from '../../flight-search-dto';
import {Flight} from "../../model/flight";
import {FlightService} from "../../../moderation/flight/flight.service";
import {Router} from "@angular/router";
import {TokenStorageService} from "../../token-storage.service";
import {ProfileService} from "../../../account/profile/shared/service/profile.service";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrls: ['./flight-search.component.css']
})
export class FlightSearchComponent implements OnInit {
  filterSearch: FlightSearchDTO = new FlightSearchDTO(null, null, null, null, null, null, null, null);
  result: Flight[];
  @Input() flightTable: any;
  @Output() filter = new EventEmitter<Flight[]>();

  constructor(private service: FlightService, private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) { }

  ngOnInit() {
  }
  loadFlights() {
    this.service.filterSearch(this.filterSearch).subscribe(
      data => {
        this.result = data;
        this.flightTable.loadSearchFilter(this.result);
        this.filter.emit(this.result);
      }
    );
  }
  reset() {
    this.filterSearch = new FlightSearchDTO(null, null, null, null, null, null, null, null);
  }

}
