package cm.danayexpress.backend.passagers.dto;

/** Nombre de passagers encore à bord pour une agence d'origine donnée. */
public record SoldeOrigineResponse(
        Long agenceOrigineId,
        String agenceOrigineNom,
        Integer solde
) {
}