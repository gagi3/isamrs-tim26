import {SeatDTO} from './seat-dto';

export class AirplaneDTO {
  companyID: BigInteger;
  name: string;
  seats: SeatDTO[];
}
