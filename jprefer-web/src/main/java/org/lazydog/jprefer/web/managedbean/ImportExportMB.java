/**
 * Copyright 2009, 2010 lazydog.org.
 *
 * This file is part of JPrefer.
 *
 * JPrefer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPrefer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lazydog.jprefer.web.managedbean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;


/**
 * Import/export managed bean.
 *
 * @author  Ron Rickard
 */
public class ImportExportMB extends AbstractMB implements Serializable {

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
            document = getPreferenceManager().exportDocument();

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
            getPreferenceManager().importDocument(document);
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
