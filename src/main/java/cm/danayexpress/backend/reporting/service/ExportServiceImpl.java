package cm.danayexpress.backend.reporting.service;

import cm.danayexpress.backend.exploitation.entity.Voyage;
import cm.danayexpress.backend.exploitation.repository.VoyageRepository;
import cm.danayexpress.backend.finances.entity.Recette;
import cm.danayexpress.backend.finances.repository.RecetteRepository;
import cm.danayexpress.backend.parcautomobile.entity.Vehicule;
import cm.danayexpress.backend.parcautomobile.repository.VehiculeRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExportServiceImpl implements ExportService {

    private final VoyageRepository voyageRepository;
    private final RecetteRepository recetteRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public byte[] genererExcelVoyages() {
        List<Voyage> voyages = voyageRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Voyages");

            // Cell Style Header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Headers
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Ligne", "Sens", "Agence Départ", "Destination", "Véhicule", "Heure Prévue", "Passagers", "Statut"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data
            int rowNum = 1;
            for (Voyage v : voyages) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(v.getId());
                row.createCell(1).setCellValue(v.getLigne() != null ? v.getLigne().getNom() : "");
                row.createCell(2).setCellValue(v.getSens() != null && v.getSens().getCode() != null ? v.getSens().getCode().name() : "");
                row.createCell(3).setCellValue(v.getAgenceDepart() != null ? v.getAgenceDepart().getNom() : "");
                row.createCell(4).setCellValue(v.getDestinationFinale() != null ? v.getDestinationFinale().getNom() : "");
                row.createCell(5).setCellValue(v.getVehicule() != null ? v.getVehicule().getImmatriculation() : "");
                row.createCell(6).setCellValue(v.getHeureDepartPrevue() != null ? v.getHeureDepartPrevue().toString() : "");
                row.createCell(7).setCellValue(v.getEffectifPassagersDepart() != null ? v.getEffectifPassagersDepart() : 0);
                row.createCell(8).setCellValue(v.getStatut() != null ? v.getStatut().name() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération de l'export Excel Voyages", e);
            throw new RuntimeException("Erreur de génération d'export Excel : " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] genererPdfVoyages() {
        List<Voyage> voyages = voyageRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // Titre
            com.lowagie.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
            Paragraph title = new Paragraph("Danay Express SARL — Rapport des Voyages", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            String[] headers = {"ID", "Ligne", "Départ", "Destination", "Véhicule", "Passagers", "Statut"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
                cell.setBackgroundColor(Color.DARK_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (Voyage v : voyages) {
                table.addCell(String.valueOf(v.getId()));
                table.addCell(v.getLigne() != null ? v.getLigne().getNom() : "");
                table.addCell(v.getAgenceDepart() != null ? v.getAgenceDepart().getNom() : "");
                table.addCell(v.getDestinationFinale() != null ? v.getDestinationFinale().getNom() : "");
                table.addCell(v.getVehicule() != null ? v.getVehicule().getImmatriculation() : "");
                table.addCell(String.valueOf(v.getEffectifPassagersDepart() != null ? v.getEffectifPassagersDepart() : 0));
                table.addCell(v.getStatut() != null ? v.getStatut().name() : "");
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF Voyages", e);
            throw new RuntimeException("Erreur de génération du PDF : " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] genererExcelRecettes() {
        List<Recette> recettes = recetteRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Recettes");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID Recette", "Agence Origine", "Passagers", "Montant (FCFA)", "Date Création"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (Recette r : recettes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(r.getId());
                row.createCell(1).setCellValue(r.getMouvementTransit() != null && r.getMouvementTransit().getAgenceOrigine() != null ? r.getMouvementTransit().getAgenceOrigine().getNom() : "");
                Integer nbPass = r.getMouvementTransit() != null ? r.getMouvementTransit().getNombrePassagers() : null;
                row.createCell(2).setCellValue(nbPass != null ? nbPass : 0);
                row.createCell(3).setCellValue(r.getMontant() != null ? r.getMontant().doubleValue() : 0.0);
                row.createCell(4).setCellValue(r.getCreatedAt() != null ? r.getCreatedAt().toString() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération Excel Recettes", e);
            throw new RuntimeException("Erreur export Excel : " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] genererPdfRecettes() {
        List<Recette> recettes = recetteRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("Danay Express SARL — Rapport Financier des Recettes", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.DARK_GRAY));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            String[] headers = {"ID Recette", "Agence Origine", "Montant (FCFA)", "Date"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
                cell.setBackgroundColor(Color.GRAY);
                table.addCell(cell);
            }

            for (Recette r : recettes) {
                table.addCell(String.valueOf(r.getId()));
                table.addCell(r.getMouvementTransit() != null && r.getMouvementTransit().getAgenceOrigine() != null ? r.getMouvementTransit().getAgenceOrigine().getNom() : "");
                table.addCell(r.getMontant() != null ? r.getMontant().toString() : "0");
                table.addCell(r.getCreatedAt() != null ? r.getCreatedAt().toString() : "");
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération PDF Recettes", e);
            throw new RuntimeException("Erreur export PDF : " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] genererExcelParcAutomobile() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Parc Automobile");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Immatriculation", "Type", "Capacité", "Propriétaire", "Agence Rattachement", "Statut"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (Vehicule v : vehicules) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(v.getId());
                row.createCell(1).setCellValue(v.getImmatriculation());
                row.createCell(2).setCellValue(v.getType() != null ? v.getType() : "");
                row.createCell(3).setCellValue(v.getCapacite() != null ? v.getCapacite() : 0);
                row.createCell(4).setCellValue(v.getProprietaire() != null ? v.getProprietaire().getNom() : "");
                row.createCell(5).setCellValue(v.getAgence() != null ? v.getAgence().getNom() : "");
                row.createCell(6).setCellValue(v.getStatut() != null ? v.getStatut().name() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération Excel Parc Automobile", e);
            throw new RuntimeException("Erreur export Excel : " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] genererPdfParcAutomobile() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("Danay Express SARL — État du Parc Automobile", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            String[] headers = {"ID", "Immatriculation", "Type", "Capacité", "Statut"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
                cell.setBackgroundColor(Color.BLUE);
                table.addCell(cell);
            }

            for (Vehicule v : vehicules) {
                table.addCell(String.valueOf(v.getId()));
                table.addCell(v.getImmatriculation());
                table.addCell(v.getType() != null ? v.getType() : "");
                table.addCell(String.valueOf(v.getCapacite() != null ? v.getCapacite() : 0));
                table.addCell(v.getStatut() != null ? v.getStatut().name() : "");
            }

            document.add(table);
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération PDF Parc Automobile", e);
            throw new RuntimeException("Erreur export PDF : " + e.getMessage(), e);
        }
    }
}
