package fr.insee.rmes.external_services.authentication.stamps;

import java.util.TreeSet;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.rmes.config.Config;
import fr.insee.rmes.config.auth.security.restrictions.StampsRestrictionsService;
import fr.insee.rmes.exceptions.RmesException;
import fr.insee.rmes.external_services.authentication.LdapConnexion;

@Service
public class RmesStampsImpl implements StampsService {
	
	static final Logger logger = LogManager.getLogger(RmesStampsImpl.class);

	@Autowired
	StampsRestrictionsService stampsRestrictionService; 
	
	@Autowired
	LdapConnexion ldapConnexion;
	
	@Autowired
	Config config;
	
	@Override
	public String getStamps() throws RmesException {
		TreeSet<String> stamps = new TreeSet<>();
		try {
			if(config.getLdapUrl() != null && !config.getLdapUrl().isEmpty()) {
				// Connexion à la racine de l'annuaire
				DirContext context = ldapConnexion.getLdapContext();

				// Spécification des critères pour la recherche des unités
				SearchControls controls = new SearchControls();
				controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				controls.setReturningAttributes(new String[] { "ou", "description" });
				String filter = "(objectClass=inseeUnite)";

				// Exécution de la recherche et parcours des résultats
				NamingEnumeration<SearchResult> results = context.search(
						"ou=Unités,o=insee,c=fr", filter, controls);
				while (results.hasMore()) {
					SearchResult entree = results.next();
					String stamp = entree.getAttributes().get("ou").get()
							.toString();
					if(!stamp.equals("AUTRE")) {
						stamps.add("\"" + stamp + "\"");
					}
				}

				context.close();
			}

			// Add SSM Stamps
			stamps.add("\"SSM-SSP\"");
			stamps.add("\"SSM-DARES\"");
			stamps.add("\"SSM-DEPP\"");
			stamps.add("\"SSM-DESL\"");
			stamps.add("\"SSM-DREES\"");
			stamps.add("\"SSM-SDES\"");
			stamps.add("\"SSM-SDSE\"");
			stamps.add("\"SSM-MEOS\"");
			stamps.add("\"SSM-OED\"");
			stamps.add("\"SSM-DSEE\"");
			stamps.add("\"SSM-SSMSI\"");
			stamps.add("\"SSM-GF3C\"");
			stamps.add("\"SSM-DSED\"");
			stamps.add("\"SSM-DESSI\"");
			stamps.add("\"SSM-SIES\"");
			stamps.add("\"SSM-DEPS\"");
			
			logger.info("Get stamps succeed");
		} catch (NamingException e) {
			logger.error("Get stamps failed");
			throw new RmesException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), "Get stamps failed");		
		}		
		return stamps.toString();
	}

	@Override
	public String getStamp() throws RmesException {
		JSONObject jsonStamp = new JSONObject();
		jsonStamp.put("stamp",stampsRestrictionService.getUser().getStamp());
		return jsonStamp.toString();
	}

}
