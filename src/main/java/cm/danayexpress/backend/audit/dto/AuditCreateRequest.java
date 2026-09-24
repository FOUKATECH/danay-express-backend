package cm.danayexpress.backend.audit.dto;

import cm.danayexpress.backend.audit.enums.ModuleApplicatif;
import cm.danayexpress.backend.audit.enums.TypeActionAudit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuditCreateRequest(

        String nomUtilisateur,

        @NotNull(message = "L'action est obligatoire")
        TypeActionAudit action,

        @NotNull(message = "Le module est obligatoire")
        ModuleApplicatif module,

        String elementId,

        @NotBlank(message = "La description de l'action est obligatoire")
        String description,

        String ancienneValeur,

        String nouvelleValeur
) {
}
