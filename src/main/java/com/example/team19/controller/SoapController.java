package com.example.team19.controller;

import com.example.team19.dto.NewReplyDTO;
import com.example.team19.soap.CarClient;
import com.example.team19.wsdl.CommentsResponse;
import com.example.team19.wsdl.SendReplyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/soap")
public class SoapController {

    @Autowired
    private CarClient carClient;

    @GetMapping(value = "/comments/{adId}")
    public ResponseEntity<?> getComments(@PathVariable("adId") Long adId){

        CommentsResponse cr = this.carClient.getComments(adId);
        return new ResponseEntity(cr.getCommentSOAP(), HttpStatus.OK);
    }

    @PutMapping(value="/comments/{id}")
    public ResponseEntity<?> replyComments(@PathVariable("id") Long commentId, @RequestBody NewReplyDTO reply)
    {

        SendReplyResponse srr = carClient.replyComment(commentId,reply);

        if(srr.isFlag())
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
