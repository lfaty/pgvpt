/**
 * Utilitaires d'affichage « grand public » pour la page détail patrimoine.
 * Objectif : ne jamais exposer de valeurs techniques brutes (enums, préfixes
 * administratifs, valeurs indéterminées) aux visiteurs du site touristique.
 */

/** Valeurs considérées comme « non renseignées » côté visiteur. */
const EMPTY_VALUES = ['', '-', 'INDETERMINEE', 'INDETERMINE', 'INCONNU', 'INCONNUE', 'NON_RENSEIGNE', 'N/A', 'NULL'];

/** Libellés lisibles pour les enums fréquents. */
const ENUM_LABELS: Record<string, string> = {
  SITE_NATUREL: 'Site naturel',
  PATRIMOINE_NATUREL: 'Patrimoine naturel',
  PATRIMOINE_CULTUREL: 'Patrimoine culturel',
  PATRIMOINE_HISTORIQUE: 'Patrimoine historique',
  PATRIMOINE_IMMATERIEL: 'Patrimoine immatériel',
  MONUMENT: 'Monument',
  MUSEE: 'Musée',
  GEOCODAGE: "Géocodage d'adresse",
  GPS: 'Relevé GPS sur site',
  RELEVE_TERRAIN: 'Relevé de terrain',
  IMAGERIE_SATELLITE: 'Imagerie satellite',
  DRONE: 'Relevé par drone',
  IMPORT_SIG: 'Import SIG',
  AUTRE: 'Autre'
};

/** Vrai si la valeur mérite d'être affichée à un visiteur. */
export function hasValue(value: unknown): boolean {
  if (value === null || value === undefined) return false;
  if (Array.isArray(value)) return value.length > 0;
  if (typeof value === 'string') return !EMPTY_VALUES.includes(value.trim().toUpperCase());
  return true;
}

/** Transforme un enum (ex. « CASCADE_NATURELLE ») en libellé lisible (« Cascade naturelle »). */
export function humanize(value: string | null | undefined): string {
  if (!hasValue(value)) return '';
  const v = String(value).trim();
  if (ENUM_LABELS[v]) return ENUM_LABELS[v];
  // Valeur déjà rédigée (contient des minuscules ou des espaces) : on la garde.
  if (/[a-zà-ÿ]/.test(v)) return v;
  const words = v.split(/[_\s]+/).map(w => w.toLowerCase());
  const s = words.join(' ');
  return s.charAt(0).toUpperCase() + s.slice(1);
}

/** Retire « Zone géographique de … » pour ne garder que le nom administratif. */
export function cleanRegion(raw: string | null | undefined): string {
  if (!hasValue(raw)) return '';
  return String(raw).replace(/^Zone g[ée]ographique\s+(du|de la|des|de|d')\s*/i, '').trim();
}

/** Retire « Zone touristique de … ». */
export function cleanZoneTouristique(raw: string | null | undefined): string {
  if (!hasValue(raw)) return '';
  return String(raw).replace(/^Zone touristique\s+(du|de la|des|de|d')\s*/i, '').trim();
}

/** Nettoie le nom de commune des préfixes (ex: « Centre historique de Gorée » → « Gorée ») et rejette les métadonnées. */
export function cleanCommune(raw: string | null | undefined): string {
  if (!hasValue(raw)) return '';
  const s = String(raw).trim();
  if (/zone touristique|latitude|longitude|fiabilit|point/i.test(s)) return '';
  return s
    .replace(/^(centre historique|village|quartier|commune)\s+(du|de la|des|de|d')\s*/i, '')
    .replace(/^(cascade|plage|ile|île)\s+de\s+/i, '')
    .trim();
}

/**
 * Construit la liste ordonnée et dédoublonnée des divisions administratives
 * (Commune → Département → Région) pour éviter « Kédougou • Kédougou ».
 */
export function locationParts(geo: any, patrimoine?: any): string[] {
  const rawCommune = geo?.commune || patrimoine?.commune || cleanZoneTouristique(geo?.zoneTouristiqueNom);
  const commune = cleanCommune(rawCommune);
  const departement = geo?.departement || patrimoine?.departement;
  const region = cleanRegion(geo?.region || patrimoine?.region);
  const parts: string[] = [];
  [commune, departement, region].forEach(p => {
    if (hasValue(p) && p !== 'Plusieurs communes' && !parts.some(x => x.toLowerCase() === String(p).toLowerCase())) {
      parts.push(String(p));
    }
  });
  return parts;
}

/** Article adapté devant un nom propre : « de la Cascade », « du Musée », « de l'Île ». */
export function articleDe(nom: string | null | undefined): string {
  if (!nom) return '';
  const n = nom.trim();
  if (/^[aeiouyhàâéèêîïôûAEIOUYHÀÂÉÈÊÎÏÔÛ]/.test(n)) return `l'${n}`;
  const first = n.split(/\s+/)[0].toLowerCase();
  const feminins = ['cascade', 'plage', 'maison', 'grande', 'mosquée', 'cathédrale', 'église', 'forêt', 'réserve', 'place', 'porte', 'tour', 'statue', 'case', 'colline', 'pointe', 'lagune', 'baie', 'vallée', 'route', 'gare', 'citadelle', 'falaise', 'grotte', 'dune', 'mangrove', 'chapelle', 'basilique'];
  if (feminins.includes(first)) return `la ${n}`;
  if (['parc', 'musée', 'monument', 'lac', 'fort', 'village', 'site', 'palais', 'marché', 'phare', 'pont', 'delta', 'fleuve', 'domaine', 'mausolée', 'tata', 'baobab', 'quartier'].includes(first)) return `le ${n}`;
  return n;
}

/** « À la découverte de la Cascade de Dindéfélo » */
export function decouverteTitre(nom: string | null | undefined): string {
  const a = articleDe(nom);
  if (a.startsWith('le ')) return `À la découverte du ${a.slice(3)}`;
  return `À la découverte de ${a}`;
}

/** Formate des coordonnées décimales en notation lisible (12.38318° N, 12.32778° O). */
export function formatCoords(lat?: number, lon?: number, digits = 5): string {
  if (lat === null || lat === undefined || lon === null || lon === undefined) return '';
  const la = `${Math.abs(lat).toFixed(digits)}° ${lat >= 0 ? 'N' : 'S'}`;
  const lo = `${Math.abs(lon).toFixed(digits)}° ${lon >= 0 ? 'E' : 'O'}`;
  return `${la}, ${lo}`;
}

/** Lien d'itinéraire Google Maps vers les coordonnées du site. */
export function directionsUrl(lat?: number, lon?: number): string | null {
  if (lat === null || lat === undefined || lon === null || lon === undefined) return null;
  return `https://www.google.com/maps/dir/?api=1&destination=${lat},${lon}`;
}
