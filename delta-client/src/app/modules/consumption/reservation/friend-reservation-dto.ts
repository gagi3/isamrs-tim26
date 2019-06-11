import {Ticket} from '../../shared/model/ticket';
import {Passenger} from '../../account/profile/shared/model/passenger';

export class FriendReservationDTO {
  ticket: Ticket;
  passenger: Passenger;
  luggage: number;
}
