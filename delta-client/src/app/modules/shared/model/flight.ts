import {AirlineCompany} from './airline-company';
import {Airplane} from './airplane';
import {PlaceAndTime} from './place-and-time';
import {Ticket} from './ticket';

export class Flight {
  id: BigInteger;
  airlineCompany: AirlineCompany;
  airplane: Airplane;
  departure: PlaceAndTime;
  transfers: PlaceAndTime[];
  arrival: PlaceAndTime;
  distance: number;
  travelTime: number;
  tickets: Ticket[];
  deleted: boolean;
}
