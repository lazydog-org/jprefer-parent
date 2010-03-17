package org.lazydog.preference.manager.web.managedbean;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import org.lazydog.preference.manager.Preference;


/**
 * Import/export managed bean.
 *
 * @author  Ron Rickard
 */
public class ImportExportMB {

    /**
     * Export the preferences.
     *
     * @param  actionEvent  the action event.
     */
    public void export(ActionEvent actionEvent) {

        try {

            // Declare.
            FacesContext context;
            String document;
            HttpServletResponse response;
            ServletOutputStream output;

            // Export the document.
            document = (String)Preference.exportDocument();

            // Set the response headers.
            context = FacesContext.getCurrentInstance();
            response = (HttpServletResponse)context.getExternalContext().getResponse();
            response.setHeader("Content-Disposition", "attachment;filename=preferences.xml");
            response.setContentType("application/octet-stream");
            response.setContentLength(document.length());

            // Send the document.
            output = response.getOutputStream();
            output.print(document);
            output.flush();
            output.close();
            context.responseComplete();
        }
        catch(Exception e) {
            // TODO: handle exception.
System.err.println("Unable to export preferences.\n" + e);
        }
    }
}
