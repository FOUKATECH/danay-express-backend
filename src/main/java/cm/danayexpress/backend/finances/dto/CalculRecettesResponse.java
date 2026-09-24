package cm.danayexpress.backend.finances.dto;

/** Résumé d'une opération de calcul/rattrapage de recettes pour un voyage. */
public record CalculRecettesResponse(
        Long voyageId,
        Integer nombreRecettesCreees,
        Integer nombreDejaCalculees,
        Integer nombreSansTarifApplicable
) {
}