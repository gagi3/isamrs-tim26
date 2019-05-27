import {AirlineCompany} from './airline-company';
import {Seat} from './seat';

export class Airplane {
  id: BigInteger;
  airlineCompany: AirlineCompany;
  name: string;
  seats: Seat[];
  deleted: boolean;
}
