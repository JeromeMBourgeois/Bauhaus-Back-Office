package fr.insee.rmes.bauhaus_services;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.insee.rmes.exceptions.RmesException;
import fr.insee.rmes.model.operations.documentations.Documentation;
import fr.insee.rmes.model.operations.documentations.MSD;

public interface OperationsDocumentationsService {


	/******************************************************************************************
	 * DOCUMENTATION
	 * *******************************************************************************************/

	
	MSD getMSD() throws RmesException;

	String getMetadataAttribute(String id) throws RmesException;

	String getMetadataAttributes() throws RmesException;
	
	//SIMS
	String getMetadataReport(String id) throws RmesException;

	Documentation getFullSimsForXml(String id) throws RmesException;

	String getFullSimsForJson(String id) throws RmesException;
	
	String createMetadataReport(String body) throws RmesException;

	String setMetadataReport(String id, String body) throws RmesException;

	String publishMetadataReport(String id) throws RmesException;
	
	String getMetadataReportOwner(String id) throws RmesException;

	String getMSDJson() throws RmesException;

	String getMetadataReportDefaultValue() throws IOException;

	Status deleteMetadataReport(String id) throws RmesException;
	
	/** export **/
	Response exportMetadataReport(String id, boolean includeEmptyMas, boolean lg1, boolean lg2) throws RmesException;

	Response exportMetadataReportForLabel(String id) throws RmesException;

	Response exportMetadataReportTempFiles(String id, Boolean includeEmptyMas, Boolean lg1, Boolean lg2) throws RmesException;


}
