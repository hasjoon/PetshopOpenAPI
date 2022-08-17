package perstore.pet.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import perstore.pet.common.NotFoundException;
import perstore.pet.dto.PetCreateRequest;
import perstore.pet.dto.PetCreateResponse;
import perstore.pet.dto.PetGetResponse;
import perstore.pet.entity.Category;
import perstore.pet.entity.Pet;
import perstore.pet.entity.PetStatus;
import perstore.pet.entity.Tag;
import perstore.pet.repository.CategoryRepository;
import perstore.pet.repository.PetRepository;
import perstore.pet.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("NotFoundException Test")
    void nouFound(){

        PetService petService = new PetService(petRepository, tagRepository,categoryRepository);
        PetCreateRequest mod1 = PetCreateRequest.builder()
            .id(2)
            .name("pet2")
            .status(PetStatus.PENDING)
            .build();

        assertThrows(NotFoundException.class,() -> {petService.updatePetInfo(1,mod1);});
    }



    @Test
    @DisplayName("펫 목록 업데이트")
    void modifyPet(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("pet1");
        pet.setStatus(PetStatus.AVAILABLE);

        //when
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        PetCreateRequest mod1 = PetCreateRequest.builder()
            .name("pet2")
            .status(PetStatus.PENDING)
            .build();

        petService.updatePetInfo(1, mod1);

        //then
        assertEquals(PetStatus.PENDING,pet.getStatus());
        assertEquals("pet2",pet.getName());
    }

    @Test
    @DisplayName("펫을 스토어에 추가")
    void createPet() {

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("tttt");

        Category category = new Category();
        category.setId(1);
        category.setName("cccc");

        PetCreateRequest mod1 = PetCreateRequest.builder()
            .name("pet1")
            .category(category)
            .tags(List.of(tag))
            .status(PetStatus.PENDING)
            .build();

        Pet oldPet = Pet
            .of(mod1.getId(),mod1.getName(), mod1.getCategory(), mod1.getTags(),mod1.getStatus());

        when(petRepository.save(any(Pet.class))).thenReturn(oldPet);

        List<Tag> forTag = new ArrayList<>();
        forTag.add(tag);

        //when
        PetCreateResponse creat = petService.creat(mod1);

        //then
        assertEquals(0,creat.getId());
        assertEquals("pet1",creat.getName());
        assertEquals(forTag.get(0).getId(),creat.getTags().get(0).getId());
        assertEquals(forTag.get(0).getName(),creat.getTags().get(0).getName());
        assertEquals(1,creat.getCategory().getId());
        assertEquals("cccc",creat.getCategory().getName());
        assertEquals(PetStatus.PENDING,creat.getStatus());
    }

    @Test
    @DisplayName("펫 하나 조회")
    void getOne(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("pet1");
        pet.setStatus(PetStatus.AVAILABLE);

        //when
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        PetGetResponse newPet = petService.getOne(1);

        //then
        assertEquals("pet1", newPet.getName());
        assertEquals(PetStatus.AVAILABLE, newPet.getStatus());

    }

    @Test
    @DisplayName("펫 status 조회")
    void 펫_단건_조회(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("pet1");
        pet1.setStatus(PetStatus.AVAILABLE);

        //when
        when(petRepository.findPetStatus(PetStatus.AVAILABLE)).thenReturn(List.of(pet1));

        List<PetGetResponse> newPetList = petService.getStatusOfPet(PetStatus.AVAILABLE);
        PetGetResponse petGetResponse = newPetList.get(0);

        //then
        assertEquals(1, newPetList.size());
        assertEquals(PetStatus.AVAILABLE, petGetResponse.getStatus());
    }

    @Test
    @DisplayName("펫 status 다건 조회")

    void 펫_status_다건_조회(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet1 = new Pet();
        pet1.setId(1);
        pet1.setName("pet1");
        pet1.setStatus(PetStatus.SOLD);

        Pet pet2 = new Pet();
        pet2.setId(2);
        pet2.setName("pet2");
        pet2.setStatus(PetStatus.SOLD);

        //when
        when(petRepository.findPetStatus(PetStatus.SOLD)).thenReturn(List.of(pet1,pet2));

        List<PetGetResponse> newPetList = petService.getStatusOfPet(PetStatus.SOLD);

        //then
        assertEquals(2, newPetList.size());
        assertEquals(PetStatus.SOLD, newPetList.get(0).getStatus());
    }

    @Test
    @DisplayName("펫 Tag 단건 조회")
    void tag_단건_조회(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet1 = new Pet();

        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("Vaigunth");

        pet1.setId(1);
        pet1.setName("pet1");
        pet1.setTags(List.of(tag1));

        //when
        when(petRepository.findPetTags(List.of("Vaigunth"))).thenReturn(List.of(pet1));

        List<PetGetResponse> listPet = petService.getTagsOfPet(List.of("Vaigunth"));

        //then
        assertEquals("Vaigunth", listPet.get(0).getTags().get(0).getName());
        assertNotEquals("tag2",listPet.get(0).getTags().get(0).getName());
    }

    @Test
    @DisplayName("펫 Tag 다건 조회")
    void tag_다건_조회(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet1 = new Pet();

        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("Vaigunth");

        Tag tag2 = new Tag();
        tag2.setId(2);
        tag2.setName("askldf");

        pet1.setId(1);
        pet1.setName("pet1");
        pet1.setTags(List.of(tag1,tag2));

        //when
        when(petRepository.findPetTags(List.of("Vaigunth","askldf"))).thenReturn(List.of(pet1));

        List<PetGetResponse> listPet = petService.getTagsOfPet(List.of("Vaigunth","askldf"));

        //then
        assertEquals(2, listPet.get(0).getTags().size());
        assertEquals("Vaigunth", listPet.get(0).getTags().get(0).getName());
        assertEquals("askldf",listPet.get(0).getTags().get(1).getName());
    }

    @Test
    @DisplayName("폼데이터로 펫 업데이트")
    void petUpdateWithFormData(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("dog1");
        pet.setStatus(PetStatus.SOLD);

        //when
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        petService.updatePet(1, PetCreateRequest.builder()
            .name("dog2")
            .status(PetStatus.AVAILABLE)
            .build());

        //then
        assertEquals("dog2", pet.getName());
        assertEquals(PetStatus.AVAILABLE, pet.getStatus());
    }

    @Test
    @DisplayName("펫 삭제(소프트 딜리트)")
    void petDelete(){

        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("dog1");

        //when
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        petService.deletePet(1);

        //then
        assertEquals(1,pet.getIs_deleted());
    }

    @Test
    @DisplayName("펫 이미지 업로드")
    void petImageUpload(){
        PetService petService = new PetService(petRepository, tagRepository, categoryRepository);

        //given
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("dog1");
        pet.setPhotoUrls(List.of("photo1"));

        PetCreateRequest mod1 = PetCreateRequest.builder()
            .name("dog2")
            .photoUrls(List.of("photo2"))
            .build();

        //when
        when(petRepository.findById(1)).thenReturn(Optional.of(pet));

        petService.uploadImage(1,"meta",mod1);

        //then
        assertEquals(List.of("photo2"),pet.getPhotoUrls());
    }
}

