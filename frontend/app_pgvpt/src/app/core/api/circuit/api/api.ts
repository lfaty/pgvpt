export * from './activites.service';
import { ActivitesService } from './activites.service';
export * from './circuits.service';
import { CircuitsService } from './circuits.service';
export * from './etapes.service';
import { EtapesService } from './etapes.service';
export * from './optimisation.service';
import { OptimisationService } from './optimisation.service';
export const APIS = [ActivitesService, CircuitsService, EtapesService, OptimisationService];
