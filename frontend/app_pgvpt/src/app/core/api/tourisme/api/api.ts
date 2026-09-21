export * from './offres.service';
import { OffresService } from './offres.service';
export * from './prestations.service';
import { PrestationsService } from './prestations.service';
export const APIS = [OffresService, PrestationsService];
