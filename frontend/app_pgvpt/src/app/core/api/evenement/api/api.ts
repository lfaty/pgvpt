export * from './evenements.service';
import { EvenementsService } from './evenements.service';
export * from './sessions.service';
import { SessionsService } from './sessions.service';
export const APIS = [EvenementsService, SessionsService];
