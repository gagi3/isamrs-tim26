import {Ticket} from '../../shared/model/ticket';

export class ReservationDTO {
  ticket: Ticket = new Ticket();
  luggage = 0;
}
