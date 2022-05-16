package fr.insee.rmes.webservice.operations;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insee.rmes.bauhaus_services.Constants;
import fr.insee.rmes.config.swagger.model.operations.documentation.Attribute;
import fr.insee.rmes.exceptions.RmesException;
import fr.insee.rmes.model.operations.documentations.Documentation;
import fr.insee.rmes.model.operations.documentations.MAS;
import fr.insee.rmes.model.operations.documentations.MSD;
import fr.insee.rmes.utils.XMLUtils;
import fr.insee.rmes.webservice.OperationsCommonResources;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@Qualifier("Report")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/operations")
public class MetadataReportResources extends OperationsCommonResources {

	
	/***************************************************************************************************
	 * DOCUMENTATION
	 ******************************************************************************************************/

	@GetMapping("/metadataStructureDefinition")
	@Produces({MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMsd", summary = "Metadata structure definition", 
	responses = { @ApiResponse(content = @Content(/*mediaType = "application/json",*/ schema = @Schema(implementation = MAS.class)))})
	public ResponseEntity<Object> getMSD(
			@Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header
			) {
		MSD msd ;
		String jsonResultat = null ;

		if (header != null && header.equals(MediaType.APPLICATION_XML_VALUE)) {
			try {
				msd = documentationsService.getMSD();
			} catch (RmesException e) {
				return returnRmesException(e);
			}
			return ResponseEntity.ok(XMLUtils.produceResponse(msd, header));
		}

		else {
			try {
				jsonResultat = documentationsService.getMSDJson();
			} catch (RmesException e) {
				return returnRmesException(e);
			}
			return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
		}
	}

	@GetMapping("/metadataAttribute/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMA", summary = "Metadata attribute specification and property", 
	responses = { @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Attribute.class)))})
	public ResponseEntity<Object> getMetadataAttribute(@PathVariable(Constants.ID) String id) {
		String jsonResultat;
		try {
			jsonResultat = documentationsService.getMetadataAttribute(id);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
	}

	@GetMapping("/metadataAttributes")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMAs", summary = "Metadata attributes specification and property", 
	responses = { @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(type="array",implementation = Attribute.class)))})
	public ResponseEntity<Object> getMetadataAttributes() {
		String jsonResultat;
		try {
			jsonResultat = documentationsService.getMetadataAttributes();
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
	}


	@GetMapping("/metadataReport/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMetadataReport", summary = "Metadata report for an id", 
	responses = { @ApiResponse(content = @Content(mediaType = "application/json" , schema = @Schema(implementation = Documentation.class)
			))})
	public ResponseEntity<Object> getMetadataReport(@PathVariable(Constants.ID) String id) {
		String jsonResultat;
		try {
			jsonResultat = documentationsService.getMetadataReport(id);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
	}

	@GetMapping("/metadataReport/default")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMetadataReportDefaultValue", summary = "Get default value for metadata report",
	responses = { @ApiResponse(content = @Content(mediaType = "application/json" , schema = @Schema(implementation = Documentation.class)
			))})
	public ResponseEntity<Object> getMetadataReportDefaultValue() throws IOException {
		return ResponseEntity.status(HttpStatus.OK).body(documentationsService.getMetadataReportDefaultValue());
	}

	@GetMapping("/metadataReport/fullSims/{id}")
	@Produces({MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@io.swagger.v3.oas.annotations.Operation(operationId = "getFullSims", summary = "Full sims for an id", 
	responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = Documentation.class)
			))})
	public ResponseEntity<Object> getFullSims(
			@Parameter(
					description = "Identifiant de la documentation (format : [0-9]{4})",
					required = true,
					schema = @Schema(pattern = "[0-9]{4}", type = "string")) @PathVariable(Constants.ID) String id,
			@Parameter(hidden = true) @HeaderParam(HttpHeaders.ACCEPT) String header
			) {
		Documentation fullsims;
		String jsonResultat;
		
		if (header != null && header.equals(MediaType.APPLICATION_XML_VALUE)) {
			try {
				fullsims = documentationsService.getFullSimsForXml(id);
			} catch (RmesException e) {
				return returnRmesException(e);
			}

			return ResponseEntity.ok(XMLUtils.produceResponse(fullsims, header));
		}

		else {
			try {
				jsonResultat = documentationsService.getFullSimsForJson(id);
			} catch (RmesException e) {
				return returnRmesException(e);
			}
			return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
		}
		
		
		
	
	}

	/**
	 * GET
	 * @param id
	 * @return
	 */
	@GetMapping("/metadataReport/Owner/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "getMetadataReport", summary = "Owner stamp for a Metadata report's id", 
	responses = { @ApiResponse(content = @Content(mediaType = "application/json" , schema = @Schema(implementation = Documentation.class)
			))})
	public ResponseEntity<Object> getMetadataReportOwner(@PathVariable(Constants.ID) String id) {
		String jsonResultat;
		try {
			jsonResultat = documentationsService.getMetadataReportOwner(id);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(jsonResultat);
	}

	/**
	 * CREATE
	 * @param body
	 * @return
	 */
	@PreAuthorize("@AuthorizeMethodDecider.isAdmin() || @AuthorizeMethodDecider.isIndicatorContributor() || @AuthorizeMethodDecider.isSeriesContributor()")
	@PostMapping("/metadataReport")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "setMetadataReport", summary = "Create metadata report",
	responses = { @ApiResponse(content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE))})
	public ResponseEntity<Object> setMetadataReport(
			@Parameter(description = "Metadata report to create", required = true,
	content = @Content(schema = @Schema(implementation = Documentation.class))) @RequestBody String body) {
		logger.info("POST Metadata report");
		String id = null;
		try {
			id = documentationsService.createMetadataReport(body);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		if (id == null) {return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(id);}
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	/**
	 * UPDATE
	 * @param id
	 * @param body
	 * @return
	 */
	@PreAuthorize("@AuthorizeMethodDecider.isAdmin() "
			+ "|| @AuthorizeMethodDecider.isIndicatorContributor() "
			+ "|| @AuthorizeMethodDecider.isSeriesContributor() "
			+ "|| @AuthorizeMethodDecider.isCnis()")
	@PutMapping("/metadataReport/{id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "setMetadataReportById", summary = "Update metadata report")
	public ResponseEntity<Object> setMetadataReportById(
			@PathVariable(Constants.ID) String id, 
			@Parameter(description = "Report to update", required = true,
			content = @Content(schema = @Schema(implementation = Documentation.class))) @RequestBody String body) {
		try {
			documentationsService.setMetadataReport(id, body);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * DELETE
	 * @param id
	 * @return
	 */
	@PreAuthorize("@AuthorizeMethodDecider.isAdmin() ")
	@DeleteMapping("/metadataReport/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "deleteMetadataReportById", summary = "Delete metadata report")
	public ResponseEntity<Object> deleteMetadataReportById(
			@PathVariable(Constants.ID) String id) {
		HttpStatus result=HttpStatus.NO_CONTENT;
		try {
			result = documentationsService.deleteMetadataReport(id);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(result.value()).build();
	}



	/**
	 * PUBLISH
	 * @param id
	 * @return response
	 */	
	@PreAuthorize("@AuthorizeMethodDecider.isAdmin() "
			+ "|| @AuthorizeMethodDecider.isIndicatorContributor() "
			+ "|| @AuthorizeMethodDecider.isSeriesContributor() ")
	@PutMapping("/metadataReport/validate/{id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@io.swagger.v3.oas.annotations.Operation(operationId = "setMetadataReportValidation", summary = "Sims validation")
	public ResponseEntity<Object> setSimsValidation(
			@PathVariable(Constants.ID) String id) throws RmesException {
		try {
			documentationsService.publishMetadataReport(id);
		} catch (RmesException e) {
			return returnRmesException(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}


	/**
	 * EXPORT
	 * @param id
	 * @param lg1
	 * @param lg2
	 * @param includeEmptyMas
	 * @return response
	 */	
	@GetMapping("/metadataReport/export/{id}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/vnd.oasis.opendocument.text" })
	@io.swagger.v3.oas.annotations.Operation(operationId = "getSimsExport", summary = "Produce a document with a metadata report")
	public ResponseEntity<?> getSimsExport(@Parameter(
			description = "Identifiant de la documentation (format : [0-9]{4})",
			required = true,
			schema = @Schema(pattern = "[0-9]{4}", type = "string")) @PathVariable(Constants.ID) String id
			,
			@Parameter(
					description = "Inclure les champs vides",
					required = false)  @QueryParam("emptyMas") Boolean includeEmptyMas
			,
			@Parameter(
					description = "Version française",
					required = false) @QueryParam("lg1")  Boolean lg1
			,
			@Parameter(
					description = "Version anglaise",
					required = false) @QueryParam("lg2")  Boolean lg2
			) throws RmesException {
		if (includeEmptyMas==null) {includeEmptyMas=true;}
		if (lg1==null) {lg1=true;}
		if (lg2==null) {lg2=true;}
		return documentationsService.exportMetadataReport(id,includeEmptyMas,lg1,lg2);	
	}

	/**
	 * EXPORT FOR LABEL
	 * @param id
	 * @return response
	 */	
	@GetMapping("/metadataReport/export/label/{id}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/vnd.oasis.opendocument.text" })
	@io.swagger.v3.oas.annotations.Operation(operationId = "getSimsExportLabel", summary = "Produce a document with a metadata report")
	public ResponseEntity<?> getSimsExportForLabel(@Parameter(
			description = "Identifiant de la documentation (format : [0-9]{4})",
			required = true,
			schema = @Schema(pattern = "[0-9]{4}", type = "string")) @PathVariable(Constants.ID) String id
			) throws RmesException {

		return documentationsService.exportMetadataReportForLabel(id);	
	}
	
	/**
	 * EXPORT xml files used to produce the final odt file
	 * @param id
	 * @param lg1
	 * @param lg2
	 * @param includeEmptyMas
	 * @return response
	 */	
	@GetMapping("/metadataReport/export/{id}/tempFiles")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/vnd.oasis.opendocument.text" })
	@io.swagger.v3.oas.annotations.Operation(operationId = "getSimsExportFiles", summary = "Get xml files used to produce a document with a metadata report")
	public ResponseEntity<Object> getSimsExportFiles(@Parameter(
			description = "Identifiant de la documentation (format : [0-9]{4})",
			required = true,
			schema = @Schema(pattern = "[0-9]{4}", type = "string")) @PathVariable(Constants.ID) String id
			,
			@Parameter(
					description = "Inclure les champs vides",
					required = false)  @QueryParam("emptyMas") Boolean includeEmptyMas
			,
			@Parameter(
					description = "Version française",
					required = false) @QueryParam("lg1")  Boolean lg1
			,
			@Parameter(
					description = "Version anglaise",
					required = false) @QueryParam("lg2")  Boolean lg2
			) throws RmesException {
		if (includeEmptyMas==null) {includeEmptyMas=true;}
		if (lg1==null) {lg1=true;}
		if (lg2==null) {lg2=true;}
		return documentationsService.exportMetadataReportTempFiles(id,includeEmptyMas,lg1,lg2);	
	}
	
}
