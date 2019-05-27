import {Flight} from './flight';
import {Seat} from './seat';
import {Passenger} from '../../account/profile/shared/model/passenger';

export class Ticket {
  id: BigInteger;
  flight: Flight;
  seat: Seat;
  price: number;
  passenger: Passenger;
  deleted: boolean;
}
