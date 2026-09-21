export * from './disponibilite.service';
import { DisponibiliteService } from './disponibilite.service';
export * from './reservations.service';
import { ReservationsService } from './reservations.service';
export const APIS = [DisponibiliteService, ReservationsService];
