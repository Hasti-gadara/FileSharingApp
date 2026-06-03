package com.example.FileSharingApp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.FileSharingApp.service.FileService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/files")
public class Filecontroller {

    @Autowired
    private FileService fileService;

    @GetMapping("/home")
    public String index(Model model){
        model.addAttribute("files",fileService.getAll());
        return "list-files";
    }
    

     @PostMapping("/upload")
    public String postMethodString(@RequestParam("file") MultipartFile file , @RequestParam("uploadedBy") String uploadedBy)throws IOException{
        fileService.uploadFile(file,uploadedBy);
        return "redirect:/files/home";
    }

    @GetMapping("/share/{id}")
    public String shareFile(@PathVariable int id,Model model){
        ResponseEntity<?> fileModel = fileService.shareFile(id);
        if(fileModel.hasBody()){
            String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
            model.addAttribute("shareUrl",currentUrl);
            model.addAttribute("file",fileModel.getBody());
            return "share-file";
        }else{
        return "redirect:/files/";
    }

}

   @PostMapping("/delete/{id}")
public String deleteFile(@PathVariable int id) {
       ResponseEntity<?> file = fileService.deleteFile(id);
       if (file.hasBody()) {
           return "redirect:/files/home";
       } else {
           return "redirect:/files/home";
       }
   }
      @GetMapping("/")
    public String login() {
        return "home";
    }

       @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable int id) {
    return fileService.getFile(id);
    } 

     
}

