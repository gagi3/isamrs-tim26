import {Flight} from './flight';
import {Ticket} from './ticket';
import {Airplane} from './airplane';

export class AirlineCompany {
  id: BigInteger;
  name: string;
  address: string;
  description: string;
  destinations: string[];
  flights: Flight[];
  discountedTickets: Ticket[];
  airplanes: Airplane[];
  deleted: boolean;
}
