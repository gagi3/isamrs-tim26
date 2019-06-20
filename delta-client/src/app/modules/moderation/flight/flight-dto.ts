import {PlaceAndTimeDTO} from './place-and-time-dto';

export class FlightDTO {
  airplaneID: BigInteger;
  departure: PlaceAndTimeDTO = new PlaceAndTimeDTO();
  arrival: PlaceAndTimeDTO = new PlaceAndTimeDTO();
  transfers: PlaceAndTimeDTO[] = [];
  distance: number;
  travelTime: number;
  generateTickets: boolean;
}
