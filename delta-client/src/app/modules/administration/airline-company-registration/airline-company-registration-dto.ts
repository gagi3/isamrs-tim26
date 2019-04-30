import {RegisterDTO} from '../../account/register/register.dto';

export class AirlineCompanyRegistrationDTO {
  name = '';
  address = '';
  description = '';
  destinations: string[] = [''];
  priceByKm: number;
  luggagePriceByItem: number;
  admin: RegisterDTO = new RegisterDTO();
}
