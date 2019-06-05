import {SeatClass} from '../enumeration/seat-class.enum';
import {Airplane} from './airplane';

export class Seat {
  id: BigInteger;
  row: number;
  column: number;
  class: SeatClass;
  airplane: Airplane;
  deleted: boolean;
}
