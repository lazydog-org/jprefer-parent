package org.lazydog.preference.manager.web.managedbean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.lazydog.preference.manager.PreferenceManager;


/**
 * Import/export managed bean.
 *
 * @author  Ron Rickard
 */
public class ImportExportMB implements Serializable {

    @EJB(mappedName="ejb/PreferenceManager", beanInterface=PreferenceManager.class)
    protected PreferenceManager preferenceManager;

    /**
     * Export the preferences to a document.
     *
     * @param  actionEvent  the action event.
     */
    public void exportDocument(ActionEvent actionEvent) {

        try {

            // Declare.
            FacesContext context;
            String document;
            HttpServletResponse response;
            ServletOutputStream output;

            // Export the document.
            document = preferenceManager.exportDocument();

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
            e.printStackTrace();
        }
    }

    /**
     * Import the preferences from a document.
     *
     * @param  uploadEvent  the upload event.
     */
    public void importDocument(UploadEvent uploadEvent) {

        try {

            // Declare.
            String document;
            UploadItem uploadItem;

            // Get the upload item.
            uploadItem = uploadEvent.getUploadItem();
            document = readFileAsString(uploadItem.getFile().getAbsolutePath());

            // Import the document.
            preferenceManager.importDocument(document);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the file as a string.
     *
     * @param  filePath  the file path.
     *
     * @return  the file as a string.
     *
     * @throws  IOException  if unable to read the file as a string.
     */
    private static String readFileAsString(String filePath) throws IOException {

        // Declare.
        byte[] buffer;
        BufferedInputStream input;

        // Read the file as a byte array.
        buffer = new byte[(int)new File(filePath).length()];
        input = new BufferedInputStream(new FileInputStream(filePath));
        input.read(buffer);

        // Convert the byte array to a string.
        return new String(buffer);
    }
}
