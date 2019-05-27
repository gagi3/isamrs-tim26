import {AirlineCompany} from '../../../../shared/model/airline-company';

export class AirlineCompanyAdmin {
  id: BigInteger;
  username: '';
  password: '';
  firstName: '';
  lastName: '';
  city: '';
  phoneNumber: '0000000000';
  activated: true;
  roles: string[];
  deleted: false;
  airlineCompany: AirlineCompany;
}
