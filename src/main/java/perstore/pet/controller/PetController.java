package perstore.pet.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import perstore.pet.dto.PetCreateRequest;
import perstore.pet.dto.PetCreateResponse;
import perstore.pet.dto.PetGetResponse;
import perstore.pet.entity.PetStatus;
import perstore.pet.service.PetService;

@RestController
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    /**
     * 이미 존재하는 펫 정보 업데이트
     */
    @PutMapping("/pet/{id}")
    public ResponseEntity modifyPet(@PathVariable("id") int id, @RequestBody PetCreateRequest petCreateRequest){
        PetCreateResponse modifiedPet = petService.updatePetInfo(id, petCreateRequest);
        return ResponseEntity.ok().body(modifiedPet);
    }

    /**
     * 새로운 펫 정보를 스토어에 업데이트
     */
    @PostMapping("/pet")
    public ResponseEntity creat(@RequestBody PetCreateRequest petCreateRequest){
        PetCreateResponse newPet = petService.creat(petCreateRequest);
        return ResponseEntity.ok().body(newPet);
    }

    /**
     *펫의 Status로 조회
     */
    @GetMapping("/pet/findByStatus")
    public ResponseEntity getStatus(@RequestParam PetStatus status){
        List<PetGetResponse> petStatus = petService.getStatusOfPet(status);
        return ResponseEntity.ok().body(petStatus);
    }

    /**
     * 펫의 Tags로 조회
     */
    @GetMapping("/pet/findByTags")
    public ResponseEntity getTags(@RequestParam List<String> tags){
        List<PetGetResponse> petTag = petService.getTagsOfPet(tags);
        return ResponseEntity.ok().body(petTag);
    }


    /**
     * 펫의 Id로 조회
     */
    @GetMapping("/pet/{id}")
    public ResponseEntity getOne(@PathVariable("id") int id){
        PetGetResponse getOne = petService.getOne(id);
        return ResponseEntity.ok().body(getOne);
    }

    /**
     * 폼데이터로 펫 업데이트
     */
    @PostMapping("/pet/{id}")
    public ResponseEntity updatePet(@PathVariable("id") int id, @RequestBody PetCreateRequest petCreateRequest){
        PetCreateResponse updatedPet = petService.updatePet(id, petCreateRequest);
        return ResponseEntity.ok().body(updatedPet);
    }

    /**
     * 펫 삭제
     */
    @DeleteMapping("/pet/{id}")
    public void deletePet(@PathVariable("id") int id){
        petService.deletePet(id);
    }


    /**
     * 펫 이미지 업데이트
     */
    @PostMapping(value = "/pet/{id}/uploadImage", produces = "application/octet-stream")
    public ResponseEntity uploadImage(@PathVariable("id") int id, @RequestParam("additionalMetadata") String metaData, @RequestBody PetCreateRequest petCreateRequest){

        PetCreateResponse uploadImage = petService.uploadImage(id,metaData,petCreateRequest);
        return ResponseEntity.ok().body(uploadImage);
    }

}
