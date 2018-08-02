package fr.insee.rmes.persistance.service.sesame.operations.series;

import java.util.List;

import fr.insee.rmes.config.swagger.model.IdLabelTwoLangs;
import fr.insee.rmes.persistance.service.sesame.links.OperationsLink;
import io.swagger.annotations.ApiModelProperty;

public class Series {

	@ApiModelProperty(value = "Id", required = true)
	public String id;

	@ApiModelProperty(value = "Label lg1", required = true)
	public String prefLabelLg1;

	@ApiModelProperty(value = "Label lg2")
	public String prefLabelLg2;

	@ApiModelProperty(value = "Alternative label Lg1")
	public String altLabelLg1;

	@ApiModelProperty(value = "Alternative label Lg2")
	public String altLabelLg2;

	@ApiModelProperty(value = "Abstract Lg1")
	public String abstractLg1;


	@ApiModelProperty(value = "Abstract lg2")
	public String abstractLg2;
	
	@ApiModelProperty(value = "History note Lg1")
	public String historyNoteLg1;


	@ApiModelProperty(value = "History note lg2")
	public String historyNoteLg2;

	@ApiModelProperty(value = "Family")
	public IdLabelTwoLangs family;

	@ApiModelProperty(value = "Operations")
	public List<IdLabelTwoLangs> operations;

	@ApiModelProperty(value = "Type's notation")
	public String typeCode;

	@ApiModelProperty(value = "Type list's notation")
	public String typeList;

	@ApiModelProperty(value = "Frequency's notation")
	public String accrualPeriodicityCode;

	@ApiModelProperty(value = "Frequencies list's notation")
	public String accrualPeriodicityList;

	@ApiModelProperty(value = "Identifier of creator")
	public String creator;

	@ApiModelProperty(value = "Identifiers of stake holder")
	public List<String> stakeHolder;

	@ApiModelProperty(value = "Identifiers of data collector")
	public List<String> dataCollector;

	@ApiModelProperty(value = "Identifier of contributor")
	public String contributor;
	
	@ApiModelProperty(value = "List of resources to see also")
	public List<OperationsLink> seeAlso;

	@ApiModelProperty(value = "List of resources that replaces the series")
	public List<OperationsLink> replaces;
	
	@ApiModelProperty(value = "List of resources replaced by the series")
	public List<OperationsLink> isReplacedBy;
	
	
	
	public Series(String id) {
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public String getPrefLabelLg1() {
		return prefLabelLg1;
	}

	public String getPrefLabelLg2() {
		return prefLabelLg2;
	}

	public String getAltLabelLg1() {
		return altLabelLg1;
	}

	public String getAltLabelLg2() {
		return altLabelLg2;
	}

	public String getAbstractLg1() {
		return abstractLg1;
	}

	public String getAbstractLg2() {
		return abstractLg2;
	}

	

	public String getHistoryNoteLg1() {
		return historyNoteLg1;
	}

	public String getHistoryNoteLg2() {
		return historyNoteLg2;
	}
	public IdLabelTwoLangs getFamily() {
		return family;
	}

	public List<IdLabelTwoLangs> getOperations() {
		return operations;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeList() {
		return typeList;
	}

	public String getAccrualPeriodicityCode() {
		return accrualPeriodicityCode;
	}

	public String getAccrualPeriodicityList() {
		return accrualPeriodicityList;
	}

	public String getCreator() {
		return creator;
	}

	public List<String> getStakeHolder() {
		return stakeHolder;
	}

	public List<String> getDataCollector() {
		return dataCollector;
	}

	public String getContributor() {
		return contributor;
	}

	public List<OperationsLink> getSeeAlso() {
		return seeAlso;
	}

	public List<OperationsLink> getReplaces() {
		return replaces;
	}

	public List<OperationsLink> getIsReplacedBy() {
		return isReplacedBy;
	}



}
