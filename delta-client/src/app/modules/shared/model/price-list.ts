import {AirlineCompany} from './airline-company';

export class PriceList {
  id: BigInteger;
  priceByKm: number;
  priceByLuggageItem: number;
  businessClassPriceCoefficient: number;
  economyClassPriceCoefficient: number;
  firstClassPriceCoefficient: number;
  discountPercentage: number;
  airlineCompany: AirlineCompany;
  deleted: boolean;
}
