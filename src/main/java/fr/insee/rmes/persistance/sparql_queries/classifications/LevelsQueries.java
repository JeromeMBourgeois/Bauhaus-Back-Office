package fr.insee.rmes.persistance.sparql_queries.classifications;

import fr.insee.rmes.bauhaus_services.rdf_utils.FreeMarkerUtils;
import fr.insee.rmes.exceptions.RmesException;
import fr.insee.rmes.persistance.sparql_queries.GenericQueries;

import java.util.HashMap;

public class LevelsQueries extends GenericQueries{
	
	public static String levelsQuery(String classificationId) throws RmesException {
		HashMap<String, Object> params = new HashMap<>();
		params.put("ID", classificationId);
		params.put("LG1", config.getLg1());
		params.put("LG2", config.getLg2());
		return FreeMarkerUtils.buildRequest("classifications/", "getClassificationLevels.ftlh", params);
	}
	
	public static String levelQuery(String classificationId, String levelId) {
		return "SELECT ?classificationId ?levelId ?prefLabelLg1 ?prefLabelLg2 ?depth ?notation \n"
			+ "?notationPattern ?broaderLg1 ?broaderLg2 ?idBroader ?narrowerLg1 ?narrowerLg2 ?idNarrower \n"
			+ "WHERE { \n"
			+ "?level rdf:type xkos:ClassificationLevel . \n"
			+ "FILTER(STRENDS(STR(?level),'/codes/" + classificationId + "/" + levelId + "')) \n"
			+ "BIND(STRAFTER(STRAFTER(STR(?level),'/codes/'), '/') AS ?levelId) \n"
			+ "BIND(STRBEFORE(STRAFTER(STR(?level),'/codes/'), '/') AS ?classificationId) \n"
			+ "?level skos:prefLabel ?prefLabelLg1 . \n"
			+ "FILTER (lang(?prefLabelLg1) = '" + config.getLg1() + "') \n"
			+ "OPTIONAL {?level skos:prefLabel ?prefLabelLg2 . \n"
			+ "FILTER (lang(?prefLabelLg2) = '" + config.getLg2() + "') } \n"
			+ "?level xkos:depth ?depth . \n"
			+ "?level skos:notation ?notation . \n"
			+ "?level xkos:notationPattern ?notationPattern . \n"
			+ "OPTIONAL {?node rdf:first ?level . \n"
				+ "OPTIONAL {?node rdf:rest ?nextNode . \n"
				+ "?nextNode rdf:first ?nextLevel . \n"
				+ "?nextLevel skos:prefLabel ?narrowerLg1 . \n"
				+ "FILTER (lang(?narrowerLg1) = '" + config.getLg1() + "') \n"
					+ "OPTIONAL {?nextLevel skos:prefLabel ?narrowerLg2 . \n"
					+ "FILTER (lang(?narrowerLg2) = '" + config.getLg2() + "') } \n"
				+ "BIND(STRAFTER(STRAFTER(STR(?nextLevel),'/codes/'), '/') AS ?idNarrower) } \n"
				+ "OPTIONAL {?previousNode rdf:rest ?node . \n"
				+ "?previousNode rdf:first ?previousLevel . \n"
				+ "?previousLevel skos:prefLabel ?broaderLg1 . \n"
				+ "FILTER (lang(?broaderLg1) = '" + config.getLg1() + "') \n"
					+ "OPTIONAL {?previousLevel skos:prefLabel ?broaderLg2 . \n"
					+ "FILTER (lang(?broaderLg2) = '" + config.getLg2() + "') } \n"
				+ "BIND(STRAFTER(STRAFTER(STR(?previousLevel),'/codes/'), '/') AS ?idBroader) } \n"
			+ ""
			+ "} \n"
			+ "} \n";	
	}
	
	public static String levelMembersQuery(String classificationId, String levelId) {
		return "SELECT DISTINCT ?id ?labelLg1 ?labelLg2 \n"
				+ "WHERE { \n"
				+ "?level rdf:type xkos:ClassificationLevel . \n"
				+ "FILTER(STRENDS(STR(?level),'/codes/" + classificationId + "/" + levelId + "')) \n"
				+ "?level skos:member ?item . \n"
				+ "?item skos:prefLabel ?labelLg1 . \n"
				+ "FILTER (lang(?labelLg1) = '" + config.getLg1() + "') \n"
				+ "OPTIONAL {?item skos:prefLabel ?labelLg2 . \n"
				+ "FILTER (lang(?labelLg2) = '" + config.getLg2() + "') } . \n"
				+ "?item skos:notation ?id . \n"
				+ "} \n"
				+ "ORDER BY ?id ";
	}
	
	  private LevelsQueries() {
		    throw new IllegalStateException("Utility class");
	}

}
