import {AirlineCompany} from '../../../../shared/model/airline-company';

export class AirlineCompanyAdmin {
  id: BigInteger;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  city: string;
  phoneNumber: string;
  activated: boolean;
  roles: string[];
  deleted: boolean;
  airlineCompany: AirlineCompany;
}
