import {RegisterDTO} from '../../account/register/register.dto';

export class AirlineCompanyRegistrationDTO {
  name = '';
  address = '';
  description = '';
  destinations: string[] = [''];
  admin: RegisterDTO = new RegisterDTO();
}
