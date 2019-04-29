import {SeatClass} from '../enumeration/seat-class.enum';
import {Airplane} from './airplane';

export class Seat {
  id: BigInteger;
  row: BigInteger;
  column: BigInteger;
  class: SeatClass;
  airplane: Airplane;
  deleted: boolean;
}
