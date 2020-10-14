package com.application.springMvc.controller;

import com.application.springMvc.utils.UtilsIO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Controller for requests handling
 * @author Ihor Savchenko
 * @version 1.0
 */
@Controller
public class MvcController {

    @Autowired
    ServletContext servletContext;

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/getImageJpegPureServlet")
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        InputStream in = servletContext.getResourceAsStream("/resources/image.jpg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping(
            value = "/getImageJpeg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaTypeJpeg() throws IOException {
        InputStream in = getClass().getResourceAsStream("/image.jpg");
        return UtilsIO.getByteArray(in);
    }

    @GetMapping(
            value = "/getImagePng",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaTypePng() throws IOException {
        InputStream in = getClass().getResourceAsStream("/image.png");
        return UtilsIO.getByteArray(in);
    }

    @GetMapping(
            value = "/getImageGif",
            produces = MediaType.IMAGE_GIF_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaTypeGif() throws IOException {
        InputStream in = servletContext.getResourceAsStream("/resources/image.gif");
        return UtilsIO.getByteArray(in);
    }

    @GetMapping(
            value = "/getBytes",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody byte[] getBytesWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/image.jpg");
        return UtilsIO.getByteArray(in);
    }

    @GetMapping("/uploadForm")
    public String getUploadForm(){
        return "uploadFormPage";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleUpload(HttpServletRequest request) {

        System.out.println(System.getProperty("java.io.tmpdir"));
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        System.out.println(isMultipart);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);

        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                System.out.println(item.getFieldName() + " : " + item.isFormField());

                if (!item.isFormField()) {
                    try (
                            InputStream uploadedStream = item.getInputStream();
                            OutputStream out = new FileOutputStream("f:/resume2.pdf")) {

                        IOUtils.copy(uploadedStream, out);
                        out.flush();
                    }
                }
            }
            return "success!";
        }
        catch (IOException | FileUploadException ex){
            return "failed" + ex.getMessage();
        }
    }

    @GetMapping("/uploadFormAsStream")
    public String getUploadFormAsStream(){
        return "uploadFormPageAsStream";
    }

    @PostMapping("/uploadAsStream")
    @ResponseBody
    public String handleUploadAsStream(HttpServletRequest request) {

        ServletFileUpload upload = new ServletFileUpload();
        try {
            FileItemIterator iterStream = upload.getItemIterator(request);
            while (iterStream.hasNext()) {
                FileItemStream item = iterStream.next();

                if (!item.isFormField()) {
                    try (
                            InputStream uploadedStream = item.openStream();
                            OutputStream out = new FileOutputStream("f:/resume2.pdf")) {

                        IOUtils.copy(uploadedStream, out);
                        out.flush();
                    }
                }
                else{
                    System.out.println(item.getFieldName() + " : " + item.isFormField());
                }
            }
            return "success!";
        }
        catch (IOException | FileUploadException ex){
            return "failed" + ex.getMessage();
        }
    }

}