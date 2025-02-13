package com.debatetimer.controller.parliamentary;

import com.debatetimer.controller.auth.AuthMember;
import com.debatetimer.domain.member.Member;
import com.debatetimer.dto.parliamentary.request.ParliamentaryTableCreateRequest;
import com.debatetimer.dto.parliamentary.response.ParliamentaryTableResponse;
import com.debatetimer.service.parliamentary.ParliamentaryService;
import com.debatetimer.view.exporter.ParliamentaryTableExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParliamentaryController {

    private final ParliamentaryService parliamentaryService;
    private final ParliamentaryTableExcelExporter parliamentaryTableExcelExporter;

    @PostMapping("/api/table/parliamentary")
    @ResponseStatus(HttpStatus.CREATED)
    public ParliamentaryTableResponse save(
            @Valid @RequestBody ParliamentaryTableCreateRequest tableCreateRequest,
            @AuthMember Member member
    ) {
        return parliamentaryService.save(tableCreateRequest, member);
    }

    @GetMapping("/api/table/parliamentary/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    public ParliamentaryTableResponse getTable(
            @PathVariable Long tableId,
            @AuthMember Member member
    ) {
        return parliamentaryService.findTable(tableId, member);
    }

    @PutMapping("/api/table/parliamentary/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    public ParliamentaryTableResponse updateTable(
            @Valid @RequestBody ParliamentaryTableCreateRequest tableCreateRequest,
            @PathVariable Long tableId,
            @AuthMember Member member
    ) {
        return parliamentaryService.updateTable(tableCreateRequest, tableId, member);
    }

    @DeleteMapping("/api/table/parliamentary/{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(
            @PathVariable Long tableId,
            @AuthMember Member member
    ) {
        parliamentaryService.deleteTable(tableId, member);
    }

    @GetMapping("/api/table/parliamentary/export/{tableId}")
    public ResponseEntity<Void> export(
//            @AuthMember Member member,
            HttpServletResponse response,
            @PathVariable Long tableId
    ) {
        try (OutputStream outputStream = response.getOutputStream()) {
            ParliamentaryTableResponse foundTable = parliamentaryService.findTableById(tableId, 1L);
            parliamentaryTableExcelExporter.export(foundTable, outputStream);
        } catch (Exception e) {
        }
        return ResponseEntity.ok().build();
    }
}
