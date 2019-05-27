import {SeatClass} from '../../shared/enumeration/seat-class.enum';

export class SeatDTO {
  rowNo: number;
  colNo: number;
  seatClass: SeatClass;
  constructor(rowNo?: number, colNo?: number, seatClass?: SeatClass) {
    this.rowNo = rowNo;
    this.colNo = colNo;
    this.seatClass = seatClass;
  }
}
