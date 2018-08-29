package qa.andrew.springboot.database.hello.mySpringBootDatabaseApp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qa.andrew.springboot.database.hello.mySpringBootDatabaseApp.exception.ResourceNotFoundException;
import qa.andrew.springboot.database.hello.mySpringBootDatabaseApp.model.MySpringBootDataModel;
import qa.andrew.springboot.database.hello.mySpringBootDatabaseApp.repository.MySpringBootRepository;

@RestController
@RequestMapping("/api")
public class MySpringBootDataAppController {
	
	@Autowired
	MySpringBootRepository myRepository;
	
	@PostMapping("/person")
	public MySpringBootDataModel createPerson(@Valid @RequestBody MySpringBootDataModel mSDM) {
		return myRepository.save(mSDM);
	}
	
	@GetMapping("person/{id}")
	public MySpringBootDataModel getPersonById(@PathVariable(value = "id")Long personID) {
		return myRepository.findById(personID).orElseThrow(()-> new ResourceNotFoundException("MySpringBootDataModel","id",personID));
	}
	
	@GetMapping("person/")
	public List<MySpringBootDataModel> getAllPeople(){
		return myRepository.findAll();
	}
	
	@PutMapping("/person/{id}")
	public MySpringBootDataModel updatePerson(@PathVariable(value = "id") Long personID, 
			@Valid @RequestBody MySpringBootDataModel personDetails) {
		
		MySpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(()-> new ResourceNotFoundException("MySpringBootDataModel","id",personID));
		
		mSDM.setName(personDetails.getName());
		mSDM.setAddress(personDetails.getAddress());
		mSDM.setAge(personDetails.getAge());
		
		MySpringBootDataModel updateData = myRepository.save(mSDM);
		return updateData;
	}
	
	@DeleteMapping("/person/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id") Long personID) {
		MySpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(()-> new ResourceNotFoundException("MySpringBootDataModel","id",personID));
		
		myRepository.delete(mSDM);
		return ResponseEntity.ok().build();
	}
	
}
