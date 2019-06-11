import {Ticket} from './ticket';
import {Passenger} from '../../account/profile/shared/model/passenger';

export class Reservation {
  id: BigInteger;
  ticket: Ticket;
  passenger: Passenger;
  reservationDate: Date;
  deleted: boolean;
}
