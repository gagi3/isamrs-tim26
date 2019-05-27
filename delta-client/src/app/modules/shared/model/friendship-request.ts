import {Passenger} from '../../account/profile/shared/model/passenger';

export class FriendshipRequest {
  id: BigInteger;
  sentFrom: Passenger;
  sentTo: Passenger;
  accepted: boolean;
  deleted: boolean;
}
