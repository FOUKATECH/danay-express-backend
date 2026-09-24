package cm.danayexpress.backend.reporting.service;

/**
 * Service pour la génération des exports Excel (.xlsx) et PDF (.pdf) (CDC section 8.11 & 15.2).
 */
public interface ExportService {

    byte[] genererExcelVoyages();

    byte[] genererPdfVoyages();

    byte[] genererExcelRecettes();

    byte[] genererPdfRecettes();

    byte[] genererExcelParcAutomobile();

    byte[] genererPdfParcAutomobile();
}
