package eu.emsodev.observations.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletResponse;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Controller
public class ODVgetFilesApiController implements ODVgetFilesApi {

    public ResponseEntity<File> odvFilesGet(HttpServletResponse response) {
        
    
    	// Set the content type and attachment header.
    	response.addHeader("Content-disposition", "attachment;filename=myfilename.txt");
    	response.setContentType("txt/plain");
    
    	File file = new File("odv.txt");
        
    	
    	
    	
        return new ResponseEntity<File>(file,HttpStatus.OK);
    }

}
