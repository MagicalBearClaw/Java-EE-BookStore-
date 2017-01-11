
package ca.pacmabooks.client.beans;

import java.io.InputStream;
import javax.faces.context.FacesContext;
import javax.inject.Named;
 
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
 
/**
 * This is used to download the book that user has
 * selected in the format that they want.
 * @author Michael mcmahon
 */
@Named
public class FileDownload {
    
    public FileDownload()
    {
     
    }

    /**
     * 
     * gets the file to download from which the format and
     * book title was selected.
     * 
     * @author Michael mcmahon
     * @param bookName
     * @param format
     * @param defaultFormat
     * @return 
     */
    public StreamedContent createFile(String bookName, String format, String defaultFormat) 
    {
        String chosenFormat = format.equals("") ? defaultFormat : format;
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/downloads/Alice_in_Wonderland.pdf");
        StreamedContent file = new DefaultStreamedContent(stream, "image/pdf", bookName + "." + chosenFormat);
        return file;
    }
    
    public void onChangeFormat()
    {
        
    }
    
}