package eu.emsodev.observations.api;

import java.io.File;

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


@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-02-14T13:31:28.991Z")

@Controller
public class NetCDFgetFilesApiController implements NetCDFgetFilesApi {

    public ResponseEntity<File> filesGet() {
        // do some magic!
        return new ResponseEntity<File>(HttpStatus.OK);
    }

}
