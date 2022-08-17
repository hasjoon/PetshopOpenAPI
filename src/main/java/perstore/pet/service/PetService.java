package perstore.pet.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import perstore.pet.common.NotFoundException;
import perstore.pet.dto.PetCreateRequest;
import perstore.pet.dto.PetCreateResponse;
import perstore.pet.dto.PetGetResponse;
import perstore.pet.dto.PetTagResponse;
import perstore.pet.entity.Category;
import perstore.pet.entity.Pet;
import perstore.pet.entity.PetStatus;
import perstore.pet.entity.Tag;
import perstore.pet.repository.CategoryRepository;
import perstore.pet.repository.PetRepository;
import perstore.pet.repository.TagRepository;

@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;


    public PetCreateResponse updatePetInfo(int id, PetCreateRequest petCreateRequest) {
        Pet newPet = petRepository.findById(id).orElseThrow(()-> new NotFoundException());
        newPet.setName(petCreateRequest.getName());
        newPet.setCategory(petCreateRequest.getCategory());
        newPet.setStatus(petCreateRequest.getStatus());
        petRepository.save(newPet);

        return PetCreateResponse.builder()
            .id(newPet.getId())
            .name(newPet.getName())
            .category(newPet.getCategory())
            .status(newPet.getStatus())
            .build();
    }

    public PetCreateResponse creat(PetCreateRequest petCreateRequest){
        Pet oldPet = new Pet();
        petRepository.save(oldPet);


        for (Tag tag : petCreateRequest.getTags()) {
            tag.setPet(oldPet);
            tagRepository.save(tag);
        }

        Category category = petCreateRequest.getCategory();
        category.setPet(oldPet);
        categoryRepository.save(category);

        oldPet.setName(petCreateRequest.getName());
        oldPet.setCategory(petCreateRequest.getCategory());
        oldPet.setTags(petCreateRequest.getTags());
        oldPet.setStatus(petCreateRequest.getStatus());
        oldPet.setPhotoUrls(petCreateRequest.getPhotoUrls());

        petRepository.save(oldPet);

        List<PetTagResponse> tagsWithout = oldPet.getTags().stream().map(item -> PetTagResponse.builder()
            .name(item.getName())
            .id(item.getId())
            .build()).collect(Collectors.toList());

        return PetCreateResponse.builder()
            .id(oldPet.getId())
            .name(oldPet.getName())
            .category(oldPet.getCategory())
            .tags(tagsWithout)
            .photoUrls(oldPet.getPhotoUrls())
            .status(oldPet.getStatus())
            .build();
    }

    public PetGetResponse getOne(int id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException());

        return PetGetResponse.builder()
            .id(pet.getId())
            .name(pet.getName())
            .category(pet.getCategory())
            .photoUrls(pet.getPhotoUrls())
            .tags(pet.getTags())
            .status(pet.getStatus())
            .build();
    }

    public PetCreateResponse updatePet(int id, PetCreateRequest petCreateRequest) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException());

        List<PetTagResponse> tagsWithout = pet.getTags().stream().map(item -> PetTagResponse.builder()
            .name(item.getName())
            .id(item.getId())
            .build()).collect(Collectors.toList());

        pet.setName(petCreateRequest.getName());
        pet.setStatus(petCreateRequest.getStatus());
        pet.setCategory(petCreateRequest.getCategory());
        pet.setTags(petCreateRequest.getTags());

        Pet savedPet = petRepository.save(pet);

        return PetCreateResponse.builder()
            .id(savedPet.getId())
            .name(savedPet.getName())
            .category(savedPet.getCategory())
            .tags(tagsWithout)
            .photoUrls(savedPet.getPhotoUrls())
            .status(savedPet.getStatus())
            .build();
    }

    public void deletePet(int id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException());
        pet.setIs_deleted(1);

        petRepository.save(pet);
    }

    public List<PetGetResponse> getStatusOfPet(PetStatus status) {
        List<Pet> pet = petRepository.findPetStatus(status);

        return pet.stream().map(item -> PetGetResponse.builder()
            .id(item.getId())
            .name(item.getName())
            .status(item.getStatus())
            .category(item.getCategory())
            .tags(item.getTags())
            .build()
        ).collect(Collectors.toList());
    }

    public List<PetGetResponse> getTagsOfPet(List<String> tags) {
        List<Pet> pet = petRepository.findPetTags(tags);

        return pet.stream().map(item -> PetGetResponse.builder()
            .id(item.getId())
            .name(item.getName())
            .category(item.getCategory())
            .tags(item.getTags())
            .status(item.getStatus())
            .build()).collect(Collectors.toList());
    }

    public PetCreateResponse uploadImage(int id, String metaData, PetCreateRequest petCreateRequest){
        Pet pet = petRepository.findById(id).orElseThrow(() -> new NotFoundException());

        List<String> photoUrls = petCreateRequest.getPhotoUrls();

        pet.setPhotoUrls(photoUrls);

        petRepository.save(pet);

        return PetCreateResponse.builder()
            .id(pet.getId())
            .name(pet.getName())
            .photoUrls(pet.getPhotoUrls())
            .status(pet.getStatus())
            .build();
    }
}
