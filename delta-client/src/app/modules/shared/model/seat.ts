import {SeatClass} from '../enumeration/seat-class.enum';
import {Airplane} from './airplane';

export class Seat {
  id: BigInteger;
  row: number;
  column: number;
  seatClass: SeatClass;
  airplane: Airplane;
  deleted: boolean;
}
