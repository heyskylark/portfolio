import Moment from 'moment';
import Technology from '../models/Technology';

export function compileTechnologies(technologies: Array<Technology>): string {
  let techString = '';
  for (let i = 0; i < technologies.length; i++) {
    techString +=
      i !== technologies.length - 1 ? `${technologies[i].name} ${'\u00B7'} ` : technologies[i].name;
  }
  return techString;
}

export function formatDate(date: Date, dateString: string): string {
  Moment.locale('en');
  return Moment(date).format(dateString);
}
