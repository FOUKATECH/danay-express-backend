package cm.danayexpress.backend.reporting.controller;

import cm.danayexpress.backend.reporting.dto.DashboardDirectionResponse;
import cm.danayexpress.backend.reporting.dto.DashboardOperationnelResponse;
import cm.danayexpress.backend.reporting.service.DashboardService;
import cm.danayexpress.backend.reporting.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/reporting")
@RequiredArgsConstructor
public class ReportingController {

    private final DashboardService dashboardService;
    private final ExportService exportService;

    @GetMapping("/dashboard/operationnel")
    public ResponseEntity<DashboardOperationnelResponse> getDashboardOperationnel() {
        return ResponseEntity.ok(dashboardService.getDashboardOperationnel());
    }

    @GetMapping("/dashboard/direction")
    public ResponseEntity<DashboardDirectionResponse> getDashboardDirection(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fin) {
        return ResponseEntity.ok(dashboardService.getDashboardDirection(debut, fin));
    }

    @GetMapping("/export/voyages/excel")
    public ResponseEntity<byte[]> exportVoyagesExcel() {
        byte[] bytes = exportService.genererExcelVoyages();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=voyages_danay_express.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @GetMapping("/export/voyages/pdf")
    public ResponseEntity<byte[]> exportVoyagesPdf() {
        byte[] bytes = exportService.genererPdfVoyages();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=voyages_danay_express.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    @GetMapping("/export/recettes/excel")
    public ResponseEntity<byte[]> exportRecettesExcel() {
        byte[] bytes = exportService.genererExcelRecettes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recettes_danay_express.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @GetMapping("/export/recettes/pdf")
    public ResponseEntity<byte[]> exportRecettesPdf() {
        byte[] bytes = exportService.genererPdfRecettes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recettes_danay_express.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    @GetMapping("/export/parc-automobile/excel")
    public ResponseEntity<byte[]> exportParcExcel() {
        byte[] bytes = exportService.genererExcelParcAutomobile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parc_automobile_danay_express.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @GetMapping("/export/parc-automobile/pdf")
    public ResponseEntity<byte[]> exportParcPdf() {
        byte[] bytes = exportService.genererPdfParcAutomobile();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parc_automobile_danay_express.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
