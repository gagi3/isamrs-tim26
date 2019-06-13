export class FlightSearchDTO {
  companyName: string;
  departurePlace: string;
  departureTime: Date;
  arrivalPlace: string;
  arrivalTime: Date;
  distance: number;
  priceFrom: number;
  priceTo: number;
  constructor(companyName: string, departurePlace: string, departureTime: Date, arrivalPlace: string,
              arrivalTime: Date, distance: number, priceFrom: number, priceTo: number) {
    this.companyName = companyName;
    this.departurePlace = departurePlace;
    this.departureTime = departureTime;
    this.arrivalPlace = arrivalPlace;
    this.arrivalTime = arrivalTime;
    this.distance = distance;
    this.priceFrom = priceFrom;
    this.priceTo = priceTo;
  }
}
