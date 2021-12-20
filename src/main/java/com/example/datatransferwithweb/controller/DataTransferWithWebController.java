package com.example.datatransferwithweb.controller;

import com.example.datatransferwithweb.service.DataTransferWithWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class DataTransferWithWebController {

    private final DataTransferWithWebService dataTransferWithWebService;


    @RequestMapping(value="/create", method = RequestMethod.POST)
    public void createImage(HttpServletRequest request) {
        dataTransferWithWebService.createImage(request);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void ImageSender(HttpServletRequest request) {
        dataTransferWithWebService.ImageSender(request);
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public void ImageReceiver(HttpServletRequest request) { dataTransferWithWebService.ImageReceiver(request); }

    @RequestMapping(value = "/receive2", method = RequestMethod.POST)
    public void ReceiveImage(HttpServletRequest request) { dataTransferWithWebService.ReceiveImage(request); }

}
