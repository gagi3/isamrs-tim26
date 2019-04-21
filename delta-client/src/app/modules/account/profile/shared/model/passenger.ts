import {Ticket} from '../../../../shared/ticket';


export class Passenger {
  id: BigInteger;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  city: string;
  phoneNumber: string;
  activated: boolean;
  roles: string[];
  tickets: Ticket[];
  friends: Passenger[];
  deleted: boolean;
  token: string;
}
