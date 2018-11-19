package pl.eHouse.web.common.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.gwt.server.AtmosphereGwtHandler;
import org.atmosphere.gwt.server.GwtAtmosphereResource;

import pl.eHouse.api.Communicator;

/**
 * @author p.havelaar
 */
public class AtmosphereHandler extends AtmosphereGwtHandler {
	
	Communicator comm;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
        	comm = Communicator.getInstance();
			new ServerInitializer().init(comm);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
    	super.doComet(resource);
    	comm.registerListenerInput(new ApiInputListener(resource));
    	comm.registerListenerOutput(new ApiOutputListener(resource));
    	comm.registerListenerResult(new ApiResultListener(resource));
    	logger.info("Create connection: " + resource.getConnectionUUID());
        return NO_TIMEOUT;
    }

    @Override
    public void cometTerminated(GwtAtmosphereResource resource, boolean initiated) {
        super.cometTerminated(resource, initiated);
        comm.unRegisterListenerInput(new ApiInputListener(resource));
        comm.unRegisterListenerOutput(new ApiOutputListener(resource));
    	comm.unRegisterListenerResult(new ApiResultListener(resource));
        logger.info("Terminate connection: " + resource.getConnectionUUID());
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response, 
    		List<?> messages, GwtAtmosphereResource resource) {
    		for(Object message : messages) {
    			WebInputListener.messageIn(message,resource);
    		}    		        		
    };

}
