package com.xhzhao.controller;

import com.xhzhao.service.ExecuteStringJavaService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class RunCodeController {
    private Logger logger = LoggerFactory.getLogger(RunCodeController.class);

    @Autowired
    private ExecuteStringJavaService executeStringJavaService;

    private static final String defaultSource = "public class Solution {\n"
            + "    public static void main(String[] args) {\n"
            + "        \n"
            + "    }\n"
            + "}";

    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String entry(Model model){
        model.addAttribute("lastSource", defaultSource);
        return "ide";
    }

    @RequestMapping(path = {"/run"}, method = RequestMethod.POST)
    public String runCode(@RequestParam("source") String source, Model model){
        String runResult = executeStringJavaService.execute(source);
        runResult = runResult.replaceAll(System.lineSeparator(), "<br/>");
        model.addAttribute("lastSource", source);
        model.addAttribute("runResult", runResult);
        return "ide";
    }


}
