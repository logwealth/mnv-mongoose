package com.javaguide.SpringBootMongoodb.Controller;


import com.javaguide.SpringBootMongoodb.Model.Tutorial;
import com.javaguide.SpringBootMongoodb.Repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:5050")
@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialRepository tutorialRepository;


    //create tutorial:
    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial){
        try {
            Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(),false));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get all tutorial:
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title){
        try{
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get tutorial by id:
    @GetMapping("tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if(tutorialData.isPresent()){
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //published:
    @GetMapping("tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished(){
        try{
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update operation:
    @PutMapping("tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial){
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        if(tutorialData.isPresent()){
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.getPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //deleted by id:
    @DeleteMapping("tutorials/{id}")
    public ResponseEntity<Tutorial> deleteTutorialById (@PathVariable("id") String id){
        try{
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //deleted all:
    @DeleteMapping("/tutorials")
    public ResponseEntity<Tutorial> deleteAllTutorial(){
        try{
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
