import {Ticket} from '../../../../shared/model/ticket';


export class Passenger {
  id: BigInteger;
  username: '';
  password: '';
  firstName: '';
  lastName: '';
  city: '';
  phoneNumber: '0000000000';
  activated: true;
  roles: string[];
  tickets: Ticket[];
  friends: Passenger[];
  deleted: false;
  token: '';
}
