package io.jans.as.server.servlet;

import io.jans.as.common.model.registration.Client;
import io.jans.as.server.service.ClientService;
import io.jans.as.server.service.token.TokenService;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.UUID;


/**
 * @author Javier Rojas Blum
 * @author Yuriy Movchan Date: 2016/04/26
 * @version August 14, 2019
 */

@WebServlet(urlPatterns = "/open-banking/v3.1/aisp/account-access-consents", loadOnStartup = 9)
public class AccontAccessConsentServlet extends HttpServlet {

	private static final long serialVersionUID = -8224898157373678903L;

	@Inject
	private Logger log;
	
	@Inject
        private TokenService tokenService;
    
        @Inject
        private ClientService clientService;

	@Override 
	public void init() throws ServletException
	{
		log.info("Inside init method of AccoutAccess Consent ***********************************************************************");
	}
	
	public static void printJsonObject(JSONObject jsonObj, ServletOutputStream out ) throws IOException {
	    for (String keyStr : jsonObj.keySet()) {
	        Object keyvalue = jsonObj.get(keyStr);
	        //Print key and value
		    out.println("key: "+ keyStr + " value: " + keyvalue);
		    if (keyvalue instanceof JSONObject)
		    	printJsonObject((JSONObject)keyvalue,out);
		}
	}
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param servletRequest servlet request
	 * @param httpResponse servlet response
	 */	
	protected void processRequest(HttpServletRequest servletRequest, HttpServletResponse httpResponse) {
		log.info("Starting processRequest method of AccoutAccess Consent ***********************************************************************");
		String authFromReq = null;
        try (PrintWriter out = httpResponse.getWriter()) {
        	
        	String jsonBodyStr= IOUtils.toString(servletRequest.getInputStream());
        	JSONObject jsonBody = new JSONObject(jsonBodyStr);

        	httpResponse.setContentType("application/json");
        	String xfapiinteractionid=UUID.randomUUID().toString();;
        	httpResponse.addHeader("x-fapi-interaction-id", xfapiinteractionid);
        	httpResponse.setCharacterEncoding("UTF-8");
        	JSONObject jsonObj = new JSONObject();
        	
        	String permissionKey="";
        	String permissionValue="";
        	
        	for (String keyStr : jsonBody.keySet()) {
    	    	Object keyvalue = jsonBody.get(keyStr);
    	    	jsonObj.put(keyStr, keyvalue);
    		    if (keyStr.equals("Risk"))
        	   	{
        	   	}
    	    	if (keyStr.equals("Data")) {
    	    		JSONObject keyvalueTemp = (JSONObject)jsonBody.get(keyStr);
    		    	for (String keyStr1 : keyvalueTemp.keySet()) {
    		    		Object keyvalue1 = keyvalueTemp.get(keyStr1);
    		    		if (keyStr1.equals("Permissions"))
    		    	   	{
    		    	   		permissionKey=keyStr1; 
    		    	   		permissionValue=keyvalue1.toString();
    		    	   	}
    		    	}
    	    	}
    	    }
    	    	
            authFromReq = servletRequest.getHeader("Authorization");
        	
            String clientDn=null;
            Client cl=null;
            String clientID=null;
            String ConsentID=null;
            clientDn=tokenService.getClientDn(authFromReq);
        	       	
        	if (clientDn != null) {
        		log.info("FAPIOBUK: ClientDn from Authoirization(tokenService) *********************************************"+clientDn);
        		cl=clientService.getClientByDn(clientDn);
        		clientID=cl.getClientId();
        	}	
        	else 
        		log.info("FAPIOBUK: ClientDn is null");
        		
        	if (clientID !=null)	
        		ConsentID=UUID.randomUUID().toString()+":"+clientID;
        	else{
        		ConsentID=UUID.randomUUID().toString();
        		log.info("FAPIOBUK: ClientID is null");
        	}
        	jsonObj.put("Links", new JSONObject().put("self","/open-banking/v3.1/aisp/account-access-consents/"+ConsentID));
        	
        	JSONObject data=new JSONObject();
        	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        	data.put("CreationDateTime", timestamp.getTime());
        	data.put("Status", "AwaitingAuthorisation");
        	data.put(permissionKey, permissionValue);
        	data.put("ConsentId",ConsentID);
        	data.put("StatusUpdateDateTime", timestamp.getTime());
        	jsonObj.put("Data",data);
        	   	
        	out.print(jsonObj.toString());
        	httpResponse.setStatus(201, "Created");

        	out.flush();
        	log.info("Finished processRequest method of AccoutAccess Consent ***********************************************************************");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
	}


	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Account Access Consent";
	}
}
